    package br.unicamp.ic.lis.cdms

    import br.unicamp.ic.lis.cdms.queryproc.Parser
    import br.unicamp.ic.lis.cdms.sa.RandomWalkerSA
    import br.unicamp.ic.lis.cdms.sa.SA
    import org.openrdf.query.QueryLanguage

    import com.tinkerpop.gremlin.groovy.Gremlin



    class SparqlQueryProc{

        def rank, params, regular, rankings, totalWeight
        def graph, sailConn, repoConn
        def regResults //results from regular query
        def Results = [:].withDefault{0.0} //results from ranking
        def parsedQuery


        def SparqlQueryProc(g, sc, rc){

            this.graph = g
            this.sailConn = sc
            this.repoConn = rc
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
                    this.processMetric(it, getVariable(it, 'orig'), getNode(it, 'dest'))
                }
                else{println "Invalid parameters (" + it + ")"}

            }

            def m = this.Results.sort{a,b -> b.value <=> a.value}

            if (this.parsedQuery.@limit) m = m[0..this.parsedQuery.@limit - 1]

//            m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().id}, ${value}"}
            m.each{key, value ->
                def label = this.graph.v(key.id).map().Label? this.graph.v(key.id).map().Label: this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().value
                println "${key.id}, ${label}, ${value}"
            }
        }

        def getNode(context, source){
            Gremlin.load()
            def nodeid = context."${source}"[0].@id
            return this.graph.v(nodeid)
        }

        def getVariable(context, source){
            Gremlin.load()
            def values = []
            while(this.regResults.hasNext()) {
                def v = this.graph.v(this.regResults.next().getValue(context."${source}"[0].@label));
                values.add(v);

            }
            return values
        }

        SA getSA(context){
            switch(context.@type.toLowerCase()){
                case "relevance":
                    return (context.@rw)? new RandomWalkerSA(context, true): new SA(context, true)
                    break
                case "connectivity":
                    return (context.@rw)? new RandomWalkerSA(context, false): new SA(context, false)
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

            origs.each{
                result = sa.process(it, dest)
                if (result > maxResult) maxResult = result
                R[it] = result
            }
            def normWeight = context.@weight/this.totalWeight

            R.each{ key, value ->
                this.Results[key] = this.Results[key] + ((value/maxResult) * normWeight)
            }


        }



        def processRegular() {

         def resultsTable = this.repoConn.prepareTupleQuery(QueryLanguage.SPARQL, this.parsedQuery.regular[0].value().trim());
            this.regResults = resultsTable.evaluate();

        }



    } //end class

