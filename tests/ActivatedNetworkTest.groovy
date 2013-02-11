import br.unicamp.ic.lis.cdms.sa.ActivatedNetwork
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/5/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
class ActivatedNetworkTest { // extends GroovyTestCase

    void testActivatedNetwork(){
        Gremlin.load()
        def g = TinkerGraphFactory.createTinkerGraph()

        def v = g.v(1)

        def AN = new ActivatedNetwork()
        AN.r = new Random(1)

        AN.add(v)
        AN.add(g.v(3))
        AN.add(g.v(5))

        AN.network[v].bla = 2

        println AN.getRandom()
        println AN.getRandom()
        println AN.getRandom()
        println AN.getRandom()
    }

}
