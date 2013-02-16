import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START n=node(*) WHERE has(n.Type) and n.Type = "Diagnose" return n
"""
    rank  """
RANK BY 1 WRELEVANCE (n,_117)
--RANK BY 2 WCONNECTIVITY (n,_121)
--RANK BY WRELEVANCE (n,_117)
--RANK BY WCONNECTIVITY (n,_117)
"""
    ranking{
        metric (type: "Relevance", weight: 1, weighted: true, weightProp: "Weight", direction: Constants.INBOUND, shortestpaths: false, sa:'InverseSA'){ //, shortestpaths: true
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
        /*metric (type: "Relevance", weight: 2, weighted: true, weightProp: "Weight", direction: Constants.INBOUND){
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        } */
        metric (type: "Connectivity", weight: 2, weighted: true, weightProp: "Weight", direction: Constants.INBOUND, shortestpaths: false, sa:'InverseSA'){//, shortestpaths: true
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
    }
}




