package br.unicamp.ic.lis.cdms.mappers

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
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
