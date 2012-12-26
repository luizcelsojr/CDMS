
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
import br.unicamp.ic.lis.cdms.util.Constants

class SATest extends GroovyTestCase{
    private sa
    def defaultArgs = [:]
    def g



    void setUp(){
        Gremlin.load()


        this.g = TinkerGraphFactory.createTinkerGraph()

        this.defaultArgs['graph'] = g
        this.defaultArgs['follow'] = Constants.INBOUND

    }

    void testSA(){

        //test basic
        def args = this.defaultArgs.clone()
        args['follow'] = Constants.OUTBOUND
        runSA(args, this.g.v(5), this.g.v(1), 81.0)



    }

    def runSA (args, orig, dest, expected){
        //this.sa = new SA(graph: g, follow: Constants.INBOUND)
        def sa = new SA()
        args.each{ key, value ->
            sa.setProperty(key, value)
        }

        def potential
        def time = Timer.closureBenchmark{potential = sa.process(orig, dest)}

        println "total time: ${time}"

        println "result: ${potential}"
        assertEquals(potential, expected)


    }
}
