package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.util.Constants
import groovy.transform.InheritConstructors

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/7/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */

@InheritConstructors
class RandomWalkerSA extends SA {

    float process(orig, dest){

        def A = [:].withDefault{0.0} //activated nodes

        def destid = dest.id.toString()

        A[orig] = this.a

        orig.as('start')
                .filter{
            A[it] > this.t}
        .transform{
            def neighbors = []
            if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
            if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

            def n = neighbors.size().toFloat()
            /*
            if (n > 1000 | n == 0) {//println "vaza ${it}.....";
                return []}*/
            def Atransfer = (this.dividePotential) ? (A[it] * this.d)/n : (A[it] * this.d)
            neighbors.each{
                // it is the path, it[-1] is the outV
                A[it[-1]] += (this.weighted) ? Atransfer * it[1].getProperty(this.weightProp).toFloat() : Atransfer
            }
            if (n) A[it] = 0.0
            //println "A ${A}"
            neighbors.collect{it[-1]}
        }.scatter
                .filter{it.map()['kind'] != 'literal'} //must be 'uri' for SPARQL queries to work. not necessary for cypher
                .filter{it.id!=destid}
        .loop('start'){it.loops<=this.c}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";


        return A[dest]

    }


}
