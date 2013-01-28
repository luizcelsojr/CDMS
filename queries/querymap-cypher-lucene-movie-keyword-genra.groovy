import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START
   movieType = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/film')
MATCH
(movie)-->(movieType)
RETURN DISTINCT movie;
"""
    ranking{
        metric (type: "Relevance", weight: 2, weighted: false, direction: Constants.BOTH, follow: ['LuceneMapper:hasToken', 'LuceneMapperIdx:hasToken']){
            orig (type: "variable", label: "movie")
            dest (type: "map", mapper: "LuceneMapper", parameter: "my favorite films are matrix and avatar")
        }
        metric (type: "Relevance", weight: 1, c: 3, weighted: false, direction: Constants.BOTH, follow: ['http://www.w3.org/2004/02/skos/core#subject']){
            orig (type: "variable", label: "movie")
            dest (type: "node", id: '705541')  //virtual reality subject
        }

    }
}




