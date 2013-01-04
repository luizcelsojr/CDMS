import br.unicamp.ic.lis.cdms.queryproc.Parser

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/4/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
class ParserTest extends GroovyTestCase {
    void setUp(){

    }

    void testParser(){
        def queryf = '/Users/luizcelso/workspace/CDMS/queries/query-cypher-rel-symdia_cases.groovy'
        def parser = new Parser()

        def query = parser.parse(queryf)

        assertEquals('cypher', query.@type)
        assertEquals('relevance', query.ranking.metric.grep{it.@type == 'relevance'}[0].@type)

    }
}
