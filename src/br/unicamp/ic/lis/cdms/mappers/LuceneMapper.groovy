package br.unicamp.ic.lis.cdms.mappers

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
    def analyser = new StandardAnalyzer(Version.LUCENE_36)
    def parser = new QueryParser(Version.LUCENE_36, "", this.analyser)

    def run(){
        println this.parser.parse("yeah babe boo:judie foster slence of lambs")
    }
}

