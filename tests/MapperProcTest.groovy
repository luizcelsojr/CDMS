/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/9/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
import br.unicamp.ic.lis.cdms.mappers.LuceneMapper
import br.unicamp.ic.lis.cdms.mappers.MapperProc
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.gremlin.groovy.Gremlin
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

class MapperProcTest   extends GroovyTestCase{
    def db_path = '/Users/luizcelso/Dropbox/db/geoinfo-rest'
    //-l cypher -f /Users/luizcelso/Dropbox/workspace/CDMS/queries/map-cypher-lucene-geoinfo.groovy -d /Users/luizcelso/Dropbox/db/geoinfo-rest
    //def db_path = '/home/lis/luizcelso/workspace/neo4j-sail-test/var/LMDBsample'
    //def db_path = '/lishome-ext/serverdata/linkedimdb/neoLMDB'
    def graph = null

    void setUp(){

    }


    void testMapperProc (){
        //Gremlin.load()
        MapperProc mp = new MapperProc(this.db_path)

        def queryf = '/Users/luizcelso/Dropbox/workspace/CDMS/queries/map-cypher-lucene-geoinfo.groovy'

        mp.processQuery(queryf)


    }


}
