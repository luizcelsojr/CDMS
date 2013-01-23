import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START
   movieType = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/film')
MATCH
(movie)-->(movieType)
RETURN DISTINCT movie
LIMIT 2;
"""
    ranking{
        metric (type: "Relevance", weight: 1, weighted: false, direction: Constants.BOTH, follow: ['LuceneMapper:hasToken', 'LuceneMapperIdx:hasToken']){
            orig (type: "variable", label: "movie")
            dest (type: "map", mapper: "LuceneMapper", parameter: "my favourite films are matrix and avatar")
        }
    }
}




