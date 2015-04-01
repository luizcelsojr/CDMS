package br.unicamp.ic.lis.cdms.source

import groovy.transform.AutoClone

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */

class Table implements Iterable{
    private List contents = []
    private schema = [] as Set
    Integer size = 0

    Table (Map[] t){
        for (r in t)
            this.addRow(r)
    }

    Table (List schema, t){
        assert t.size() > 0, "Wooops, can't create table from empty list"
        for (r in t){
            assert r.size() == schema.size(), "Woops, row size ($r.size) is different from schema ($schema.size) size"
            Map newRow = [:]
            for (i in 0..(schema.size() - 1)) newRow[schema[i]] = r[i]

            this.addRow(newRow)
        }
    }

    Set getSchema(){return this.schema}

    def addRow(Map row){
        this.contents.add(row.clone())
        for (a in row) this.schema.add(a.key)
        this.size++
    }

    Integer getSize(){return this.size}

    Table unique(){
        return new Table( this.contents.unique() as Map[])
    }

    def orderAsc(column) {
        this.contents.sort{a,b -> a."$column" <=> b."$column"}
    }

    def orderAsc(List attributes) {
        for (attr in attributes.reverse())
            this.contents.sort{a,b -> a."$attr" <=> b."$attr"}
    }

    def orderDesc(column) {
        this.contents.sort{a,b -> b."$column" <=> a."$column"}
    }

    def orderDesc(List attributes) {
        for (attr in attributes.reverse())
            this.contents.sort{a,b -> b."$attr" <=> a."$attr"}
    }

    Map getRowAt(Integer index){
        return this.contents[index].clone()
    }

    List getRawContents(){
        return this.contents
    }

    Table copy() {
        Table clone= new Table();
        //TODO: clone of a clone
        this.contents.each{clone.addRow(it)}

        return clone;
    }

    Boolean testEvery(Closure test){
        return this.contents.every(test)
    }

    Boolean testAny(Closure test){
        return this.contents.any(test)
    }


    def print(int count = 0){
        println "XXXXXXX table XXXXXXXXX"
        println this.schema

        if (!this.size) {
            println "XXXXXXX 0 rows XXXXXXXXX"
            return
        }

        if ((count < 1) || (count > this.size)) count = this.size

        for (row in this.contents[0..(count-1)]){
            println this.schema.collect{(row[it] == null)?"<null>":(row[it].class == String)?"'" + row[it] + "'":(row[it].class == Double)?String.format("%1\$,.2f", row[it]):row[it]}
        }
        println "XXXXXXX ${this.size} rows XXXXXXXXX"
    }

    @Override
    Iterator iterator() {
        Integer counter = 0
        [hasNext: { counter < this.size },
                next: { counter++;return this.contents[counter -1].clone() }] as Iterator
    }

}
