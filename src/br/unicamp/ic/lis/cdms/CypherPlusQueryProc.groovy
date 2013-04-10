package br.unicamp.ic.lis.cdms

import br.unicamp.ic.lis.cdms.queryproc.Parser
import br.unicamp.ic.lis.cdms.sa.RandomWalkerSA
import br.unicamp.ic.lis.cdms.sa.SA
import br.unicamp.ic.lis.cdms.sa.ShortestPathsSA
import br.unicamp.ic.lis.cdms.sa.TraceableSA
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import org.neo4j.cypher.ExecutionEngine
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import com.tinkerpop.blueprints.impls.neo4j.*
import scala.collection.immutable.Map;

import com.tinkerpop.gremlin.groovy.Gremlin



class CypherPlusQueryProc{

	def rank, params, regular, rankings, totalWeight
	def neoGraphDB, graph
	def regResults //results from regular query
	def Results = [:].withDefault{0.0} //results from ranking
    def parsedQuery
    def mapper = null


	def CypherPlusQueryProc(db_path){
		this.neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
		this.graph = new Neo4jGraph(this.neoGraphDB)

		registerShutdownHook( this.neoGraphDB );
		rank = params = regular = ""
		rankings = [:]
		totalWeight = 0
		
	}

	
	def parseQuery(query){
        def parser = new Parser()

        this.parsedQuery = parser.parse(query)

        this.parsedQuery.ranking.metric.each{
            this.totalWeight += it.@weight.toFloat()
        }

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
            else if (it.orig[0].@type == 'variable' && it.dest[0].@type == 'map'){ //first param is variable, second is node/id
                this.processMetric(it, getOrigs(it), getDestMap(it))
            }
            else if (it.dest[0].@type == null){ //no dest -> set metric
                this.processSetMetric(it, getOrigs(it))
            }

            else{println "Invalid parameters (" + it + ")"}

        }

        if (this.mapper) this.mapper.rollback()

        def m = this.Results.sort{a,b -> b.value <=> a.value}

        if (this.parsedQuery.@limit) m = m[0..this.parsedQuery.@limit - 1]

        m.each{key, value ->
            def label = this.graph.v(key.id).map().Label? this.graph.v(key.id).map().Label: this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().value
            println "${key.id}, ${label}, ${value}"
        }
		
	}

    def getDest(context){
        Gremlin.load()

        def destid = context.dest[0].@id
        def dest = this.graph.v(destid)
        println dest.map()
        return dest
    }

    def getDestMap(context){
        //creates dest based on a mapper
        Gremlin.load()

        def mapperName = context.dest[0].@mapper

        println mapperName

        //TODO: create mapper class with builder for all mappers
        def mapperClass = 'br.unicamp.ic.lis.cdms.mappers.' + mapperName as Class
        this.mapper = mapperClass.newInstance(this.graph)

        def node = this.mapper.map(context.dest[0].@parameter)

        return node
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

    SA getSA(context){
        switch(context.@type.toLowerCase()){
            case "relevance":
                def saClass = SA
                if (context.@sa) saClass = 'br.unicamp.ic.lis.cdms.sa.' + context.@sa as Class

                if (context.@shortestpaths) return new ShortestPathsSA(context, true, this.neoGraphDB)
                if (context.@rw) return new RandomWalkerSA(context, true)
                return saClass.newInstance(context, true)
                break
            case "rrelevance":
                def saClass = TraceableSA
                if (context.@sa) saClass = 'br.unicamp.ic.lis.cdms.sa.' + context.@sa as Class

                return saClass.newInstance(context, true)
                break
            case "connectivity":
                def saClass = SA
                if (context.@sa) saClass = 'br.unicamp.ic.lis.cdms.sa.' + context.@sa as Class

                if (context.@shortestpaths) return new ShortestPathsSA(context, false, this.neoGraphDB)
                if (context.@rw) return new RandomWalkerSA(context, false)
                return saClass.newInstance(context, false)
                break
            case "reputation":
                def saClass = SetSA
                if (context.@sa) saClass = 'br.unicamp.ic.lis.cdms.sa.' + context.@sa as Class

                return saClass.newInstance(context, false)
                break
            case "influence":
                processQueryInfluence()
                break

            default:
                println	"Invalid Ranking Operation"
        }
    }

	def processMetric(context, origs, dest){
        Gremlin.load()
		println "rank: weight=${context.@weight} ${context.@type} context: " + context;

			def R = [:] //results
			def maxResult = 0.0
            def result = 0.0

            def sa = getSA(context)


            println "${context.@type} - ${(origs.size > 10)? origs[0..10].toString() + '...x ' + origs.size: origs}  --> ${dest}"

            boolean inverse = false

            if (inverse) {
                sa.inverseDirection()
                sa.registerDests(origs) //added for rrelevance
            }


            origs.each{
                if (inverse) result = sa.inverseProcess(it, dest)
                else result = sa.process(it, dest)
                if (result > maxResult) maxResult = result
                R[it] = result
            }
            def normWeight = context.@weight/this.totalWeight

            R.each{ key, value ->
                this.Results[key] = this.Results[key] + ((value/maxResult) * normWeight)
            }


	}

    def processSetMetric(context, origs){
        Gremlin.load()
        println "rank: weight=${context.@weight} ${context.@type} context: " + context;

        def R = [:] //results
        def maxResult = 0.0
        def result = 0.0

        def sa = getSA(context)
        sa.registerOrigs(origs)

        println "${context.@type} - ${(origs.size > 10)? origs[0..10].toString() + '...x ' + origs.size: origs} "

        sa.process()
        origs.each{
            result = sa.getPotential(it)
            if (result > maxResult) maxResult = result
            R[it] = result
        }
        def normWeight = context.@weight/this.totalWeight

        R.each{ key, value ->
            this.Results[key] = this.Results[key] + ((value/maxResult) * normWeight)
        }


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


    def processRegular() {
		
		ExpandoMetaClass.enableGlobally()
		Map.metaClass.getProperty = {name -> delegate.get(name).get()}
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

} //end class

