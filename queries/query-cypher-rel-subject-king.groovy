import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher", limit: 50) {
    regular  """
START
   subjectType = node:node_auto_index(value='http://data.linkedmdb.org/resource/movie/film_subject')
MATCH
   (subject)-[:`http://www.w3.org/1999/02/22-rdf-syntax-ns#type`]->(subjectType)
RETURN DISTINCT subject;
"""
    rank  """
RANK BY RELEVANCE (?a,<http://data.linkedmdb.org/resource/director/8501>)
"""
    ranking{
        metric (type: "Connectivity", weight: 1, c: 3, direction: Constants.BOTH, rw: false, steps: 200){
            orig (type: "variable", label: "subject")
            dest(type: "node", id: "776366") // 687584 = id for http://data.linkedmdb.org/resource/writer/13251 (stephen king)
        }
    }
}

