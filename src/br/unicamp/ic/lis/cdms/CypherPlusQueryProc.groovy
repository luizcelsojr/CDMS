package br.unicamp.ic.lis.cdms

import br.unicamp.ic.lis.cdms.queryproc.Parser
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import com.tinkerpop.blueprints.impls.neo4j.*
import scala.collection.immutable.Map;

import com.tinkerpop.gremlin.groovy.Gremlin



class CypherPlusQueryProc{

	def rank, params, cypher, rankings, totalWeight
	def neoGraphDB, graph
	def regResults //results from regular query
	def Results = [:].withDefault{0.0} //results from ranking
    def parsedQuery

	def benchmark = { closure ->
		def start, now
		start = System.currentTimeMillis()
		closure.call()
		now = System.currentTimeMillis()
		now - start
	}

	def CypherPlusQueryProc(db_path){
		
		this.neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
		this.graph = new Neo4jGraph(this.neoGraphDB)
		
		registerShutdownHook( this.neoGraphDB );
		rank = params = cypher = ""
		rankings = [:]
		totalWeight = 0
		
	}

	
	def parseQuery(query){

        def parser = new Parser()

        this.parsedQuery = parser.parse(query)

        this.parsedQuery.ranking.metric.each{
            this.totalWeight += it.@weight.toFloat()
        }

		println("cypher: " + cypher)
		println "this.rankings = ${this.parsedQuery.ranking}"
//		System.exit(1)
		

	}

	def processQuery(query){


		parseQuery(query)

		//processCypher()

        this.parsedQuery.ranking.metric.each{
            processCypher()
            this."processQuery${it.@type}"(it)
        }
/*
		this.rankings.each{ key, value ->
			processCypher()
			switch(key.toLowerCase()){
				case "relevance":
					processQueryRelevance()
					break
				case "wrelevance":
					processQueryWRelevance(this.rankings[key])
					break
				case "connectivity":
					processQueryConnectivity()
					break
				case "wconnectivity":
					processQueryWConnectivity(this.rankings[key])
					break
				case "reputation":
					processQueryReputation()
					break
				case "influence":
					processQueryInfluence()
					break

				default:
					println	"Invalid Ranking Operation"
			}
		}
*/
		def m = this.Results.sort{a,b -> b.value <=> a.value}
		//println "m: ${m}"
		m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).map().Label}, ${value}"}
		
	}

	def processQueryRelevance(context){
		Gremlin.load()
		println "rank: weight=${context.@weight} RELEVANCE context: " + context;


		if (context.orig[0].@type == 'variable' && context.dest[0].@type == 'node'){ //first param is variable, second is node/id
			def destid = context.dest[0].@id
			def dest = this.graph.v(destid)
			def origs = []
            def weightProp = "Weight"


			
			while(this.regResults.hasNext()) {
				def v = this.graph.v(this.regResults.next().getProperty(context.orig[0].@label).getId());
				origs.add(v);
				
			}


			def A = [:].withDefault{0}
			def R = [:] //results
			def maxResult = 0.0

			
			def t = 0.1 //activation threshold
			def d = 0.9 //decay factor

			println "Relevance - " + origs + " --> " + dest

			def m = [:].withDefault{0}
			def p = [:]
			
			def NOTFOLLOW = ["http://www.w3.org/1999/02/22-rdf-syntax-ns#type"]
			
			//def paths = origs._().both.simplePath().loop(2){it.loops<=3}{(it.object.id==dest.id)}.path().toList()

			def duration = benchmark {
				origs.each{
						A = [:].withDefault{0}
						A[it] = 100
						
						it.as('start')
							.filter{
									//if (A[it] <= t) println "No no for ${it} with ${A[it]}";
									A[it] > t}
							.transform{
								def neighbors = it.inE.outV.path().toList()
								//println neighbors[0][1].Weight.toFloat()
								//System.exit(1)
								
								def n = neighbors.size()
								if (n > 1000 | n == 0) {//println "vaza ${it}.....";
									return []}
								def Atransfer = (A[it] * d)/n
								neighbors.each{
									A[it[-1]] += Atransfer * it[1].getProperty(weightProp).toFloat() // it is the path, it[-1] is the outV
								}
								if (n) A[it] = 0
								//println "A ${A}"
								neighbors.collect{it[-1]}
							}.scatter
							.filter{it.id!=destid}
							.loop('start'){it.loops<=3}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";
							
							//println "vai ${dest} ${A[dest]}"; if (it.object.id==destid) println "yeahhhhh! ${A[dest]} ${it.object}";
							//					.except([dest])
							
						if (A[dest] > maxResult) maxResult = A[dest]
						
						R[it] = A[dest]
						//println A
						//println R
						//System.exit(1)

				}
				
				def normWeight = context.@weight/this.totalWeight
				
				R.each{ key, value ->
					this.Results[key] = this.Results[key] + ((value/maxResult) * normWeight)
				}


			}
			println "execution took ${duration} ms"

		}
		else{println "Invalid parameters (" + params + ")"}
	}

	def processQueryWConnectivity(context){
		Gremlin.load()
		println "rank: WCONN params: "  + context.params;
		def plist = context.params.split(",").collect{it.trim()}
		

		if (!plist[0].startsWith("_") && (plist[1].startsWith("_"))){ //first param is variable, second is id
			def destid = plist[1][1..-1] //remove <> from id
			def dest = this.graph.v(destid)
			def origs = []
			
			while(this.regResults.hasNext()) {
				def v = this.graph.v(this.regResults.next().getProperty(plist[0]).getId());
				origs.add(v);
				
			}
			
//def g = TinkerGraphFactory.createTinkerGraph()
//origs = [g.v(4), g.v(1), g.v(5), g.v(3), g.v(6)]
//dest = g.v(2)


			def A = [:].withDefault{0}
			def R = [:] //results
			def maxResult = 0.0
			
			//origs.each{A[it] = 1}
			
			def t = 0.1 //activation threshold
			def d = 0.9 //decay factor

			println "Relevance - " + origs + " --> " + dest

			def m = [:].withDefault{0}
			def p = [:]
			
			def NOTFOLLOW = ["http://www.w3.org/1999/02/22-rdf-syntax-ns#type"]
			
			//def paths = origs._().both.simplePath().loop(2){it.loops<=3}{(it.object.id==dest.id)}.path().toList()

			def duration = benchmark {
				origs.each{

						A = [:].withDefault{0}
						A[it] = 100
						
						it.as('start')
							.filter{
									//if (A[it] <= t) println "No no for ${it} with ${A[it]}";
									A[it] > t}
							.transform{
								def neighbors = it.inE.outV.path().toList()
								//println neighbors[0][1].Weight.toFloat()
								//System.exit(1)
								
								def n = neighbors.size()
								if (n > 1000 | n == 0) {//println "vaza ${it}.....";
									return []}
								def Atransfer = (A[it] * d)
								neighbors.each{
									A[it[-1]] += Atransfer * it[1].Weight.toFloat() // it is the path, it[-1] is the outV
								}
								if (n) A[it] = 0
								//println "A ${A}"
								neighbors.collect{it[-1]}
							}.scatter
							.filter{it.id!=destid}
							.loop('start'){it.loops<=3}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";
							
							//println "vai ${dest} ${A[dest]}"; if (it.object.id==destid) println "yeahhhhh! ${A[dest]} ${it.object}";
							//					.except([dest])
							
						if (A[dest] > maxResult) maxResult = A[dest]
						
						R[it] = A[dest]
						//println A
						//println R
						//System.exit(1)

				}

				
				def normWeight = context.weight/this.totalWeight
				
				R.each{ key, value ->
					this.Results[key] = this.Results[key] + ((value/maxResult) * normWeight)
				}


			}
			println "execution took ${duration} ms"

		}
		else{println "Invalid parameters (" + params + ")"}
	}
	
	def processQueryRelvance_Old(){
		Gremlin.load()
		println "rank: RELEVANCE params: " + params;
		def plist = this.params.split(",").collect{it.trim()}
		

		if (!plist[0].startsWith("_") && (plist[1].startsWith("_"))){ //first param is variable, second is id
			
			def dest = null
			def origs = []
			
			while(this.regResults.hasNext()) { 
				def v = this.graph.v(this.regResults.next().getProperty(plist[0]).getId()); 
				origs.add(v);
				if (!dest) dest = this.graph.v(plist[1][1..-1])
				
			}
			println "Relevance - " + origs + " --> " + plist[1]


			def m = [:].withDefault{0}
			def p = [:]
			def paths = origs._().both.simplePath().loop(2){it.loops<=3}{(it.object.id==dest.id)}.path().toList()

			paths.each{
				println it;
				def rank=1;
				for (v in it) {
					rank=rank/v.both.count();
				}
				rank = rank/it.size();
				if (m[it[0]] < rank){
					m[it[0]] = rank;
					p[it[0]] = it;
				}
			}
			m = m.sort{a,b -> b.value <=> a.value}
			m.each{println it.toString() + " - " + p[it.key].toString()}
			m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).map().label}, ${value}"}
		}
		else{println "Invalid parameters (" + params + ")"}
	}


    def processQueryInfluence(){
        println "rank: INFLUENCE params: " + params;
        def plist = this.params.split(",").collect{it.trim()}

        if (plist[0].startsWith("?")){ //first param is variable
            def origs = []

            while(this.regResults.hasNext()) { def v = this.neoGraphDB.getNodeById(this.regResults.next().getValue(plist[0][1..-1]).getLocalName()); origs.add(v);}
            println "Influence - " + origs

            def m = [:].withDefault{1}
            origs._().out.simplePath().loop(2){it.loops<=3}{(it.object.out.count() == 0)}.path().toList().each{m[it[0]]++}

            m = m.sort{a,b -> b.value <=> a.value}[0..9]
            m.each{if (it.key in origs) {println(it)}}
            //origs.each{println it.id + " = " + m[it]}
        }
        else{println "Invalid parameters (" + params + ")"}
    }

    def processQueryReputation(){
        Gremlin.load()
        println "rank: REPUTATION params: " + params;
        def plist = this.params.split(",").collect{it.trim()}


        if (!plist[0].startsWith("_")){ //first param is variable
            def origs = []

            while(this.regResults.hasNext()) { def v = this.graph.v(this.regResults.next().getProperty(plist[0]).getId()); origs.add(v);}
            println "Reputation - " + origs

            def m = [:].withDefault{1}
            //println origs.class.name
            //return

            origs.reverse()._().gather.scatter.transform{
                def rank = m[it];
                //println "ahhhh " + it.class.name;
                def neighbors = it.both.toList();
                def degree = neighbors.size();
                neighbors.each {
                    m[it] = m[it] + (rank/degree);
                }
                neighbors;
            }.scatter.range(0,5000).loop(5){true}.iterate()



            m = m.sort{a,b -> b.value <=> a.value} //[0..9]

            //println m
            //return

            m.each{if (it.key in origs) {println(" " + it.key.map() + " " + it)}}
            //origs.each{println it.id + " = " + m[it]}
        }
        else{println "Invalid parameters (" + params + ")"}
    }


    def processCypher() {
		
		ExpandoMetaClass.enableGlobally()
		Map.metaClass.getProperty = {name -> delegate.get(name).get()}
		//Map2.metaClass.setProperty = {name, val -> delegate.setProperty(name, val)}
		
		def engine = new ExecutionEngine( this.neoGraphDB );

		this.regResults = engine.execute(this.parsedQuery.regular[0].value().trim());
		
		return
		//println( this.regResults );
		//while(this.regResults.hasNext()) { print this.regResults.next()}
		//def column = this.regResults.columnAs("n.label")
		println this.regResults.class.name
		println this.regResults.next().class.name
		println this.regResults.next().n
		println this.regResults.next().keys()
		println this.regResults.next().get('n').class
		println this.regResults.next().get('n').class.methods
		println this.regResults.next().get('n').get().class.methods
		println this.regResults.next().get('n').get().class
		println this.regResults.next().get('n').get().getId()
		println this.regResults.next().get('n.label').get()
		return
		this.regResults.each{
			println it.get('n') //.get().getId()
	}
		this.regResults.next().class.methods.each{
			//n -> println n
		}
		
		//while(column.hasNext()) { print column.next()}
		return

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

} //end class

