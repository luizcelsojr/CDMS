
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

        //do not change!!!!
        this.defaultArgs['graph'] = g
        this.defaultArgs['direction'] = Constants.OUTBOUND
        this.defaultArgs['c'] = 2
        this.defaultArgs['weighted'] = false
        this.defaultArgs['weightProp'] = 'weight'
        this.defaultArgs['dividePotential'] = false

    }

    void testSA(){

        //test basic
        def args

        println "*** dividing potential unweighted SA ***"
        args = this.defaultArgs.clone()
        args['direction'] = Constants.OUTBOUND
        args['dividePotential'] = true
        runSA(args, this.g.v(1), this.g.v(5), ((((100.0/3.0)*0.9)/2.0)*0.9))

        println "*** simple weighted SA ***"
        args = this.defaultArgs.clone()
        args['c'] = 1
        args['direction'] = Constants.INBOUND
        args['weighted'] = true
        runSA(args, this.g.v(3), this.g.v(4), 36.0)


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
        assertEquals(expected, potential, 0.001)



    }
}
