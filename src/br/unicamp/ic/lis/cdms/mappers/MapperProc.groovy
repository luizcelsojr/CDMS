package br.unicamp.ic.lis.cdms.mappers

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.gremlin.groovy.Gremlin
import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/11/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
class MapperProc {

    def rank, params, regular, rankings, totalWeight
    def neoGraphDB, graph
    def regResults //results from regular query
    def parsedQuery


    def MapperProc(db_path){
        this.neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
        this.graph = new Neo4jGraph(this.neoGraphDB)

        registerShutdownHook( this.neoGraphDB );
        rank = params = regular = ""
        rankings = [:]
        totalWeight = 0

    }

    def parseQuery(query){
        def parser = new MapParser()

        this.parsedQuery = parser.parse(query)


        println("regular: " + this.parsedQuery.regular[0].value().trim())
        println "this.mapping = ${this.parsedQuery.mapping}"
    }

    def processQuery(query){
        parseQuery(query)
        processRegular()

        def mapperName = this.parsedQuery.mapping.mapper[0].@type

        println mapperName

        def mapperClass = 'br.unicamp.ic.lis.cdms.mappers.' + mapperName as Class
        def mapper = mapperClass.newInstance(this.graph)

        println mapper.class.name

        process(this.parsedQuery.mapping.mapper[0], mapper)

    }


    def process(context, mapper){
        Gremlin.load()
        println context.parameter[0].@value


        def origs = []
        while(this.regResults.hasNext()) {
            def v = this.graph.v(this.regResults.next().getProperty(context.parameter[0].@value).getId());
            println v

            mapper.map(v)

            v.outE('LuceneMapper:hasToken').inV.each{
                println "${it} - - ${it.map()}"
            }
        }

        mapper.commit()
    }

    def processRegular() {

        ExpandoMetaClass.enableGlobally()
        scala.collection.immutable.Map.metaClass.getProperty = {name -> delegate.get(name).get()}
        //Map2.metaClass.setProperty = {name, val -> delegate.setProperty(name, val)}

        def engine = new ExecutionEngine( this.neoGraphDB );

        this.regResults = engine.execute(this.parsedQuery.regular[0].value().trim());
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
