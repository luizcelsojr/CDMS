package br.unicamp.ic.lis.cdms.algebra

import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/4/15
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
class Operators {
    GroovyShell sh = new GroovyShell()

    public Table select (Table t, Closure condition){
        Table t2 = new Table()
        for (row in t){
            if (condition(row)) t2.addRow(row)
        }
        return t2
    }

    Table project (Table t, List columns){
        if (!columns) return t
        assert t.getSchema().containsAll(columns), "Wooops, table schema does not contain all attributes to project"
        Table tOut = new Table()
        Map newRow
        for (row in t) {
            newRow = [:]
            for (c in columns) newRow[c] = row[c]
            tOut.addRow(newRow)
        }
        return tOut
    }

    Table step(Table V, Table E,  direction){
        //TODO: filter labels (add parameter)
        assert "id" in V.getSchema(), "First table must have an 'id' column"

        assert "id" in E.getSchema(), "Second table must have an 'id' column"
        assert "id_n" in E.getSchema(), "Second table must have an 'id_n' column"

        Table tOut = new Table()
        Table vIn

        if ("id_n" in V.getSchema()) vIn = V.copy()
        else    vIn = set(V, ["it.id_n = it.id"])



        if (direction == Constants.INBOUND) tOut =  stepIn(vIn, E, [])
        else if (direction == Constants.OUTBOUND) tOut =  stepOut(vIn, E, [])
        else if (direction == Constants.BOTH) tOut =  union(stepIn(vIn, E, []), stepOut(vIn, E, []))


/*        for (v in V){
            for (e in E){
                if ((v.id_n == e.id_n) && (direction != Constants.OUTBOUND)){  //BOTH or INBOUND
                    def newW = e.clone()
                    newW.id_n = newW.id
                    newW.remove('id')
                    tOut.addRow(v.clone().plus(newW))

                }
                else if ((v.id_n == e.id) && (direction != Constants.INBOUND)){  //BOTH or OUTBOUND
                    def newW = e.clone()
                    newW.remove('id')
                    tOut.addRow(v.clone().plus(newW))

                }
            }
        }   */

        return tOut
    }

    Table set (t, List setFunctions){
        //setup and execute set functions
        Table tOut = new Table()
        List setClosures = []
        for (f in setFunctions) setClosures.add(this.sh.evaluate("{it -> " + f + "}"))
        for (row in t) {
            setClosures.each{it(row)}
            tOut.addRow(row)
        }
        return tOut

    }

    Table map (t, List mapFunctions){
        if (!mapFunctions) return t
        //setup and execute set functions
        Table tOut = new Table()
        List mapClosures = []
        for (f in mapFunctions) mapClosures.add(this.sh.evaluate("{it -> " + f + "}"))

        Table tCount = reduce(t, ["id"], [[aggr:"sum", func:"1", as:"c"]])
        tCount = project(set(tCount, ["it.idCount = it.id"]), ["idCount", "c"])
        t = tetaJoin(t, tCount, {it.id == it.idCount})

        for (row in t) {
            mapClosures.each{it(row)}
            tOut.addRow(row)
        }
        return tOut
    }

    Table stepOut(Table V, Table E, def follow = [], String newIdName = "id_n"){
        Table tOut = new Table()

        //E = rename(E, [["id", "E_id"], ["id_n", "E_id_n"]])
        E = renameAll(E, "E", "")

        tOut =  tetaJoin(V, E, {it.id_n == it.E_id})
        tOut = rename(tOut, [["E_id_n", newIdName]])

        return tOut

    }

    Table stepIn(Table V, Table E, def follow = [], String newIdName = "id_n"){
        Table tOut = new Table()

        //E = rename(E, [["id", "E_id"], ["id_n", "E_id_n"]])
        E = renameAll(E, "E", "")

        tOut =  tetaJoin(V, E, {it.id_n == it.E_id_n})
        tOut = rename(tOut, [["E_id", newIdName]])

        return tOut

    }

    Table stepMap(Table R, Table E, Table V, Object direction, Object follow = [], List mapFunctions){
        //TODO: filter labels (add parameter)
        assert "id" in R.getSchema(), "First table must have an 'id' column"
        assert "id" in E.getSchema(), "Second table must have an 'id' column"
        assert "id_n" in E.getSchema(), "Second table must have an 'id_n' column"

        Table tOut = new Table()
        Table t = new Table()
        //V.print()


        if (!("id_n" in R.getSchema())) R = set(R, ["it.id_n = it.id"])

        R = set(R, ["it.id_old = it.id_n"])

        if (direction == Constants.INBOUND) t =  stepIn(R, E, [], "newId")
        else if (direction == Constants.OUTBOUND) t =  stepOut(R, E, [], "newId")
        else if (direction == Constants.BOTH) t =  union(stepIn(R, E, [], "newId"), stepOut(R, E, [], "newId"))

        if (V.getSize()>0) {
            V = renameAll(V, "V", "")
            t = tetaJoin(t, V, {it.newId == it.V_id})
        }

        //t.print()

        List mapClosures = []
        for (f in mapFunctions) mapClosures.add(this.sh.evaluate("{it -> " + f + "}"))

        Table tCount = reduce(t, ["id_old"], [[aggr:"sum", func:"1", as:"c"]])
        tCount = project(set(tCount, ["it.idCount = it.id_old"]), ["idCount", "c"])
        //tCount.print()

        t = tetaJoin(t, tCount, {it.id_old == it.idCount})
        //t.print()

        for (row in t) {
            mapClosures.each{it(row)}
            tOut.addRow(row)
        }

        tOut = rename(tOut, [["newId", "id_n"]])
        //tOut.print()

        return tOut

    }

    //public Table beta (Table t, Integer n, Closure condition, direction, follow, List set, List mapFunctions, List reduceFunctions, List updateFunctions) {}

    public Table beta (Table t, Table e, Table v, Integer n, Closure condition, Object direction, Object follow, List setFunctions, List mapFunctions, List reduceGroupBy, List reduceFunctions, List updateFunctions, List stopConditions = []) {
        //TODO: check schemas
        //TODO: check arguments
        if (n < 1) return t

        Table tOut = new Table()
        Table tNew = t.copy()
        tNew = set(tNew, ['it.id_n = it.id'])

        if (setFunctions) tNew = set(tNew, setFunctions)

        while (n-- > 0){
            //tNew.print()
            tNew = stepMap(tNew, e, v, direction, follow, mapFunctions)
            //tNew.print()

            if (reduceFunctions) tNew = reduce(tNew, reduceGroupBy, reduceFunctions )
            //tNew.print()

            if (updateFunctions) tOut = update(tOut, tNew, reduceGroupBy, updateFunctions)
            //tOut.print()

            if (stopConditions) for (c in stopConditions){
                def test = c[0]
                if (tOut."$test"(c[1])) return tOut
            }
        }

        return tOut

    }



    Table reduce(Table t, List group, List <Map> reduceFunctionsIn){
        //aggregation example:
        // [aggr:"min", func:"it.dist", as:"minDist"]

        if (! t.getSize()) return t

        Table tOut = new Table()

        t.orderAsc(group)

        List postClosures = [] //closures that do no aggregate (workaround)

        List <Map> reduceFunctions = []
        //setup reduce functions
        for (f in reduceFunctionsIn) {
            if (f.aggr == 'post'){//not to be run during grouping (workaround since we don't have expr parsing)
                postClosures.add(f.func)
            }else{
                f.closure = this.sh.evaluate("{it -> " + f.func + "}")
                f.group = []
                reduceFunctions.add(f)

            }
        }

        def i = 0
        def cRow = t.getRowAt(0)
        for (row in t){

            if (!(group.every{row."$it" == cRow."$it"}) ) { //((it.id_n != cRow.id_n) )
                //for (row in t) setClosures.each{it(row)}
                for (f in reduceFunctions) {
                    cRow[f.as] = (f.group."${f.aggr}"())
                    //if (!cRow[f.as]) cRow[f.as] = 0 // in case previous returns null
                    f.group = []
                }
                tOut.addRow(cRow)
                cRow = row

            }

            for (f in reduceFunctions) f.group.add( f.closure(row))
        }

        //execute for the last row
        for (f in reduceFunctions)
            cRow[f.as] = (f.group."${f.aggr}"())
        tOut.addRow(cRow)

        if (postClosures) tOut = set(tOut, postClosures)


        return tOut
    }


    public Table update(Table oldT, Table newT, List group, List updateFunctions){
        //TODO: asset it has a group and functions
        if (!updateFunctions) return t
        //setup and execute set functions
        Table tOut = new Table()
        List updateClosures = []
        for (f in updateFunctions) updateClosures.add(this.sh.evaluate("{current, newV -> " + f + "}"))

        for (rOld in oldT){
            def addedO = false
            for (rNew in newT){
                if (group.every{rOld."$it" == rNew."$it"}){
                    //Map newRow = rOld
                    updateClosures.each{it(rOld, rNew)}
                    tOut.addRow(rOld)
                    addedO = true
                }
            }
            if (!addedO) tOut.addRow(rOld)
        }

        //another pass to complete the full join
        for (rNew in newT){
            def addedO = false
            for (rOld in oldT){
                if (group.every{rOld."$it" == rNew."$it"}){
                    addedO = true
                }
            }
            if (!addedO) tOut.addRow(rNew)
        }

        return tOut
    }


    public Table rename(Table t, List renamings){
        Table tOut = new Table()
        for (r in renamings) {
            assert (r.size()==2 && r[0]!=r[1]), "Wooops, $r is not a valid attribute renaming."
            assert (t.getSchema().contains(r[0])), "Wooooops, cannot rename ${r[0]} because it doesn't exist!"
        }

        for (row in t) {
            for (r in renamings){
                row[r[1]] = row[r[0]]
                row.remove(r[0])
            }
            tOut.addRow(row)
        }
        return tOut
    }

    public Table renameAll(Table t, String prefix, String suffix = ""){
        Table tOut = new Table()
        Map newRow
        for (row in t) {
            newRow = [:]
            for (a in row){
                def newName = a.key
                if (prefix) newName = prefix + "_" + newName
                if (suffix) newName = newName + "_" + suffix
                newRow[newName] = a.value
            }
            tOut.addRow(newRow)
        }
        return tOut
    }

    public Table tetaJoin(Table A, Table B, Closure condition){
        Table tOut = new Table()
        for (rA in A)
            for (rB in B){
                Map newRow = rA.plus(rB)
                if (condition(newRow)) tOut.addRow(newRow)
            }
        return tOut
    }

    public Table tetaLeftJoin(Table A, Table B, Closure condition){
        Table tOut = new Table()
        for (rA in A){
            def added = false
            for (rB in B){
                Map newRow = rA.plus(rB)
                if (condition(newRow)) {
                    tOut.addRow(newRow)
                    added = true
                }

            }
            if (!added) tOut.addRow(rA)
        }
        return tOut
    }

    public union (Table t1, Table t2){
        Table tOut = t1.copy()
        for (row in t2) tOut.addRow(row)
        return tOut
    }

    public unique (Table t){

        return t.unique()
    }

    Table reduceDeprecated(Table t, List <Map> reduceFunctions){
        //aggregation example:
        // [aggr:"min", func:"it.dist", as:"minDist"]

        Table tOut = new Table()

        if (! t.getSize()) return t

        t.orderAsc('id_n')

        //setup reduce functions
        for (f in reduceFunctions) {
            f.closure = this.sh.evaluate("{it -> " + f.func + "}")
            f.group = []
        }

        def i = 0
        def cRow = t.getRowAt(0)
        t.each{

            if ((it.id_n != cRow.id_n) ) { //|| (! t.iterator().hasNext())
                //for (row in t) setClosures.each{it(row)}
                for (f in reduceFunctions) {
                    cRow[f.as] = (f.group."${f.aggr}"())
                    f.group = []
                }
                tOut.addRow(cRow)
                cRow = it

            }

            for (f in reduceFunctions) f.group.add( f.closure(it))
        }

        //execute for the last row
        for (f in reduceFunctions)
            cRow[f.as] = (f.group."${f.aggr}"())
        tOut.addRow(cRow)


        return tOut
    }

}
