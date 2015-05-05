package br.unicamp.ic.lis.cdms.benchmark

import br.unicamp.ic.lis.cdms.source.Neo4jDB
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.blueprints.oupls.jung.GraphJung
import com.tinkerpop.gremlin.groovy.Gremlin
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import java.text.DecimalFormat


import java.util.Random
import edu.uci.ics.jung.algorithms.scoring.PageRank

//def db_path = '/Users/luizcelso/Dropbox/db/geoinfo'
def db_path = '/home/luizcelso/Dropbox/db/food'
def neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(db_path)
def graph = new Neo4jGraph(neoGraphDB)
int totalTests = 30

registerShutdownHook( neoGraphDB )
Gremlin.load()


j = new GraphJung(graph)
pr = new PageRank(j, 0.15d)

pr.evaluate()

def vertices = j.getVertices().collect{ [it, pr.getVertexScore(it)] }

//def vertices = [[graph.v(1111), 1.5], [graph.v(1113), 1.5],[graph.v(1044661), 0.66]]
//def vertices = [[graph.v(1), 1.5], [graph.v(11), 1.5],[graph.v(3), 0.66]]

println "total vertices: ${vertices.size()}"

//vertices.each{println it[0].id + ": " + graph.v(it[0].id).map()}


Random rand = new Random()
int max = vertices.size() - 1

def deltaTotal = 0.0
def gainTotal = 0.0

totalTests.times{
    def orig = rand.nextInt(max) + 1 //to avoid root node
    def dest = rand.nextInt(max) + 1
    while (orig == dest) dest = rand.nextInt(max) + 1

    def predictedBest, predictedWorst, root, target
    if (vertices[orig][1] < vertices[dest][1]){
        root = vertices[orig][0]
        target = vertices[dest][0]
    } else {
        root = vertices[dest][0]
        target = vertices[orig][0]
    }


    def cBest, cWorst = 0

    predictedBest = Timer.closureBenchmark{cBest=shortestPath(root, target, 10)}
    println "total predicted best time: ${predictedBest} --> ${predictedBest/1000/60/60}h ${predictedBest/1000/60}min"
    println "total nodes: ${cBest} -- ${new DecimalFormat("##,###").format(cBest)}"


    (root, target) = [target, root]  // swap


    predictedWorst = Timer.closureBenchmark{cWorst=shortestPath(root, target, 10)}
    //predictedWorst = Timer.closureBenchmark{path = root.both().gather.scatter.except(s).store(s).sideEffect{c++}.loop(6){it.object.id != target.id && it.loops <= 10}.has('id',target.id).path().toList()}

    println "total predicted worst time: ${predictedWorst} --> ${predictedWorst/1000/60/60}h ${predictedWorst/1000/60}min"
    println "total nodes: ${cWorst} -- ${new DecimalFormat("##,###").format(cWorst)}"


    //println "best " + graph.v(vertices[orig][0].id).both.loop(1){println it.object.id + "xxx" + vertices[dest][0].id; println it.object.id == vertices[dest][0].id; it.object.id != vertices[dest][0].id && it.loops < 5}.path().toList()

    deltaTotal += (predictedWorst - predictedBest)
    println "from: ${vertices[orig][0]}(${vertices[orig][1]}) to: ${vertices[dest][0]}(${vertices[dest][1]}) - best predicted time: ${predictedBest} - worst predicted time: ${predictedWorst} - delta: ${predictedWorst - predictedBest}"

    def gain = 1.0 - (cBest/cWorst)
    gainTotal += gain
    println "from: ${vertices[orig][0]}(${vertices[orig][1]}) to: ${vertices[dest][0]}(${vertices[dest][1]}) - best predicted count: ${cBest} - worst predicted count: ${cWorst} - gain: ${gain}"




}

println "delta total time: ${deltaTotal} --> ${deltaTotal/1000/60/60}h ${deltaTotal/1000/60}min"
println "average gain: ${gainTotal/totalTests} -- ${new DecimalFormat("##,###").format(gainTotal/totalTests)}"


def shortestPath(root, target, int n){
    Gremlin.load()
    def s = [root] as Set
    def neighbors = [root] as Set
    def newNeighbors = [] as Set

    while (n--){
        newNeighbors = []
        neighbors.each{
            newNeighbors.addAll(it.both().except(s).store(s).toList())
        }
        neighbors = newNeighbors
        //println "processing: ${neighbors}"
        if (neighbors.contains(target)) break

    }
    return s.size()

}


private static void registerShutdownHook( final GraphDatabaseService graphDb )
{
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running example before it's completed)
    Runtime.getRuntime().addShutdownHook( new Thread()
    {
        @Override
        public void run()
        {
            graphDb.shutdown();
        }
    } );
}




