package br.unicamp.ic.lis.cdms



/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/2/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */

def shell = new GroovyShell()

def file = new File('/home/lis/luizcelso/workspace/CDMS/queries/query-sparql-rel-actors-woody.groovy')

def config = shell.evaluate(file.text)


println config.rank
//config.query.ranking.each{println it}



