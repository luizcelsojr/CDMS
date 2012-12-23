import com.tinkerpop.blueprints.impls.tg.*
import com.tinkerpop.blueprints.oupls.sail.*
import com.tinkerpop.gremlin.groovy.Gremlin
import br.unicamp.ic.lis.cdms.*


def getSailConn(){
	
	sail = new GraphSail(new TinkerGraph());
	sail.initialize();
	sc = sail.getConnection();
	
	vf = sail.getValueFactory();
	sc.addStatement(vf.createURI("http://tinkerpop.com#1"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("marko"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#1"), vf.createURI("http://tinkerpop.com#age"), vf.createLiteral(29), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#2"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("vadas"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#2"), vf.createURI("http://tinkerpop.com#age"), vf.createLiteral(27), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#3"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("lop"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#3"), vf.createURI("http://tinkerpop.com#lang"), vf.createLiteral("java"), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("josh"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com#age"), vf.createLiteral(32), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#5"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("ripple"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#5"), vf.createURI("http://tinkerpop.com#lang"), vf.createLiteral("java"), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#6"), vf.createURI("http://tinkerpop.com#name"), vf.createLiteral("peter"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#6"), vf.createURI("http://tinkerpop.com#age"), vf.createLiteral(35), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#1"), vf.createURI("http://tinkerpop.com#knows"), vf.createURI("http://tinkerpop.com#2"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#1"), vf.createURI("http://tinkerpop.com#knows"), vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com#knows"), vf.createURI("http://tinkerpop.com#6"), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#1"), vf.createURI("http://tinkerpop.com#created"), vf.createURI("http://tinkerpop.com#3"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com#created"), vf.createURI("http://tinkerpop.com#3"), vf.createURI("http://tinkerpop.com"));
	sc.addStatement(vf.createURI("http://tinkerpop.com#6"), vf.createURI("http://tinkerpop.com#created"), vf.createURI("http://tinkerpop.com#3"), vf.createURI("http://tinkerpop.com"));
	
	sc.addStatement(vf.createURI("http://tinkerpop.com#4"), vf.createURI("http://tinkerpop.com#created"), vf.createURI("http://tinkerpop.com#5"), vf.createURI("http://tinkerpop.com"));
	
	sc;
}

def getGraph(){
	g = TinkerGraphFactory.createTinkerGraph()
	g.addEdge(g.v(4),g.v(6),'knows')
	g
}

Gremlin.load()

//def RunQuery(args){
	def cli = new CliBuilder(usage: 'RunQuery.groovy -[hlf] [query]')
	// Create the list of options.
	cli.with {
		h longOpt: 'help', 'Show usage information'
		l longOpt: 'language', args: 1, argName: 'language', 'Sets query language to "language"'
		f longOpt: 'input-file', args: 1, argName: 'file', 'Read query from "file"'
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
	def file = "/home/lis/luizcelso/phd/workspace/GIRDB/queries/query.txt"  // Default.
	if (options.f) { 
		file = options.f
	} else if (options.l) {
		language = options.l
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
	
	sc = getSailConn();
	g = getGraph();
	
	qp = new SparqlQueryProc(g, sc)
	
	
	qp.processQuery(query)
	
	
//}


//RunQuery(args)



