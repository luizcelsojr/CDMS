package br.unicamp.ic.lis.cdms.source

import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.algebra.OperatorsNeo4j
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import com.tinkerpop.gremlin.groovy.Gremlin
import com.tinkerpop.blueprints.impls.neo4j.*

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
class Neo4jDB extends Database {
    def neoGraphDB
    Neo4jGraph graph

    def Neo4jDB(db_path){
\

        this.neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
        this.graph = new Neo4jGraph(this.neoGraphDB)

        registerShutdownHook( this.neoGraphDB );
        //Gremlin.load()
    }

    static {
        Gremlin.load()
    }



    Operators getOperators(){
        return new OperatorsNeo4j(this)
    }

    Neo4jGraph G(){
        this.graph
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
