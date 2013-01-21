package br.unicamp.ic.lis.cdms.randomwalker

import br.unicamp.ic.lis.cdms.sa.RandomWalkerSA
import br.unicamp.ic.lis.cdms.util.Constants
import com.tinkerpop.blueprints.Vertex

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/1/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
class RW {

    RandomWalkerSA sa = null
    Vertex orig = null
    Vertex dest = null
    Vertex current = null
    Random r = null

    RW (RandomWalkerSA sa, Vertex orig, Vertex dest){
        this.sa = sa
        this.orig = orig
        this.dest = dest
        this.current = this.orig
        this.r = new Random()

    }

    RW (RandomWalkerSA sa, Vertex orig, Vertex dest, long seed){
        this(sa, orig, dest)
        this.r = new Random(seed)
    }


    void reset(){
        this.current = this.orig
    }


    def step(){

        if (this.sa.A[this.current] < this.sa.t) {
            reset()
            return //check semantics
        }
        def neighbors = []
        if (this.sa.direction != Constants.OUTBOUND) neighbors.addAll(this.current.inE.filter{(this.sa.follow)?it.label in this.sa.follow: true}.outV.filter{this.current.map()['kind'] != 'literal'}.path().toList()) // if INBOUND or BOTH, add all inbound edges
        if (this.sa.direction != Constants.INBOUND) neighbors.addAll(this.current.outE.filter{(this.sa.follow)?it.label in this.sa.follow: true}.inV.filter{this.current.map()['kind'] != 'literal'}.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

        def n = neighbors.size().toFloat()

        if (n) {
            def next = neighbors[this.r.nextInt(n)] //get random element

            def Atransfer = (this.sa.dividePotential) ? (this.sa.A[this.current] * this.sa.d)/n : (this.sa.A[this.current] * this.sa.d)
            // it is the path, it[-1] is the outV
            this.sa.A[next[-1]] += (this.sa.weighted) ? Atransfer * next[1].getProperty(this.sa.weightProp).toFloat() : Atransfer

            if (next[-1] == this.dest){
                reset()
                return
            }

            this.current = next[-1]

            //this.sa.A[it] = 0.0f
        } else {
            reset()
            return

        }
    }


}
