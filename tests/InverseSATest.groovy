
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
import br.unicamp.ic.lis.cdms.benchmark.Timer
import br.unicamp.ic.lis.cdms.sa.InverseSA
import br.unicamp.ic.lis.cdms.sa.SA
import br.unicamp.ic.lis.cdms.util.Constants
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin

class InverseSATest{ // extends GroovyTestCase
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
        this.defaultArgs['follow'] = []

    }

    void testSA(){

        //test basic
        def args

        println "*** dividing potential unweighted SA ***"
        args = this.defaultArgs.clone()
        args['direction'] = Constants.OUTBOUND
        args['dividePotential'] = true
        //runSA(args, this.g.v(1), this.g.v(5), ((((100.0/3.0)*0.9)/2.0)*0.9))

        println "*** simple weighted SA ***"
        args = this.defaultArgs.clone()
        args['c'] = 1
        args['direction'] = Constants.INBOUND
        args['weighted'] = true
        //runSA(args, this.g.v(3), this.g.v(4), 36.0)

        println "*** dividing potential unweighted SA, follow 'created' ***"
        args = this.defaultArgs.clone()
        args['direction'] = Constants.BOTH
        args['dividePotential'] = false
        args['follow'] = ['created', 'non-existent']
        args['c'] = 3
        runSA(args, this.g.v(1), this.g.v(5), ((((((100.0/1.0)*0.9)/3.0)*0.9))/2.0)*0.9)

    }

    def runSA (args, orig_, dest_, expected){
        //this.sa = new SA(graph: g, follow: Constants.INBOUND)
        def sa = new InverseSA()
        args.each{ key, value  ->
            sa.setProperty(key, value)
        }

        def isa = new SA()
        args.each{ key, value  ->
            isa.setProperty(key, value)
        }

        def r = new Random()

        9.times{
            def orig = this.g.v(r.nextInt(6) + 1)
            def dest = this.g.v(r.nextInt(6) + 1)
            println "orig = ${orig} --> dest = ${dest}"


            assertEquals(isa.process(orig, dest), sa.process(orig, dest), 0.001f)


        }







    }
}
