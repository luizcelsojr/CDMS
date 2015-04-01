package br.unicamp.ic.lis.cdms.queryproc

import br.unicamp.ic.lis.cdms.queryproc.parser.betaBaseListener
import br.unicamp.ic.lis.cdms.queryproc.parser.betaParser
import org.antlr.v4.runtime.misc.NotNull

/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 3/26/15
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
class BetaListener extends betaBaseListener {
    @Override public void enterBool_exp(@NotNull betaParser.Bool_expContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBool_exp(@NotNull betaParser.Bool_expContext ctx) {

    }

    @Override public void enterCol_test(@NotNull betaParser.Col_testContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCol_test(@NotNull betaParser.Col_testContext ctx) {
        println ctx //.INT().getText()

    }


}
