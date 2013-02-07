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
    int radius = 0 //how far the walker is from orig

    RW (RandomWalkerSA sa, Vertex orig, Vertex dest){
        this.sa = sa
        this.orig = orig
        this.dest = dest
        this.current = orig
        this.r = new Random()

    }

    RW (RandomWalkerSA sa, Vertex orig, Vertex dest, long seed){
        this(sa, orig, dest)
        this.r = new Random(seed)
    }


    void reset(){

        this.current = this.sa.Actv.getRandom()
        //if (!this.sa.Actv.network[this.current].radius) println "XXXXXXXXXXXXXXXXXX ${this.sa.Actv.network[this.current]} ${this.current} \n ${this.sa.Actv.network}"
        this.radius = this.sa.Actv.network[this.current].radius
        //this.sa.Actv.network[this.orig].potential = this.sa.a
    }


    def step(){

        while (this.radius >= this.sa.c)  this.reset()
        //println "current node ${this.current}, previou radius = ${this.radius}"

        this.radius++

        if (this.sa.Actv.network[this.current].potential < this.sa.t) {
            this.reset()
            return
        }


        def neighbors = []

        if (this.sa.direction != Constants.OUTBOUND) neighbors.addAll(this.current.inE.filter{(this.sa.follow)?it.label in this.sa.follow: true}.outV.filter{it.map()['kind'] != 'literal'}.path().toList()) // if INBOUND or BOTH, add all inbound edges
        if (this.sa.direction != Constants.INBOUND) neighbors.addAll(this.current.outE.filter{(this.sa.follow)?it.label in this.sa.follow: true}.inV.filter{it.map()['kind'] != 'literal'}.path().toList()) // if OUTBOUND or BOTH, add all outbound edges


        def n = neighbors.size().toFloat()


        if (n) {
            def next = neighbors[this.r.nextInt(n.toInteger())] //get random element
            //println "next node = ${next[-1]}, current radius = ${this.radius}"

            Float Atransfer = (this.sa.dividePotential) ? (this.sa.Actv.network[this.current].potential * this.sa.d)/n : (this.sa.Actv.network[this.current].potential * this.sa.d)
            // it is the path, it[-1] is the outV
            if (this.sa.weighted) Atransfer = Atransfer * next[1].getProperty(this.sa.weightProp).toFloat()
            this.sa.Actv.add(next[-1], Atransfer, this.radius)
            //this.sa.Actv.network[next[-1]].potential += (this.sa.weighted) ? Atransfer * next[1].getProperty(this.sa.weightProp).toFloat() : Atransfer
            //this.sa.Actv.network[next[-1]].radius = this.radius

            if (next[-1] == this.dest){
                reset()
                return
            }

            //this.sa.Actv.network[this.current].potential = 0.0f

            this.current = next[-1]

        } else {
            reset()
            return

        }
    }

}
