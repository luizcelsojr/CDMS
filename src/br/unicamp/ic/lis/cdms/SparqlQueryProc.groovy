package br.unicamp.ic.lis.cdms

import org.openrdf.query.QueryLanguage
import com.tinkerpop.gremlin.groovy.Gremlin



class SparqlQueryProc{

	def rank, params, sparql
	def graph, sailConn, repoConn
	def sparqlResults

	def SparqlQueryProc(g, sc, rc){

		this.graph = g
		this.sailConn = sc
		this.repoConn = rc
		rank = params = sparql = ""
	}

	def removeNS(s){
		s.split("#")[1]
	}
	
	def benchmark = { closure ->
		def start, now
		start = System.currentTimeMillis()
		closure.call()
		now = System.currentTimeMillis()
		now - start
	}

	def parseQuery(query){
		def myRegularExpression =/RANK BY ([a-zA-Z]+) \((.*)\)/
		def matcher

		for (line in query.split("\n")){
			matcher = ( line =~ myRegularExpression )
			if (matcher.size()){
				this.rank = matcher[0][1];
				this.params = matcher[0][2];
			}
			else{
				this.sparql += line + "\n";
			}
		}

		println("sparql: " + sparql)

	}

	def processQuery(query){

		parseQuery(query)

		processSparql()

		switch(this.rank.toLowerCase()){
			case "relevance":
				processQueryRelevance()
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

	def processQueryInfluence(){
		println "rank: INFLUENCE params: " + params;
		def plist = this.params.split(",").collect{it.trim()}

		if (plist[0].startsWith("?")){ //first param is variable
			def origs = []

			while(this.sparqlResults.hasNext()) { def v = this.graph.v(this.sparqlResults.next().getValue(plist[0][1..-1]).getLocalName()); origs.add(v);}
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
		println "rank: REPUTATION params: " + params;
		def plist = this.params.split(",").collect{it.trim()}

		if (plist[0].startsWith("?")){ //first param is variable
			def origs = []

			while(this.sparqlResults.hasNext()) { def v = this.graph.v(this.sparqlResults.next().getValue(plist[0][1..-1]).getLocalName()); origs.add(v);}
			println "Reputation - " + origs

			def m = [:].withDefault{1}

			origs._().transform{
				def rank = m[it];
				def neighbors = it.both.toList();
				def degree = neighbors.size();
				neighbors.each {
					m[it] = m[it] + (rank/degree);
				}
				neighbors;
			}.scatter.range(0,10).loop(3){it.loops<=10}.iterate()

			m = m.sort{a,b -> b.value <=> a.value}[0..9]
			m.each{if (it.key in origs) {println(it)}}
			//origs.each{println it.id + " = " + m[it]}
		}
		else{println "Invalid parameters (" + params + ")"}
	}

	def processQueryRelevance(){
		Gremlin.load()
		println "rank: RELEVANCE params: " + params;
		def plist = this.params.split(",").collect{it.trim()}

		if (plist[0].startsWith("?") && !(plist[1].startsWith("?"))){ //first param is variable, second is id
			//def dest = this.graph.v(plist[1])
			def destid = plist[1][1..-2] //remove <> from id
			def dest = this.graph.v(destid)
			def origs = []


			while(this.sparqlResults.hasNext()) { def v = this.graph.v(this.sparqlResults.next().getValue(plist[0][1..-1])); origs.add(v);}

//def g = TinkerGraphFactory.createTinkerGraph()
//origs = [g.v(4), g.v(1), g.v(5), g.v(3), g.v(6)]
//dest = g.v(2)


			def A = [:].withDefault{0}
			def R = [:] //results
			
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
					if (!R[it]){
						A = [:].withDefault{0}
						A[it] = 100
						
						it.as('start')
							.filter{
									//if (A[it] <= t) println "No no for ${it} with ${A[it]}";
									A[it] > t}
							.transform{
								def neighbors = it.both.toList()
								def n = neighbors.size()
								if (n > 1000 | n == 0) {//println "vaza ${it}....."; 
									return []}
								def Atransfer = (A[it] * d)/n
								neighbors.each{
									A[it] += Atransfer
								}
								if (n) A[it] = 0
								//println "A ${A}"
								neighbors
							}.scatter
							.filter{it.id!=destid}
							.filter{it.map()['kind'] == 'uri'}
							.loop('start'){it.loops<=2}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";
							
							//println "vai ${dest} ${A[dest]}"; if (it.object.id==destid) println "yeahhhhh! ${A[dest]} ${it.object}";
							//					.except([dest]) 
						
						R[it] = A[dest]
						//println R
					} 
				}
				m = R.sort{a,b -> b.value <=> a.value}
				//println "m: ${m}"
				m.each{println it}
				m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().id}, ${value}"}
			}
			println "execution took ${duration} ms"
/*			
			def paths = origs._().as('start')
				.copySplit(
					_().inE.filter{!(it.label in NOTFOLLOW)}.outV, 
					_().outE.filter{!(it.label in NOTFOLLOW)}.inV
				).fairMerge()
				.filter{it.map()['kind'] == 'uri'}
				.simplePath().loop('start'){it.loops<=3}{(it.object.id==dest)}.path().toList()
							
			paths.each{
				println it;
				def rank=1;
				for (v in it) {
					if (v.class.name == 'com.tinkerpop.blueprints.Vertex') rank=rank/v.both.count();
				}
				rank = rank/it.size();
				if (m[it[0]] < rank){
					m[it[0]] = rank;
					p[it[0]] = it;
				}
			}
			m = m.sort{a,b -> b.value <=> a.value}[0..9]
			m.each{println it.toString() + " - " + p[it.key].toString()}
	*/		

		}
		else{println "Invalid parameters (" + params + ")"}
	}

	def processSparql() {
		
		
		def resultsTable = this.repoConn.prepareTupleQuery(QueryLanguage.SPARQL, this.sparql);
		this.sparqlResults = resultsTable.evaluate();
/*			
		def parser = new SPARQLParser();
		//sparql = "SELECT ?developer ?age WHERE { ?developer <http://tinkerpop.com#age> ?age . FILTER (?age < 34)}";
		def query = parser.parseQuery(this.sparql, "http://data.linkedmdb.org");
		this.sparqlResults = this.sailConn.evaluate(query.getTupleExpr(), query.getDataset(), new EmptyBindingSet(), false);
*/
	}



} //end class

