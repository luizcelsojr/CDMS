package br.unicamp.ic.lis.cdms.data.load

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import org.neo4j.graphdb.*
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.helpers.collection.MapUtil
import org.neo4j.kernel.EmbeddedGraphDatabase
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.unsafe.batchinsert.BatchInserter
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.neo4j.unsafe.batchinsert.LuceneBatchInserterIndexProvider;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader


dbDir = "/Users/luizcelso/Dropbox/db/food"
FileUtils.deleteRecursively( new File(dbDir) );

InputStream ins = new FileInputStream("/opt/food.graphml");

neoGraphDB = new GraphDatabaseFactory().newEmbeddedDatabase(dbDir)
graph = new Neo4jGraph(neoGraphDB)

GraphMLReader reader = new GraphMLReader(graph);
reader.setVertexIdKey("id");
reader.setEdgeIdKey("id");
reader.setEdgeLabelKey("label");
reader.inputGraph(ins)



