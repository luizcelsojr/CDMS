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

    public Table beta (Table t, Closure condition, direction, follow, Closure set){
        Table t2 = new Table()

        for (row in t) set(row)

        def newRow
        for (row in t){
/*            db.G().v(row.id).both._.each{
                newRow = row.clone()
                newRow.id = it.id
                t2.addRow(newRow)
            }

*/
            def neighborEdges = []
            if (direction != Constants.OUTBOUND) neighborEdges.addAll(db.G().v(row.id).inE.filter{(follow)?it.label in follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
            if (direction != Constants.INBOUND) neighborEdges.addAll(db.G().v(row.id).outE.filter{(follow)?it.label in follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

            for (n in neighborEdges) {
                newRow = row.clone()
                newRow.id = n[-1].id
                t2.addRow(newRow)
            }

            //if (condition(row)) t2.addRow(row)
        }
        return union(t, t2)


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
