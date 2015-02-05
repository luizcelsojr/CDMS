package br.unicamp.ic.lis.cdms.source

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/2/15
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
class Table implements Iterable{
    private contents = []
    Integer size = 0
    //private Integer counter = 0

    def addRow(Map row){
        this.contents.add(row)
        this.size++
    }

    def print(){
        this.contents.each{println it}
    }

    Integer getSize(){
        return this.size
    }

    @Override
    Iterator iterator() {
        Integer counter = 0
        [hasNext: { counter < this.size },
                next: { counter++;return this.contents[counter -1] }] as Iterator
    }

}
