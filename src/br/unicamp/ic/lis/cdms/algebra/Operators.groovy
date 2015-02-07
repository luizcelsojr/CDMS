package br.unicamp.ic.lis.cdms.algebra

import br.unicamp.ic.lis.cdms.source.Table

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/4/15
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Operators {
    public abstract Table select (Table t, Closure condition)

    public abstract Table beta (Table t, Integer n, Closure condition, direction, follow, List set, List mapFunctions, List reduceFunctions)

    public abstract Table scanFilterV(Closure filter)

    public abstract Table scanFilterE(Closure filter)

    public union (Table t1, Table t2){
        for (row in t2) t1.addRow(row)
        return t1
    }


}
