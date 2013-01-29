import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query  (type: "sparql", limit: 50) {
    regular  """
PREFIX movie: <http://data.linkedmdb.org/resource/movie/>
select distinct ?subject, ?l where{
    ?subject a movie:film_subject .
    ?subject rdfs:label ?l
}
"""
    rank  """
RANK BY RELEVANCE (?a,<http://data.linkedmdb.org/resource/director/8501>)
"""
    ranking{
        metric (type: "Relevance", weight: 1, c: 3, direction: Constants.BOTH, rw: false, steps: 200){
            orig (type: "variable", label: "subject")
            dest(type: "node", id: " http://data.linkedmdb.org/resource/writer/13251")
        }
    }
}

