package br.unicamp.ic.lis.cdms.data.load
import org.neo4j.graphdb.*
import org.neo4j.helpers.collection.MapUtil
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.neo4j.unsafe.batchinsert.LuceneBatchInserterIndexProvider;


dbDir = "/Users/luizcelso/Dropbox/db/food"
FileUtils.deleteRecursively( new File(dbDir) );

//db = new EmbeddedGraphDatabase(dbdir)
BatchInserter inserter = BatchInserters.inserter(dbDir);

baseDir= "/Users/luizcelso/Dropbox/phd/db/food-sources/"

//def fileNodes, fileEdges

AllFileNodes = ["nodes.csv", "recipes.csv"]

AllFileEdges = ["of_cuisine", "belongs_to", "comp_part_of", "located_in", "ingr_part_of"]

def header = null

indexProvider = new LuceneBatchInserterIndexProvider( inserter );
foodIdx = indexProvider.nodeIndex( "food", MapUtil.stringMap( "type", "exact" ) );


//process nodes

for (fileNodes in AllFileNodes) {
    header = null
    new File(baseDir + fileNodes).splitEachLine(",") {fields ->
        props = [:]
        if (!header) {
            header = fields[1..-1]
        } else {
            i = 1
            id = fields[0].toLong()
            header.each{
                props[it]=fields[i++]
            }
        println "$id ${props}"
            def node = inserter.createNode(id, props)
            foodIdx.add(id, props)
        }
    }
}

//process edges



for (fileEdges in AllFileEdges) {
    header = null
    new File(baseDir + fileEdges + ".csv").splitEachLine(",") {fields ->
        if (!header) {
            header = 1
        } else {
            i = 2
            source = fields[0].toLong()
            target = fields[1].toLong()

            inserter.createRelationship( source, target, DynamicRelationshipType.withName(fileEdges), [:] );
        }
    }

}
foodIdx.flush();
indexProvider.shutdown();
inserter.shutdown();
//start a=node:symdia(Type="Symptom") return a;

