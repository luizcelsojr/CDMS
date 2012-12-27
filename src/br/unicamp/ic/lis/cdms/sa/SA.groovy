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
    String follow = Constants.BOTH

    def orig
    def dest

    static {
        Gremlin.load()
    }

    def benchmark = { closure ->
        def start, now
        start = System.currentTimeMillis()
        closure.call()
        now = System.currentTimeMillis()
        now - start
    }


    float process(orig, dest){
        def weightProp = "weight"
        def A = [:].withDefault{0}

        def t = 0.1 //activation threshold
        def d = 0.9 //decay factor

        println "SA - ${orig} --> ${dest} \n Follow = ${this.follow}"

        def m = [:].withDefault{0}
        def p = [:]

        //def NOTFOLLOW = ["http://www.w3.org/1999/02/22-rdf-syntax-ns#type"]

        def destid = dest.id.toString()

        def duration = benchmark {

                A = [:].withDefault{0}
                A[orig] = 100

                orig.as('start')
                        .filter{
                            A[it] > t}
                        .transform{
                    //TODO set DIRECTION
                            def neighbors = it.inE.outV.path().toList()

                            def n = neighbors.size()
                            //TODO remove (after new benchmarks)
                            if (n > 1000 | n == 0) {//println "vaza ${it}.....";
                                return []}
                            def Atransfer = (A[it] * d)
                            neighbors.each{
                                A[it[-1]] += Atransfer * it[1].getProperty(weightProp).toFloat() // it is the path, it[-1] is the outV
                            }
                            if (n) A[it] = 0
                            //println "A ${A}"
                            neighbors.collect{it[-1]}
                        }.scatter
                        .filter{it.id!=destid}
                        .loop('start'){it.loops<=3}{(it.object.id==destid)}.iterate() //println "it.object.id=${it.object.id}";

        }
        println "execution took ${duration} ms"
        return A[dest]

    }


}
