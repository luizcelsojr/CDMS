package br.unicamp.ic.lis.cdms.data.load
import org.neo4j.graphdb.*
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.neo4j.unsafe.batchinsert.LuceneBatchInserterIndexProvider;

ExpandoMetaClass.enableGlobally()
PropertyContainer.metaClass.getProperty = {name -> delegate.getProperty(name)}
PropertyContainer.metaClass.setProperty = {name, val -> delegate.setProperty(name, val)}

dbDir = "/lishome-ext/luizcelso/graphdbs/hello"
FileUtils.deleteRecursively( new File(dbDir) );

//db = new EmbeddedGraphDatabase(dbdir)
BatchInserter inserter = BatchInserters.inserter(dbDir);

indexProvider = new LuceneBatchInserterIndexProvider( inserter );
symdiaIdx = indexProvider.nodeIndex( "symdia", MapUtil.stringMap( "type", "exact" ) );
//symdiaIdx.setCacheCapacity( "type", 100000 );

n1 = ["message":"Hello", "type":"greeting"]
def node1 = inserter.createNode(100,n1)
symdiaIdx.add(100,n1)


n2 = ["message":"world", "type":"planet"]
def node2 = inserter.createNode(101,n2)
symdiaIdx.add(101,n2)

def knows = DynamicRelationshipType.withName("KNOWS")

inserter.createRelationship( 100, 101, knows, ["message":'brave Neo4j'] );


inserter.shutdown();


// try it out from a normal db
GraphDatabaseService db = new EmbeddedGraphDatabase(dbDir);
db.allNodes.each{println it}
Node mNode = db.getNodeById( 100 );
Node cNode = mNode.getSingleRelationship( knows, Direction.OUTGOING )
		.getEndNode();

println mNode.getProperty( "type" )
assert "greeting" == mNode.getProperty( "type" )
db.shutdown();
