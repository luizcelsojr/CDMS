package br.unicamp.ic.lis.cdms.sa

import com.tinkerpop.blueprints.Vertex

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/1/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
class ActivatedNetwork {
    def network = [:].withDefault{[:]}
    def nodeList = []
    def r
    def maxRadius

    ActivatedNetwork(int maxRadius, int seed){
        this.maxRadius = maxRadius

        this.r = new Random(seed + 1) //just to make it different from the seed for RandomWalker... doesnt really matter
    }


    boolean add (Vertex v, Float potential, int radius){
        if (this.network.containsKey(v)) {
            this.network[v].potential+=potential
            return true
        } else{
            this.network[v].node = v
            this.network[v].potential = potential
            this.network[v].radius = radius
            if (radius < this.maxRadius) this.nodeList.add(v)
            return true
        }

    }

    void reset(){
        this.network = [:].withDefault{[:]}
        this.nodeList = []

    }

    Vertex getRandom(){
        return nodeList[this.r.nextInt(nodeList.size())]
    }


}
