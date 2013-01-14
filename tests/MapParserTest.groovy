import br.unicamp.ic.lis.cdms.mappers.MapParser

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/9/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
class MapParserTest extends GroovyTestCase {

    void setUp(){

    }

    void testMapParser (){
        def queryf = '/Users/luizcelso/workspace/CDMS/queries/map-cypher-lucene-movie-test.groovy'
        def parser = new MapParser()

        def map = parser.parse(queryf)

        assertEquals('cypher', map.@language)
        assertEquals('LuceneMapper', map.mapping.mapper.grep{it.@type == 'LuceneMapper'}[0].@type)
        assertEquals('movie', map.mapping.mapper.parameter.grep{it.@key == 'node'}[0].@value)

    }
}

