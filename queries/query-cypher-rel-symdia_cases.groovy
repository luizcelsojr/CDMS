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
        metric (type: "relevance", weight: 2, weighted: true){
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
        metric (type: "connectivity", weight: 1, weighted: true){
            orig (type: "variable", label: "n")
            dest(type: "node", id: "117")
        }
    }
}




