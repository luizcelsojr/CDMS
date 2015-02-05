import br.unicamp.ic.lis.cdms.util.Constants

def builder = NodeBuilder.newInstance()


builder.query (action: "map", language: "cypher") {
    regular  """
START n=node(*) WHERE has(n.type) and n.type = "rest" return n
"""
    mapping{
        mapper (type: "LuceneMapper"){
            parameter (key: "node", value:'n')
        }
    }
}




