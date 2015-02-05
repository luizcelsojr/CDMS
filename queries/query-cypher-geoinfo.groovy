import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START n=node(*) WHERE has(n.type) and n.type = "rest" return n
"""
    rank  """
RANK BY 1 WRELEVANCE (n,_117)
--RANK BY 2 WCONNECTIVITY (n,_121)
--RANK BY WRELEVANCE (n,_117)
--RANK BY WCONNECTIVITY (n,_117)
"""
    ranking{
        /*metric (type: "RRelevance", weight: 1, weighted: true, weightProp: "Weight", direction: Constants.INBOUND, shortestpaths: false){ //, shortestpaths: true , sa:'TraceableSA'
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
        metric (type: "Relevance", weight: 2, weighted: true, weightProp: "Weight", direction: Constants.INBOUND){
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
        metric (type: "Connectivity", weight: 3, c: 7, weighted: false, weightProp: "Weight", direction: Constants.BOTH, shortestpaths: false  ){//, fo: { a, d, n -> a * d }, shortestpaths: true , sa:'TraceableSA'
            orig (type: "variable", label: "n")
            dest(type: "node", id: "1")
        } */
        metric (type: "Connectivity", weight: 1, desc: true, c: 7, a: 0.01, t: 0,  weighted: true, weightProp: "Weight", direction: Constants.INBOUND, shortestpaths: false, fo: { a, d, n -> a }, fi: { currentV, newV, w -> ((currentV == 0) || (currentV > (newV+w)))? (newV+w) : currentV}  ){//, fo: { a, d, n -> a * d }, shortestpaths: true , sa:'TraceableSA'
            orig (type: "variable", label: "n")
            dest(type: "node", id: "1")
        }

    }
}




