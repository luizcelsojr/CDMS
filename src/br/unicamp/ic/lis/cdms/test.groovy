package br.unicamp.ic.lis.cdms



/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/2/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */

def shell = new GroovyShell()



def file = new File('/Users/luizcelso/workspace/CDMS/queries/query-sparql-rel-actors-woody.groovy')

def query = shell.evaluate(file.text)


query.ranking.metric.each{println it.@type}
query.ranking.@total = query.ranking.metric.size()

//println query.ranking[1]
//println query.ranking."metric1".@type[0]
//println query.ranking.value()
//query.ranking.children.each{println it}
//config.query.ranking.each{println it}

def writer = new StringWriter()
query.print(new PrintWriter(writer))
println writer.toString()


println query.ranking
//def builder = new NodeBuilder()
//def query = builder.query{
//
//}


