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
        Table tOut = new Table()
        Map newRow
        for (row in t) {
            newRow = [:]
            for (c in columns) newRow[c] = row[c]
            tOut.addRow(newRow)
        }
        return tOut
    }

    Table stepOut(Table V, Table E){
        Table tOut = new Table()

        E = rename(E, [["id", "E_id"], ["id_n", "E_id_n"]])

        tOut =  tetaJoin(V, E, {it.id_n == it.E_id})
        tOut = rename(tOut, [["E_id_n", "id_n"]])

        return tOut

    }

    Table stepIn(Table V, Table E){
        Table tOut = new Table()

        E = rename(E, [["id", "E_id"], ["id_n", "E_id_n"]])

        tOut =  tetaJoin(V, E, {it.id_n == it.E_id_n})
        tOut = rename(tOut, [["E_id", "id_n"]])

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



        if (direction == Constants.INBOUND) tOut =  stepIn(vIn, E)
        else if (direction == Constants.OUTBOUND) tOut =  stepOut(vIn, E)
        else if (direction == Constants.BOTH) tOut =  union(stepIn(vIn, E), stepOut(vIn, E))


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

    Table stepMap(Table V, Table E,  direction, List mapFunctions){

    }


    Table reduce(Table t, List <Map> reduceFunctions){
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

    //public Table beta (Table t, Integer n, Closure condition, direction, follow, List set, List mapFunctions, List reduceFunctions, List updateFunctions) {}

    public Table beta (Table t, Table e, Integer n, Closure condition, direction, follow, List setFunctions, List mapFunctions, List reduceGroupBy, List reduceFunctions, List updateFunctions) {
        //TODO: check schemas
        if (n < 1) return t

        Table tNew = new Table()

        Table tOut = t.copy()


        //if (setFunctions) set(tOut, setFunctions.add('it.id_n = it.id'))
        if (setFunctions) tOut = set(tOut, setFunctions)

        tOut = set(tOut, ['it.id_n = it.id'])

        Table tLast = tOut.copy()

        //setup map functions
        //List mapClosures = []
        //for (f in mapFunctions) mapClosures.add(this.sh.evaluate("{it, e, c -> " + f + "}"))


        while (n-- > 0){
            tNew.print()
            tNew = step(tLast, e, direction)
            tNew.print()
            if (mapFunctions) tNew = map(tNew, mapFunctions) //map
            tNew.print()

            if (updateFunctions) tOut = set(tOut, updateFunctions)
            tOut.print()

            //if (reduceFunctions) tNew = reduce(tNew, reduceGroupBy, reduceFunctions )

            tOut = union(tOut, tNew)
            tOut.print()

            if (reduceFunctions) tOut = reduce(tOut, reduceGroupBy, reduceFunctions )
            tOut.print()

            tLast = tNew.copy()//tOut.copy()
        }

        return tOut

    }



    Table reduce(Table t, List group, List <Map> reduceFunctions){
        //aggregation example:
        // [aggr:"min", func:"it.dist", as:"minDist"]

        if (! t.getSize()) return t

        Table tOut = new Table()

        t.orderAsc(group)



        //setup reduce functions
        for (f in reduceFunctions) {
            f.closure = this.sh.evaluate("{it -> " + f.func + "}")
            f.group = []
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


        return tOut
    }

    public Table rename(Table t, List renamings){
        Table tOut = new Table()
        for (r in renamings) assert (r.size()==2 && r[0]!=r[1]), "Wooops, $r is not a valid attribute renaming."

        for (row in t) {
            for (r in renamings){
                row[r[1]] = row[r[0]]
                row.remove(r[0])
            }
            tOut.addRow(row)
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

    public union (Table t1, Table t2){
        Table tOut = t1.copy()
        for (row in t2) tOut.addRow(row)
        return tOut
    }

    public unique (Table t){

        return t.unique()
    }


}
