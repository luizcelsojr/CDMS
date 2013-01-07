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
                    this.processMetric(it, getOrigs(it), getDest(it))
                }
                else{println "Invalid parameters (" + it + ")"}

            }

            def m = this.Results.sort{a,b -> b.value <=> a.value}
            //println "m: ${m}"
            m.each{key, value -> println "${key.id}, ${this.graph.v(key.id).outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().id}, ${value}"}

        }

        def getDest(context){
            Gremlin.load()
            def destid = context.dest[0].@id
            return this.graph.v(destid)
        }

        def getOrigs(context){
            Gremlin.load()
            def origs = []
            while(this.regResults.hasNext()) {
                def v = this.graph.v(this.regResults.next().getValue(context.orig[0].@label));
                origs.add(v);

            }
            return origs
        }

        SA getSA(context){
            switch(context.@type.toLowerCase()){
                case "relevance":
                    return new RandomWalkerSA(context, true)
                    break
                case "connectivity":
                    return new RandomWalkerSA(context, false)
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

            println "${context.@type} - ${origs}  --> ${dest}"

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

