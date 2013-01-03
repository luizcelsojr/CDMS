
/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/2/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */

def builder = NodeBuilder.newInstance()


builder.query {
    regular  """
PREFIX movie: <http://data.linkedmdb.org/resource/movie/>
select * where{
    ?f movie:director <http://data.linkedmdb.org/resource/director/8501> .
    ?f rdfs:label ?l .
    ?f movie:actor ?a .
    ?a rdfs:label ?la .
    ?f movie:initial_release_date ?date .
            FILTER ( fn:starts-with(?date, "199") )
}
"""
    rank  """
RANK BY RELEVANCE (?a,<http://data.linkedmdb.org/resource/director/8501>)
"""
    ranking{
        metric (type: "relevance", weight: 2){
            orig (type: "variable", label: "?a")
            dest(type: "node", id: "http://data.linkedmdb.org/resource/director/8501")
        }
        metric (type: "connectivity", weight: 1){
            orig (type: "variable", label: "?a")
            dest(type: "node", id: "http://data.linkedmdb.org/resource/director/8501")
        }
    }
}


/*
          metric2{
            type = "connectivity"
            weight = 1
            orig{
                type = "variable"
                label = "?a"
            }
            dest{
                type = "node"
                id = "http://data.linkedmdb.org/resource/director/8501"
            }
        }

*/