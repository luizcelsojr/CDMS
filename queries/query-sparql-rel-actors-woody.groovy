def builder = NodeBuilder.newInstance()


builder.query  (type: "sparql") {
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
LIMIT 98
"""
    rank  """
RANK BY RELEVANCE (?a,<http://data.linkedmdb.org/resource/director/8501>)
"""
    ranking{
        metric (type: "Relevance", weight: 1){
            orig (type: "variable", label: "a")
            dest(type: "node", id: "http://data.linkedmdb.org/resource/director/8501")
        }
    }
}

