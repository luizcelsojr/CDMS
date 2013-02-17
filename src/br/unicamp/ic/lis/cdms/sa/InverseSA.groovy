package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.util.Constants
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.gremlin.groovy.Gremlin
import groovy.transform.InheritConstructors

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/25/12
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
@InheritConstructors
class InverseSA  extends SA {


    float process(dest, orig){

        if (dest.id == orig.id) return this.a

        def A = [:].withDefault{0.0f} //activated nodes

        def destid = dest.id //.toString()

        def countIterations = 0

        A[orig] = this.a

        orig.as('start')
                        .sideEffect{countIterations++}
                        .filter{(A[it] > this.t)} // and (countIterations < this.maxIterations)
                        .transform{
                            def Atransfer = 0.0f
                            def n = 0.0f


                            if (it.id!=orig.id){
                                def neighbors = []
                                if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.filter{(this.follow)?it.label in this.follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                                if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.filter{(this.follow)?it.label in this.follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges


                                n = neighbors.size().toFloat()
                                if (n){
                                    if (this.dividePotential) A[it] = A[it]/n
                                    Atransfer = (A[it] * this.d)
                                    //Atransfer = (this.dividePotential) ? (A[it] * this.d)/n : (A[it] * this.d)
                                }

                            }else{
                                Atransfer =  A[it] * this.d
                            }

                            def upstreamNeighbors = []
                            if (this.direction != Constants.INBOUND) upstreamNeighbors.addAll(it.inE.filter{(this.follow)?it.label in this.follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                            if (this.direction != Constants.OUTBOUND) upstreamNeighbors.addAll(it.outE.filter{(this.follow)?it.label in this.follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges


                            upstreamNeighbors.each{
                                // it is the path, it[-1] is the outV
                                A[it[-1]] += (this.weighted) ? Atransfer * it[1].getProperty(this.weightProp).toFloat() : Atransfer
                            }
                            if (upstreamNeighbors.size()) A[it] = 0.0f                         //.filter{it.id!=destid}
                            //println "A ${A}"
                            upstreamNeighbors.collect{it[-1]}
                        }.scatter
                .filter{it.id!=destid}
                .filter{it.map()['kind'] != 'literal'} //must be 'uri' for SPARQL queries to work. not necessary for cypher
                        .loop('start'){it.loops<=this.c}.iterate() //println "it.object.id=${it.object.id}";

        //println "....Total iterations = ${countIterations}"


        if ((A[dest] > 0.0f) && (this.dividePotential)) {
            dest.sideEffect{
                    def neighbors = []
                    if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.filter{(this.follow)?it.label in this.follow: true}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
                    if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.filter{(this.follow)?it.label in this.follow: true}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges

                    if (neighbors.size()){
                       A[it] = A[it]/neighbors.size()
                    }

                }.iterate()
        }
        return A[dest]

    }


}
