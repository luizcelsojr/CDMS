package br.unicamp.ic.lis.cdms

import br.unicamp.ic.lis.cdms.benchmark.Timer
import br.unicamp.ic.lis.cdms.queryproc.BetaQueryProc
import br.unicamp.ic.lis.cdms.queryproc.CypherPlusQueryProc
import br.unicamp.ic.lis.cdms.queryproc.QueryProcessor
import br.unicamp.ic.lis.cdms.source.Table
import com.tinkerpop.blueprints.impls.tg.*
import com.tinkerpop.gremlin.groovy.Gremlin
import groovy.transform.Field
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

/*
import virtuoso.sesame2.driver.VirtuosoRepository


def getSailConn(){
	def VIRTUOSO_INSTANCE = "gaponga";
	def VIRTUOSO_PORT = 1111;
	def VIRTUOSO_USERNAME = "dba";
	def VIRTUOSO_PASSWORD = "dba";

	def repository = new VirtuosoRepository("jdbc:virtuoso://" + VIRTUOSO_INSTANCE + ":" + VIRTUOSO_PORT, VIRTUOSO_USERNAME, VIRTUOSO_PASSWORD);
	repository.initialize()

	RepositoryConnection con = null;
	con = repository.getConnection();

	def sail = new RepositorySail(repository, false);

	sc = sail.getConnection();

	sc;
}

def getSail(){
	def VIRTUOSO_INSTANCE = "gaponga";
	def VIRTUOSO_PORT = 1111;
	def VIRTUOSO_USERNAME = "dba";
	def VIRTUOSO_PASSWORD = "dba";

	def repository = new VirtuosoRepository("jdbc:virtuoso://" + VIRTUOSO_INSTANCE + ":" + VIRTUOSO_PORT, VIRTUOSO_USERNAME, VIRTUOSO_PASSWORD);
	repository.initialize()

	def sail = new RepositorySail(repository, false);

	sail;
}

 */

def getGraph(){

	g = TinkerGraphFactory.createTinkerGraph()
	g.addEdge(g.v(4),g.v(6),'knows')
	g
}


Gremlin.load()

@Field static GraphDatabaseService neoDB = null

void connectNeoDB(db_path){
    if (!neoDB) {
        neoDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)

    }
}



def runFromCommandLine(args){


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
    def file = '/Users/luizcelso/workspace/CDMS/queries/query-cypher-rel-symdia_cases.groovy'  // Default.
    def db_path = "/Users/luizcelso/db/symdia_cases2"

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

    def f = new File(file)

    query = f.text

    run(db_path, language, query)

}

Table run(db_path, language, query){
    QueryProcessor qp
    connectNeoDB(db_path)

    switch ( language ) {
/*		case "sparql":

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
			break   */
        case "cypher":
            qp = new CypherPlusQueryProc(neoDB)
            break
        case "beta":
            qp = new BetaQueryProc(neoDB)
            break
    }

    Table result

    def time = Timer.closureBenchmark{result = qp.processQuery(query)}
    println "total time: ${time} --> ${time/1000/60/60}h ${time/1000/60}min"

    return result

}


runFromCommandLine(args)


/*
  
-l sparql -f /Users/luizcelso/workspace/CDMS/queries/query-sparql-rel-actors-woody.groovy
-l cypher -f /Users/luizcelso/workspace/CDMS/queries/query-cypher-rel-symdia_cases.groovy -d /Users/luizcelso/db/symdia_cases2


java -jar ~/Dropbox/workspace/GIRDB/target/GIRDB-0.0.1-SNAPSHOT.jar -l cypher -f /home/lis/luizcelso/phd/workspace/GIRDB/queries/query-cypher-rel-symdia_cases.txt -d /lishome-ext/luizcelso/graphdbs/symdia_cases
java -jar /home/lis/luizcelso/workspace/CDMS/target/CDMS-0.0.1-SNAPSHOT.jar -l cypher -f /home/lis/luizcelso/workspace/CDMS/queries/querymap-cypher-lucene-movie-keyword-genra.groovy -d /lishome-ext/serverdata/linkedimdb/neoLMDB


 */
