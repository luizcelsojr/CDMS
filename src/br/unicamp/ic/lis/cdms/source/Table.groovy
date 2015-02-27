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
    //private Integer counter = 0

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

    def print_old(){
        println "XXXXXXX table XXXXXXXXX"
        println this.schema
        this.contents.each{println it.collect{(!it.value)?"<null>":(it.value.class == String)?"'$it.value'":(it.value.class == Double)?String.format("%1\$,.2f", it.value):it.value}}
    }

    def print(){
        println "XXXXXXX table XXXXXXXXX"
        println this.schema

        for (row in this.contents){
            println this.schema.collect{(!row[it])?"<null>":(row[it].class == String)?"'" + row[it] + "'":(row[it].class == Double)?String.format("%1\$,.2f", row[it]):row[it]}
        }
    }


    Integer getSize(){
        return this.size
    }

    Table unique(){
        return new Table( this.contents.unique() as Map[])
    }

    def orderAsc(column) {
        this.contents.sort{a,b -> a."$column" <=> b."$column"}
    }

    def orderAsc(List attributes) {
        for (attr in attributes.reverse()){
            this.contents.sort{a,b -> a."$attr" <=> b."$attr"}
        }

    }

    def orderDesc(column) {
        this.contents.sort{a,b -> b."$column" <=> a."$column"}
    }

    Map getRowAt(Integer index){
        return this.contents[index].clone()
    }

    List getRawContents(){
        return this.contents
    }

    /*
    Table clone() {
        def bos = new ByteArrayOutputStream()
        def oos = new ObjectOutputStream(bos)
        oos.writeObject(this); oos.flush()
        def bin = new ByteArrayInputStream(bos.toByteArray())
        def ois = new ObjectInputStream(bin)
        return ois.readObject()
    }
    */

    Table copy() {
        Table clone= new Table();
        //TODO: clone of a clone
        this.contents.each{clone.addRow(it)}

        return clone;
    }

/*
    protected Object clone() throws CloneNotSupportedException {

        Table clone=(Table)super.clone();

        this.contents.each{clone.addRow(it)}

        return clone;

    }
*/

    @Override
    Iterator iterator() {
        Integer counter = 0
        [hasNext: { counter < this.size },
                next: { counter++;return this.contents[counter -1].clone() }] as Iterator
    }
/*
    public Table clone() throws CloneNotSupportedException {
        Table result = new Table()
        //result.contents = []
        this.contents.each{result.addRow(it)}
        //result.size = size
        return result
    }
*/
}
