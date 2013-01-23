package br.unicamp.ic.lis.cdms.sa

import br.unicamp.ic.lis.cdms.randomwalker.RW
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
    def Actv = [:].withDefault{0.0f} //activated nodes

    float process(orig, dest){


        this.Actv[orig] = this.a


        def rw = new RW(this, orig, dest, 10)

        rw.step()

        println rw.current

        rw.step()

        println rw.current


        return this.Actv[dest]

    }


}
