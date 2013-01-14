package br.unicamp.ic.lis.cdms.mappers

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.gremlin.groovy.Gremlin
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

        neo = new Neo4jGraph(this.neoGraphDB)

        registerShutdownHook( this.neoGraphDB );
        rank = params = regular = ""
        rankings = [:]
        totalWeight = 0

    }

    def parseQuery(query){
        def parser = new MapParser()

        this.parsedQuery = parser.parse(query)


        println("regular: " + this.parsedQuery.regular[0].value().trim())
        println "this.rankings = ${this.parsedQuery.ranking}"
    }

    def processQuery(query){
        parseQuery(query)

        this.parsedQuery.ranking.metric.each{
            processRegular()
            if (it.orig[0].@type == 'variable' && it.dest[0].@type == 'node'){ //first param is variable, second is node/id
                this.processMetric(it, getOrigs(it), getDest(it))
            }
            else{println "Invalid parameters (" + it + ")"}

        }

        def m = this.Results.sort{a,b -> b.value <=> a.value}
        //println "m: ${m}"
        //m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).map().Label}, ${value}"}
        m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().value}, ${value}"}

    }


    def getOrigs(context){
        Gremlin.load()
        def origs = []
        while(this.regResults.hasNext()) {
            def v = this.graph.v(this.regResults.next().getProperty(context.orig[0].@label).getId());
            origs.add(v);

        }
        return origs
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
