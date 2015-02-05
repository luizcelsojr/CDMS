import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (type: "cypher") {
    regular  """
START n=node(*) WHERE has(n.type) and n.type = "rest" return n;
"""
    ranking{
        metric (type: "Relevance", weight: 1, c: 2, weighted: false, direction: Constants.BOTH, follow: ['LuceneMapper:hasToken', 'LuceneMapperIdx:hasToken']){
            orig (type: "variable", label: "n")
            dest (type: "map", mapper: "LuceneMapper", parameter: "family friendly restaurant")
        }
        metric (type: "Connectivity", weight: 6, desc: true, c: 7, a: 0.01, t: 0,  weighted: true, weightProp: "Weight", follow: ['connects'], direction: Constants.INBOUND, shortestpaths: false, fo: { a, d, n -> a }, fi: { currentV, newV, w -> ((currentV == 0) || (currentV > (newV+w)))? (newV+w) : currentV}  ){//, fo: { a, d, n -> a * d }, shortestpaths: true , sa:'TraceableSA'
            orig (type: "variable", label: "n")
            dest(type: "node", id: "1")
        }
        metric (type: "Reputation", weight: 1, c: 6, weighted: false, follow: ['likes', 'knows'], direction: Constants.BOTH){ //
            orig (type: "variable", label: "n")
        }
        metric (type: "Relevance", weight: 1, c: 7, weighted: false, follow: ['isa', 'instanceof'], direction: Constants.BOTH){
            orig (type: "variable", label: "n")
            dest (type: "node", id: "25")
        }   /**/
    }
}




