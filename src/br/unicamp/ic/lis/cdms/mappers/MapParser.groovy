package br.unicamp.ic.lis.cdms.mappers

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 1/1/13
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
class MapParser {
    def parse(query){ //TODO: query should be the query string, not a script file as it is now
        def queryConfig
        def shell = new GroovyShell()



        def file = new File(query)

        queryConfig = shell.evaluate(file.text)
        return queryConfig
    }

    void parseFromText(String query) {
        //old improvised parsing
        def rankby =/RANK BY ([1-9]) ([a-zA-Z]+) \((.*)\)/
        def comment =/--.*/
        def matcher

        for (line in query.split("\n")){
            switch (line) {
                case ~rankby:
                    matcher = ( line =~ rankby )
                    if (matcher.size()){
                        def metric = matcher[0][2]
                        def weight = matcher[0][1].toFloat()
                        this.rankings[metric] = ["weight":weight, "params":matcher[0][3]]
                        this.totalWeight += weight
                        //this.rank = matcher[0][1];
                        //this.params = matcher[0][2];
                    }
                    break
                case ~comment:
                    break
                default:
                    this.cypher += line + "\n"
            }
        }

        println("cypher: " + cypher)
        println "this.rankings = ${this.rankings}"
    }
}
