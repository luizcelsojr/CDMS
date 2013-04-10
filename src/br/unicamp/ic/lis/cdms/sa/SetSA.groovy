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
class SetSA extends SA {
    def A = [:].withDefault{0.0f} //activated nodes
    def Actv = new ActivatedNetwork() //activated nodes

    void process(){

        def countIterations = 0

        origs.each{Actv.addOrUpdate(it, null, this.a)}

        origs._().as('start').gather.scatter
                .sideEffect{countIterations++}
        .filter{(Actv.getPotential(it) > this.t)} // and (countIterations < this.maxIterations)
                .transform{
            def neighbors = []
            def current = it

            if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.filter{(this.follow)?it.label in this.follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
            if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.filter{(this.follow)?it.label in this.follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

            def n = neighbors.size().toFloat()

            Float Apotential = Actv.getAndDrainPotential(current)
            Float Atransfer = (this.dividePotential) ? (Apotential * this.d)/n : (Apotential * this.d)
            Float AtoNeighbor = 0.0f
            neighbors.each{
                // it is the path, it[-1] is the outV
                AtoNeighbor = (this.weighted) ? Atransfer * it[1].getProperty(this.weightProp).toFloat() : Atransfer
                Actv.addOrUpdate(it[-1], current, AtoNeighbor)
            }
            neighbors.collect{it[-1]}
        }.scatter  //.filter{it.id!=destid}
                .filter{it.map()['kind'] != 'literal'} //must be 'uri' for SPARQL queries to work. not necessary for cypher
                .loop('start'){it.loops<=this.c}.iterate() //println "it.object.id=${it.object.id}";

    }

    float getPotential(node){
        return Actv.getTotalPotential(node)
    }


}
