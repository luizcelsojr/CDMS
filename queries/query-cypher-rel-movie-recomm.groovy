import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher", limit: 15) {
    regular  """
START
   silence = node:node_auto_index(value='http://data.linkedmdb.org/resource/film/38145'),
   movieType = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/film')
MATCH
(movie)-->(movieType),
(movie)-[:`http://data.linkedmdb.org/resource/movie/initial_release_date`]->(date)
WHERE date.value =~ '199.*'
RETURN DISTINCT movie;
"""
    rank  """
RANK BY 1 WRELEVANCE (n,_117)
--RANK BY 2 WCONNECTIVITY (n,_121)
--RANK BY WRELEVANCE (n,_117)
--RANK BY WCONNECTIVITY (n,_117)
"""
    ranking{
        /*metric (type: "Connectivity", weight: 1, c: 4, weighted: false, direction: Constants.BOTH, follow: ['http://www.w3.org/2004/02/skos/core#subject']){
            orig (type: "variable", label: "movie")
            dest(type: "node", id: "695985")
        }
        metric (type: "Connectivity", weight: 3, c: 3, weighted: false, direction: Constants.BOTH, follow: ['http://data.linkedmdb.org/resource/movie/director']){
            orig (type: "variable", label: "movie")
            dest(type: "node", id: "695985")
        }*/
        metric (type: "Relevance", weight: 3, c: 5, weighted: false, direction: Constants.BOTH, follow: ['http://data.linkedmdb.org/resource/movie/actor']){
            orig (type: "variable", label: "movie")
            dest(type: "node", id: "695985")
        }

    }
}




