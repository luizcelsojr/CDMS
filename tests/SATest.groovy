
/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/24/12
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/24/12
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */

//import groovy.util.*
import br.unicamp.ic.lis.cdms.sa.*
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin
import br.unicamp.ic.lis.cdms.benchmark.Timer

class SATest extends GroovyTestCase{
    private sa



    void setUp(){
        Gremlin.load()
        sa = new SA()

        //TODO: groovify

        def g = TinkerGraphFactory.createTinkerGraph()
        sa.setGraph(g)
        sa.setOrig(g.v(5))
        sa.setDest(g.v(1))

        println "setuuupiiiii"
    }

    void testSA(){
        def potential
        def time = Timer.closureBenchmark{potential = this.sa.process()}

        println "total time: ${time}"

        println "result: ${potential}"
        assertEquals(potential, 81.0)
    }
}
