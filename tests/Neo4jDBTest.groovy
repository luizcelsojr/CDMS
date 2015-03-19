/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */


import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.source.Neo4jDB
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants

class Neo4jDBTest extends GroovyTestCase {
    //def db_path = '/Users/luizcelso/Dropbox/db/geoinfo'
    def db_path = '/Users/luizcelso/Dropbox/db/food'

    void setUp(){

    }


    void testNeo4jDB(){
        def db = new Neo4jDB(this.db_path)

        Operators opr = db.getOperators()
        //parser =
        //engine.execute(query, parser, operators)



        println 'xxxxxxx'
      /*
        t = opr.scanFilterV({it.type == 'person' && it.id > 14})
        t.print()

        println 'xxxxxxx'
        assertEquals(t.getSize(), 8)

        t = opr.select(t, {it.id == 17 || it.Label == 'Celso'})
        t.print()

        println 'xxxxxxx'
        assertEquals(t.getSize(), 2)


        ==== pagerank
        Table t
        t = opr.scanFilterV({it.id > 15 && it.id < 20})
        Table t2
        t2 = opr.beta(t, 3, {true}, Constants.BOTH, ['knows'], ["it.rank = 100.0f"], ["it.rank = it.rank/c"], [[aggr:"sum", func:"it.rank", as:"rank"]], ["it.rank = 0.0f"])
        t2.orderAsc('rank')

        ==== min/max distances
        Table t
        t = opr.scanFilterV({it.id==3 || it.id==4})
        t = opr.beta(t, 3, {true}, Constants.OUTBOUND, ['connects'], ["it.dist = 0.0f"], ["it.dist = it.dist + e.Weight.toFloat()"], ["id", "id_n"], [[aggr:"min", func:"it.dist", as:"minDist"], [aggr:"max", func:"it.dist", as:"maxDist"]], [])
        t = opr.project(t, ["id", "id_n", "dist", "minDist", "maxDist", "type_n"])
        t.orderAsc('id')
        t.print()


        */

        //relevance
        //Table t = opr.beta(r, 2, { true }, Constants.BOTH, [], ["it.rank = 1.0f"], ["it.rank = 0.8* it.rank/it.c"], ['id_n'], [[aggr: "sum", func: "it.rank", as: "rank"]], [])


        Table r = opr.scanFilterV({it.type=='CUISINE'})
        Table t = opr.beta(r, 2, { true }, Constants.INBOUND, [], ["it.rank = 1.0f"], ["it.rank = 0.8* it.rank/c"], ['id_n', 'id'], [[aggr: "sum", func: "it.rank", as: "rank"]], ["current.rank = newV.rank + current.rank"])
        t = opr.select(t, {it.id_n == 1283})
        t.orderDesc('rank')
        //t = opr.project(t, ["V_id", "V_Label", "rank"])
        t.print()



    }
}
