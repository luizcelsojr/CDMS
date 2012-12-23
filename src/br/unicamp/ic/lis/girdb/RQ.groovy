package br.unicamp.ic.lis.girdb

import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.sail.*
import com.tinkerpop.blueprints.impls.tg.*
import com.tinkerpop.blueprints.oupls.sail.*
import org.openrdf.query.parser.sparql.*
import org.openrdf.query.impl.*
import com.tinkerpop.gremlin.groovy.Gremlin
import groovy.util.CliBuilder
import net.fortytwo.sesametools.reposail.RepositorySail
//import virtuoso.sesame2.driver.VirtuosoRepository
import com.tinkerpop.blueprints.impls.sail.SailGraph
import br.unicamp.ic.lis.girdb.CypherPlusQueryProc
import br.unicamp.ic.lis.girdb.SparqlQueryProc

def getSailConn(){
	def VIRTUOSO_INSTANCE = "gaponga";
	def VIRTUOSO_PORT = 1111;
	def VIRTUOSO_USERNAME = "dba";
	def VIRTUOSO_PASSWORD = "dba";
/*
	def repository = new VirtuosoRepository("jdbc:virtuoso://" + VIRTUOSO_INSTANCE + ":" + VIRTUOSO_PORT, VIRTUOSO_USERNAME, VIRTUOSO_PASSWORD);
	repository.initialize()

	RepositoryConnection con = null;
	con = repository.getConnection();

	def sail = new RepositorySail(repository, false);

	sc = sail.getConnection();

	sc;*/
}

def getSail(){
	def VIRTUOSO_INSTANCE = "gaponga";
	def VIRTUOSO_PORT = 1111;
	def VIRTUOSO_USERNAME = "dba";
	def VIRTUOSO_PASSWORD = "dba";
 /*
	def repository = new VirtuosoRepository("jdbc:virtuoso://" + VIRTUOSO_INSTANCE + ":" + VIRTUOSO_PORT, VIRTUOSO_USERNAME, VIRTUOSO_PASSWORD);
	repository.initialize()

	def sail = new RepositorySail(repository, false);

	sail;  */
}

def getGraph(){

	g = TinkerGraphFactory.createTinkerGraph()
	g.addEdge(g.v(4),g.v(6),'knows')
	g
}


Gremlin.load()

def RunQ(args){
    def cli = new CliBuilder(usage: 'RunQuery.groovy -[hlf] [query]')
    // Create the list of options.
    cli.with {
        h longOpt: 'help', 'Show usage information'
        l longOpt: 'language', args: 1, argName: 'language', 'Sets query language to "language"'
        f longOpt: 'input-file', args: 1, argName: 'file', 'Read query from "file"'
        d longOpt: 'db-path', args: 1, argName: 'path', 'Neo4j database path'
    }

    def options = cli.parse(args)
    if (!options) {
        return
    }
    // Show usage text when -h or --help option is used.
    if (options.h) {
        cli.usage()
        return
    }

    // Determine formatter.
    def language = "cypher"  // Default.
    def file = "/home/lis/luizcelso/phd/workspace/GIRDB/queries/query-cypher-rel-symdia_cases.txt"  // Default.
    def db_path = "/lishome-ext/luizcelso/graphdbs/symdia_cases"

    if (options.f) {
        file = options.f
    }
    if (options.l) {
        language = options.l
    }
    if (options.d) {
        db_path = options.d
    }
    // Handle all non-option arguments.
    def query = ''  // Default is empty query.
    def extraArguments = options.arguments()
    if (extraArguments) {
        query = extraArguments.join(' ')
    }




    myFile = new File(file)

    if (query == "") {
        for (line in myFile) {query+="\n"+line}
    }

    def qp

	switch ( language ) {
		case "sparql":
		/*
			def VIRTUOSO_INSTANCE = "gaponga";
			def VIRTUOSO_PORT = 1111;
			def VIRTUOSO_USERNAME = "dba";
			def VIRTUOSO_PASSWORD = "dba";
		
			def repository = new VirtuosoRepository("jdbc:virtuoso://" + VIRTUOSO_INSTANCE + ":" + VIRTUOSO_PORT, VIRTUOSO_USERNAME, VIRTUOSO_PASSWORD);
			repository.initialize()
		
			def sail = new RepositorySail(repository, false);
			
			sc = sail.getConnection()
			
			rc = repository.getConnection();

			g = new SailGraph(sail)
			
			qp = new SparqlQueryProc(g, sc, rc)
			break */
		case "cypher":
			qp = new CypherPlusQueryProc(db_path)
			//nc.shutdown();
			break
	}


	qp.processQuery(query)


}


RunQ(args)


/*
  
-l sparql -f /home/lis/luizcelso/phd/workspace/GIRDB/queries/query-sparql-rep.txt
-l cypher -f /home/lis/luizcelso/phd/workspace/GIRDB/queries/query-cypher-rec.txt -d /lishome-ext/luizcelso/graphdbs/fb_likes_copy


java -jar ~/Dropbox/workspace/GIRDB/target/GIRDB-0.0.1-SNAPSHOT.jar -l cypher -f /home/lis/luizcelso/phd/workspace/GIRDB/queries/query-cypher-rel-symdia_cases.txt -d /lishome-ext/luizcelso/graphdbs/symdia_cases


 */
