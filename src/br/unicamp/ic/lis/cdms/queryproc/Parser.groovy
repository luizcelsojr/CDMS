package br.unicamp.ic.lis.cdms.queryproc

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/1/13
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
class Parser {
    def parse(query){ //TODO: query should be the query string, not a script file as it is now
        def queryConfig
        def shell = new GroovyShell()



        def file = new File(query)

        queryConfig = shell.evaluate(file.text)
        return queryConfig
    }
}
