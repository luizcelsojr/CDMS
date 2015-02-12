package br.unicamp.ic.lis.cdms.queryproc

import br.unicamp.ic.lis.cdms.source.Table
import org.neo4j.graphdb.GraphDatabaseService

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/8/15
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */

abstract class QueryProcessor
{
    def neoGraphDB
    public QueryProcessor(GraphDatabaseService neoDB) {
        this.neoGraphDB = neoDB
        registerShutdownHook( neoDB )
    }

    public abstract Table processQuery(query)

    private static void registerShutdownHook( GraphDatabaseService graphDb )
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
