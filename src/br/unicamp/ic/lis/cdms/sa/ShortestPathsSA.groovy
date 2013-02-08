package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.randomwalker.RW
import br.unicamp.ic.lis.cdms.util.Constants
import groovy.transform.InheritConstructors
import org.neo4j.cypher.ExecutionEngine

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/7/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */

@InheritConstructors
class ShortestPathsSA extends SA {
    def graph = null
    def engine = null

        ShortestPathsSA(context, dividePotential, graph){

        super(context, dividePotential)

        this.graph = graph

        ExpandoMetaClass.enableGlobally()
        scala.collection.immutable.Map.metaClass.getProperty = {name -> delegate.get(name).get()}
        //Map2.metaClass.setProperty = {name, val -> delegate.setProperty(name, val)}

        this.engine = new ExecutionEngine( this.graph );
    }



    float process(orig, dest){

        def paths = getShortestPaths(orig, dest)


        def A = [:].withDefault{0.0f} //activated nodes

        def destid = dest.id //.toString()

        def countIterations = 0

        A[orig] = this.a

        orig.as('start')
                .sideEffect{countIterations++}
        .filter{(A[it] > this.t)} // and (countIterations < this.maxIterations)
                .transform{
            def neighbors = []


            def ids = paths.clone().collect{it.head()}

            println "ids ${ids}"
            paths = paths.each{it.remove(0)}.findAll{it.size()}

            if (this.direction != Constants.OUTBOUND) neighbors.addAll(it.inE.filter{it.id in ids}.outV.path().toList()) // if INBOUND or BOTH, add all inbound edges
            if (this.direction != Constants.INBOUND) neighbors.addAll(it.outE.filter{it.id in ids}.inV.path().toList()) // if OUTBOUND or BOTH, add all outbound edges


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


    List getShortestPaths(orig, dest) {


        def query = """
START d=node(${orig.id}), e=node(${dest.id})
MATCH p = allShortestPaths( d-[*..${this.c}]-e )
RETURN p
"""
        println query

        def results = engine.execute(query);

        def paths = []

        while(results.hasNext()) {
            def path = []
            results.next().p.path.each{path.add(it.id)}
            paths.add(path)
        }

        println paths

        //System.exit(1)
        return paths
    }



}

