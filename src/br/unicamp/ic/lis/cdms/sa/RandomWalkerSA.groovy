package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.randomwalker.RW
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
    def Actv = new RandomActivatedNetwork(this.c, this.seed) //activated nodes

    float process(orig, dest){

        this.Actv.reset()

        this.Actv.add(orig, this.a, 0)


        def rw = new RW(this, orig, dest, this.seed) //, 10


        this.steps.times {
            rw.step()
            //println this.Actv[dest]
        }
        if (this.Actv.network[dest]) println "${orig} -> ${this.Actv.network[dest]}"
        println "----------------------------------------------------------------${orig} -> ${this.Actv.network[dest]}"

        return (this.Actv.network[dest].potential)?this.Actv.network[dest].potential:0.0

    }


}

