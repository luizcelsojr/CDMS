import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START
   allenNode = node:node_auto_index(value='http://data.linkedmdb.org/resource/director/8501')
MATCH
(movie)-[:`http://data.linkedmdb.org/resource/movie/director`]->(allenNode),
(movie)-[:`http://data.linkedmdb.org/resource/movie/actor`]->(actor),
(movie)-[:`http://data.linkedmdb.org/resource/movie/initial_release_date`]->(date)
WHERE date.value =~ '199.*'
RETURN DISTINCT actor
LIMIT 1000;
"""
    rank  """
RANK BY 1 WRELEVANCE (n,_117)
--RANK BY 2 WCONNECTIVITY (n,_121)
--RANK BY WRELEVANCE (n,_117)
--RANK BY WCONNECTIVITY (n,_117)
"""
    ranking{
        metric (type: "Relevance", weight: 1, weighted: false, direction: Constants.BOTH){
            orig (type: "variable", label: "actor")
            dest(type: "node", id: "776408")
        }
    }
}



