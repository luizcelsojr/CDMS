package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.util.Constants
import groovy.transform.InheritConstructors

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/25/12
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
@InheritConstructors
class TraceableSA extends SA {
    def Actv = new ActivatedNetwork() //activated nodes

    float process(orig, dest){

        if (dest.id == orig.id) return this.a

        def A = [:].withDefault{0.0f} //activated nodes

        def destid = dest.id //.toString()

        A[orig] = this.a

        orig.as('start')
                        .filter{(A[it] > this.t)} // and (countIterations < this.maxIterations)
                        .transform{
                            def neighbors = []
                            if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.filter{(this.follow)?it.label in this.follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                            if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.filter{(this.follow)?it.label in this.follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

                            def n = neighbors.size().toFloat()
                            /*
                            if (n > 1000 | n == 0) {//println "vaza ${it}.....";
                                return []}*/
                            def Atransfer = (this.dividePotential) ? (A[it] * this.d)/n : (A[it] * this.d)
                            neighbors.each{
                                // it is the path, it[-1] is the outV
                                A[it[-1]] += (this.weighted) ? Atransfer * it[1].getProperty(this.weightProp).toFloat() : Atransfer
                            }
                            if (n) A[it] = 0.0f                         //.filter{it.id!=destid}
                            //println "A ${A}"
                            neighbors.collect{it[-1]}
                        }.scatter
                .filter{it.id!=destid}
                .filter{it.map()['kind'] != 'literal'} //must be 'uri' for SPARQL queries to work. not necessary for cypher
                        .loop('start'){it.loops<=this.c}.iterate() //println "it.object.id=${it.object.id}";

        //println "....Total iterations = ${countIterations}"


        return A[dest]


    }


}
