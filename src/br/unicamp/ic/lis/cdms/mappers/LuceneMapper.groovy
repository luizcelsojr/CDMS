package br.unicamp.ic.lis.cdms.mappers

import com.tinkerpop.blueprints.TransactionalGraph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.gremlin.groovy.Gremlin
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.util.Version
/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/9/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
class LuceneMapper {
    TransactionalGraph graph = null
    def idxName = this.class.simpleName + 'Idx'
    def edgeLabel = this.class.simpleName + ':hasToken'
    def index = null

    def analyser = new StandardAnalyzer(Version.LUCENE_36)
    def parser = new QueryParser(Version.LUCENE_36, "", this.analyser)

    LuceneMapper (TransactionalGraph g) {
        this.graph = g


        if (!(this.index = this.graph.getIndex(this.idxName, Vertex.class))){
            this.index = this.graph.createIndex(this.idxName, Vertex.class)

        }

    }

    Vertex map (Vertex node){
        //return same node in case of mapping, null if no mapping
        Gremlin.load()
        def tokenNode


        def content

        if (!(content = node.content)) //content exists for anonymous (temporary) nodes
            content = node.outE('http://www.w3.org/2000/01/rdf-schema#label').inV.next().value

        if (!content) return null

        content =  QueryParser.escape(content)

        this.parser.parse(content).each{
            def value = it.toString()
            def inode
            def nodes = this.index.get('token', value)

            nodes.each{inode = it} //there should be a better way of doing this


            if (!inode) {
                inode = this.graph.addVertex()
                inode.token = value
                this.index.put('token', inode.token, inode)
            }

            if (!node.outE(this.edgeLabel).inV.any{it.id == inode.id}) //if the map isnt already there
                this.graph.addEdge(node, inode, this.edgeLabel) //add map

        }

        return node
    }

    Vertex map (String content){
        //returns new node in case of mapping, null if no mapping
        Vertex node = this.graph.addVertex()
        node.content = content

        return map(node)
    }

    void commit(){
        this.graph.stopTransaction(TransactionalGraph.Conclusion.SUCCESS)
    }

    void rollback(){
        this.graph.stopTransaction(TransactionalGraph.Conclusion.FAILURE)
    }

}

