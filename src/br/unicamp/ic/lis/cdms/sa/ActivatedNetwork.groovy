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

    def ActivatedNetwork(){}


    boolean addOrUpdate (Vertex v, Vertex previous, Float potential){
        if (this.network.containsKey(v)) {
            this.network[v].potential+=potential
            this.network[v].totalPotential+=potential
            if (!this.network[v].previous.contains(previous)) this.network[v].previous.add(previous)
            return true
        } else{
            this.network[v].node = v
            this.network[v].potential = potential
            this.network[v].totalPotential = potential
            this.network[v].previous = []
            this.network[v].previous.add(previous)
            return true

        }
    }

    boolean setPotential (Vertex v, Float potential){
        if (this.network.containsKey(v)) {
            this.network[v].potential = potential
            this.network[v].totalPotential = potential
            return true
        } else{
            addOrUpdate(v, null, potential)
            println "ups, something went wrong. go get a cup of coffee. then stop being lazy and use proper exceptions"
            return false

        }
    }

    void reset(){
        this.network = [:].withDefault{[:]}
    }

    Float getAndDrainPotential(Vertex v){
        def potential = 0.0f
        if (this.network.containsKey(v)) {
            potential = this.network[v].potential
            this.network[v].potential = 0.0f
        }
        return potential
    }

    Float getPotential(Vertex v){
        def potential = 0.0f
        if (this.network.containsKey(v)) {
            potential = this.network[v].potential
        }
        return potential
    }

    Float getTotalPotential(Vertex v){
        def potential = 0.0f
        if (this.network.containsKey(v)) {
            potential = this.network[v].totalPotential
        }
        return potential
    }

    List getPrevious(Vertex v){
        if (this.network.containsKey(v)) {
            return this.network[v].previous
        }
        return []
    }

}
