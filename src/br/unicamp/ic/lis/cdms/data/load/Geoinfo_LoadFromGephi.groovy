package br.unicamp.ic.lis.cdms.data.load
import org.neo4j.graphdb.*
import org.neo4j.helpers.collection.MapUtil
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.neo4j.unsafe.batchinsert.LuceneBatchInserterIndexProvider;


dbDir = "/Users/luizcelso/Dropbox/db/geoinfo-rest"
FileUtils.deleteRecursively( new File(dbDir) );

//db = new EmbeddedGraphDatabase(dbdir)
BatchInserter inserter = BatchInserters.inserter(dbDir);

fileNodes = "/Users/luizcelso/Dropbox/db/geoinfo-streets-rest-social-onto Nodes.csv"

fileEdges = "/Users/luizcelso/Dropbox/db/geoinfo-streets-rest-social-onto Edges.csv"

def header = null

indexProvider = new LuceneBatchInserterIndexProvider( inserter );
symdiaIdx = indexProvider.nodeIndex( "symdia", MapUtil.stringMap( "type", "exact" ) );

//symdiaIdx.setCacheCapacity( "type", 100000 );

//process nodes
new File(fileNodes).splitEachLine(",") {fields ->
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
		symdiaIdx.add(id, props)
	}
}

//process edges
header = null

//def linked = DynamicRelationshipType.withName("linked")

new File(fileEdges).splitEachLine(",") {fields ->
	if (!header) {
		header = fields[2..-1]
	} else {
		i = 2
		props = [:]
		source = fields[0].toLong()
		target = fields[1].toLong()
		header.each{
			props[it]=fields[i++]
		}
		
		
		inserter.createRelationship( source, target, DynamicRelationshipType.withName(props['Label']), props );
	}
}

symdiaIdx.flush();
indexProvider.shutdown();
inserter.shutdown();
//start a=node:symdia(Type="Symptom") return a;

