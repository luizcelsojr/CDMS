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

    public Table beta (Table t, Integer n, Closure condition, direction, follow, List setFunctions, List mapFunctions, List reduceFunctions, List updateFunctions){
        return beta (t, n, condition, direction, follow, setFunctions, mapFunctions, ['id_n'], reduceFunctions, updateFunctions)
    }
    public Table beta (Table t, Integer n, Closure condition, direction, follow, List setFunctions, List mapFunctions, List reduceGroupBy, List reduceFunctions, List updateFunctions){
        if (n < 1) return t

        Table tNew

        Table tOut = t.copy()


        //if (setFunctions) set(tOut, setFunctions.add('it.id_n = it.id'))
        if (setFunctions) tOut = set(tOut, setFunctions)

        tOut = set(tOut, ['it.id_n = it.id'])

        Table tLast = tOut.copy()

        //setup map functions
        List mapClosures = []
        for (f in mapFunctions) mapClosures.add(this.sh.evaluate("{it, e, c -> " + f + "}"))


        def newRow
        while (n-- > 0){
            tNew = new Table()
            for (row in tLast){
                def neighborEdges = []
                if (direction != Constants.OUTBOUND) neighborEdges.addAll(db.G().v(row.id_n).inE.filter{(follow)?it.label in follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                if (direction != Constants.INBOUND) neighborEdges.addAll(db.G().v(row.id_n).outE.filter{(follow)?it.label in follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

                for (ne in neighborEdges) {
                    newRow = row.clone()
                    newRow.id_n = ne[-1].id

                    for (attribute in ne[-1].map()){
                        def attributeName = attribute.key + "_n"
                        newRow."$attributeName" = attribute.value
                    }

                    //execute map functions
                    mapClosures.each{it(newRow,ne[1],neighborEdges.size())}

                    tNew.addRow(newRow)
                }

                //if (condition(row)) t2.addRow(row)
            }

            if (updateFunctions) tOut = set(tOut, updateFunctions)

            tOut = union(tOut, tNew)

            if (reduceFunctions) tOut = reduce(tOut, reduceGroupBy, reduceFunctions )

            tLast = tNew//.copy()
        }

        return tOut
    }

    Table scanFilterV(Closure filter) {
        def V = new Table()

        db.G().V().filter(filter)_.each{
            Map newV = it.map()
            newV['id'] = it.id
            V.addRow(newV)

        }
        return V

    }

    Table scanFilterE(Closure filter) {
        def E = new Table()

        db.G().E().filter(filter)_.each{
            Map newE = it.map()
            //TODO: lowercase everything
            newE['id'] = it.outV.id.next()
            newE['id_n'] = it.inV.id.next()
            newE['label'] = it.label
            E.addRow(newE)
        }

        return E

    }

    static {
        Gremlin.load()
    }

}
