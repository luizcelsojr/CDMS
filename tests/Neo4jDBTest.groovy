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
    def db_path = '/Users/luizcelso/Dropbox/db/geoinfo-rest'

    void setUp(){

    }


    void testNeo4jDB(){
        def db = new Neo4jDB(this.db_path)

        Operators opr = db.getOperators()
        //parser =
        //engine.execute(query, parser, operators)

        Table t

        t = opr.scanFilterV({true})
        t.print()

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

        */

        t = opr.scanFilterV({it.id==2})
        println '=>t'
        t.print()
        println 'xxxxxxx'

        t = opr.beta(t, {true}, Constants.BOTH, ['connects', 'knows'], {it.dist = 0})

        t.print()
        println 'xxxxxxx'


        assertEquals('cypher', 'cypher')


    }
}
