package br.unicamp.ic.lis.cdms.queryproc

import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.source.Neo4jDB
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import org.neo4j.graphdb.GraphDatabaseService
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.CompilerConfiguration

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 2/8/15
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
class BetaQueryProc extends QueryProcessor{

    def BetaQueryProc(GraphDatabaseService neoDB){
        //this.neoGraphDB = neoDB
        super(neoDB)
//        this.graph = new Neo4jGraph(this.neoGraphDB)
    }


    def processQuery(query){

        def db = new Neo4jDB(this.neoGraphDB)

        Operators opr = db.getOperators()
        //parser =
        //engine.execute(query, parser, operators)

        // Add imports for script.
        def importCustomizer = new ImportCustomizer()
        // import static com.mrhaki.blog.Type.*
        //importCustomizer.addStaticStars 'com.mrhaki.blog.Type'
        // import com.mrhaki.blog.Post as Article
        importCustomizer.addImport('Table', 'br.unicamp.ic.lis.cdms.source.Table')
        importCustomizer.addImport('Constants', 'br.unicamp.ic.lis.cdms.util.Constants')

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(importCustomizer)



        // Create shell and execute script.
        def sh = new GroovyShell(configuration)


        //sh.evaluate(query )
        Closure queryClosure = sh.evaluate("{opr -> " + query + "}")

        queryClosure(opr)

    }
}
