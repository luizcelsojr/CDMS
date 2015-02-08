import br.unicamp.ic.lis.cdms.queryproc.CypherPlusParser

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
        def queryf = '/Users/luizcelso/workspace/CDMS/queries/query-cypher-rel-symdia_cases_test.groovy'
        def parser = new CypherPlusParser()

        def query = parser.parse(queryf)

        assertEquals('cypher', query.@type)
        assertEquals('Relevance', query.ranking.metric.grep{it.@type == 'Relevance'}[0].@type)

    }
}
