import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START n = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/actor') MATCH (actor)-->(n) RETURN actor;
"""
    rank  """
RANK BY 1 WRELEVANCE (n,_117)
--RANK BY 2 WCONNECTIVITY (n,_121)
--RANK BY WRELEVANCE (n,_117)
--RANK BY WCONNECTIVITY (n,_117)
"""
    ranking{
        metric (type: "Relevance", weight: 1, weighted: false, direction: Constants.OUTBOUND){
            orig (type: "variable", label: "actor")
            dest(type: "node", id: "5")
        }
    }
}




