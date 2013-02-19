import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query  (type: "sparql", limit:50) {
    regular  """
PREFIX movie: <http://data.linkedmdb.org/resource/movie/>
select distinct ?a where{
    ?f movie:director <http://data.linkedmdb.org/resource/director/8501> .
    ?f rdfs:label ?l .
    ?f movie:actor ?a .
    ?a rdfs:label ?la .
    ?f movie:initial_release_date ?date .
            FILTER ( fn:starts-with(?date, "199") )
}
LIMIT 200
"""
    rank  """
RANK BY RELEVANCE (?a,<http://data.linkedmdb.org/resource/director/8501>)
"""
    ranking{
        metric (type: "Relevance", weight: 1, rw: false, steps: 150, seed: 2,  direction:Constants.BOTH){ //, follow: ['http://data.linkedmdb.org/resource/movie/actor', 'http://xmlns.com/foaf/0.1/made']
            orig (type: "variable", label: "a")
            dest(type: "node", id: "http://data.linkedmdb.org/resource/director/8501")
        }
    }
}

