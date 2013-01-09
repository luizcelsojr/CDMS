package br.unicamp.ic.lis.cdms.sa

import com.tinkerpop.gremlin.groovy.Gremlin
import com.tinkerpop.blueprints.Graph
import br.unicamp.ic.lis.cdms.util.Constants

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 12/25/12
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
class SA {
    //setting class variables with default values
    Graph graph
    String type = '' // metric type... should not be used here but its existence makes SA constructor simpler
    Float weight = 1.0f // weight of the metric.. same as above
    int c = 2 //maximum radius of the SA network
    Float t = 0.1f //activation threshold
    Float d = 0.9f //decay factor
    Float a = 100.0f
    String direction = Constants.BOTH //which direction the SA steps must follow
    Boolean weighted = false //whether edge weights must be multiplied to degrade the signal
    String weightProp = "weight" //property with containing edge weights
    Boolean dividePotential = false // whether activation potential will be divided among neighbors (true) or passed integrally (false)
    //def NOTFOLLOW = ["http://www.w3.org/1999/02/22-rdf-syntax-ns#type"]


    def orig
    def dest

    static {
        Gremlin.load()
    }

    SA (){
        //default constructor
    }

    SA(context, dividePotential){
        context.attributes().each{key, value ->
            this."$key" = value
        }
        this.dividePotential = dividePotential
    }

    float process(orig, dest){

        def A = [:].withDefault{0.0f} //activated nodes

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
                            if (n) A[it] = 0.0f
                            //println "A ${A}"
                            neighbors.collect{it[-1]}
                        }.scatter
                .filter{it.map()['kind'] != 'literal'} //must be 'uri' for SPARQL queries to work. not necessary for cypher
                        .filter{it.id!=destid}
                        .loop('start'){it.loops<=this.c}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";


        return A[dest]

    }


}
