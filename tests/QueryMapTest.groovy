/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/9/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */

import br.unicamp.ic.lis.cdms.CypherPlusQueryProc
import br.unicamp.ic.lis.cdms.mappers.MapperProc

class QueryMapTest extends GroovyTestCase {
    //def db_path = '/Users/luizcelso/db/LMDBsample'
    //def db_path = '/home/lis/luizcelso/workspace/neo4j-sail-test/var/LMDBsampleMapped'
    def db_path = '/lishome-ext/serverdata/linkedimdb/neoLMDB'
    def graph = null

    void setUp(){

    }


    void testQueryMap (){
        //Gremlin.load()
        def queryf = '/Users/luizcelso/workspace/CDMS/queries/querymap-cypher-lucene-movie-keyword.groovy'
        def qp = new CypherPlusQueryProc(db_path)

        qp.processQuery(queryf)



    }


}
