/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/9/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */

import br.unicamp.ic.lis.cdms.mappers.*
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.gremlin.groovy.Gremlin
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

class MapperTest extends GroovyTestCase {

    def graph = null

    void setUp(){

        def db_path = '/Users/luizcelso/db/LMDBsample'
        def neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
        this.graph = new Neo4jGraph(neoGraphDB)

        registerShutdownHook( neoGraphDB );

    }


    void testLucene (){
        Gremlin.load()
        def mapper = new LuceneMapper(this.graph)

        mapper.map(this.graph.v(4))
//        mapper.index.get('token', 'silence').each{println "hello ${it}"}

        this.graph.v(4).outE('LuceneMapperIdx:hasToken').inV.each{
            println it
        }

//        mapper.rollback()
        mapper.commit()
//        print mapper.index.getIndexName()
//        mapper.index.get('token', 'silence').each{println "wont say ${it}"}



    }


    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

}
