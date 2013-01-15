import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (action: "map", language: "cypher") {
    regular  """
START
   movieType = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/film')
MATCH
(movie)-->(movieType)
RETURN DISTINCT movie;
"""
    mapping{
        mapper (type: "LuceneMapper"){
            parameter (key: "node", value:'movie')
        }
    }
}




