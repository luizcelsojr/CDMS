package br.unicamp.ic.lis.cdms.algebra

import br.unicamp.ic.lis.cdms.source.Neo4jDB
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants
import com.tinkerpop.gremlin.groovy.Gremlin

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/4/15
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
class OperatorsNeo4j extends Operators{
    Neo4jDB db
    GroovyShell sh = new GroovyShell()

    OperatorsNeo4j(Neo4jDB db){
        this.db = db
    }
    public Table select (Table t, Closure condition){
        Table t2 = new Table()
        for (row in t){
            if (condition(row)) t2.addRow(row)
        }
        return t2
    }

    def set (t, List setFunctions){
        //setup and execute set functions
        List setClosures = []
        for (f in setFunctions) setClosures.add(this.sh.evaluate("{it -> " + f + "}"))
        for (row in t) setClosures.each{it(row)}

    }

    Table reduce(Table t, List <Map> reduceFunctions){
        //aggregation example:
        // [aggr:"min", func:"it.dist", as:"minDist"]

        Table tOut = new Table()

        if (! t.getSize()) return t

        t.orderAsc('id')

        //setup reduce functions
        for (f in reduceFunctions) {
            f.closure = this.sh.evaluate("{it -> " + f.func + "}")
            f.group = []
        }

        def i = 0
        def cRow = t.getRowAt(0)
        t.each{

            if ((it.id != cRow.id) ) { //|| (! t.iterator().hasNext())
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

    public Table beta (Table t, Integer n, Closure condition, direction, follow, List setFunctions, List mapFunctions, List reduceFunctions, List updateFunctions){
        Table tNew

        Table tOut = t.copy()


        if (setFunctions) set(tOut, setFunctions)

        Table tLast = tOut.copy()

        //setup map functions
        List mapClosures = []
        for (f in mapFunctions) mapClosures.add(this.sh.evaluate("{it, e, c -> " + f + "}"))


        def newRow
        while (n-- > 0){
            tNew = new Table()
            for (row in tLast){
                def neighborEdges = []
                if (direction != Constants.OUTBOUND) neighborEdges.addAll(db.G().v(row.id).inE.filter{(follow)?it.label in follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                if (direction != Constants.INBOUND) neighborEdges.addAll(db.G().v(row.id).outE.filter{(follow)?it.label in follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

                for (ne in neighborEdges) {
                    newRow = row.clone()
                    newRow.id = ne[-1].id
                    //execute map functions
                    mapClosures.each{it(newRow,ne[1],neighborEdges.size())}

                    tNew.addRow(newRow)
                }

                //if (condition(row)) t2.addRow(row)
            }

            if (updateFunctions) set(tOut, updateFunctions)

            tOut = union(tOut, tNew)

            if (reduceFunctions) tOut = reduce(tOut, reduceFunctions )

            tLast = tNew //.copy()
        }

        return tOut
    }

    Table scanFilterV(Closure filter) {
        def V = new Table()

        def results = []
        db.G().V().filter(filter)_.each{
            def newV = it.map()
            newV['id'] = it.id
            V.addRow(newV)

        }
        //println results


        return V

    }

    Table scanFilterE(Closure filter) {
        def E = new Table()
        E.addRow([source:1, dest:1])

        println this.graph.E

        return E

    }

    static {
        Gremlin.load()
    }

}
