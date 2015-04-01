package br.unicamp.ic.lis.cdms.queryproc

import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.queryproc.parser.betaBaseVisitor
import br.unicamp.ic.lis.cdms.queryproc.parser.betaParser
import br.unicamp.ic.lis.cdms.source.Table
import org.antlr.v4.runtime.misc.NotNull



/**
 * Created with IntelliJ IDEA.
 * User: luizcelso
 * Date: 3/26/15
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
class BetaVisitor extends betaBaseVisitor {
    Map memory = [:]
    Operators opr
    GroovyShell sh = new GroovyShell()

    BetaVisitor(Operators opr){
        this.opr = opr
    }

    @Override public Table visitQuery(@NotNull betaParser.QueryContext ctx) {
        visitChildren(ctx)
        return memory['return']
    }

    @Override public Table visitReturn_expr(@NotNull betaParser.Return_exprContext ctx) {
        //return visitChildren(ctx);
        memory['return'] = visit(ctx.operation())
    }

    @Override public Table visitAssign_expr(@NotNull betaParser.Assign_exprContext ctx) {
        String id = ctx.TABLE_ID().getText(); // id is left-hand side of '='
        Table value = visit(ctx.operation());
        memory[id] = value;
        return value;
        //return visitChildren(ctx);

    }

    @Override public Table visitBeta(@NotNull betaParser.BetaContext ctx) {
        println ctx.r
        println ctx.v
        println ctx.e
        println ctx.dir
        println ctx.list_labels().text.split(',')*.trim()

        //        rConnSmall = basicOpr.beta(rConnSmall, eConn, new Table(), 4, { true }, Constants.BOTH, ['connects'], ["it.minDist = 0.0f", "it.maxDist = 0.0f"], ["it.minDist = it.minDist + it.E_Weight.toFloat()", "it.maxDist = it.maxDist + it.E_Weight.toFloat()"], ["id_n", "id"], [[aggr: "min", func: "it.minDist", as: "minDist"], [aggr: "max", func: "it.maxDist", as: "maxDist"]], ["current.minDist = [newV.minDist, current.minDist].min()", "current.maxDist = [newV.maxDist, current.maxDist].max()"])


        return visitChildren(ctx);
    }

    @Override public Table visitSelect(@NotNull betaParser.SelectContext ctx) {
        Table t
        def exp = (ctx.bool_exp())? visit(ctx.bool_exp()): "true"

        String id = ctx.TABLE_ID().getText();

        assert memory[id], "Runtime error: Table $id not declared."

        t = this.opr.select(memory[id], this.sh.evaluate("{it -> " + exp + "}"))

        return t
    }

    @Override public Table visitSscan(@NotNull betaParser.SscanContext ctx) {
        Table t


        def exp = (ctx.bool_exp())? visit(ctx.bool_exp()): "true"
        println exp

        if (ctx.table.getType() == betaParser.VTABLE)
            t = this.opr.scanFilterV(this.sh.evaluate("{it -> " + exp + "}"))
        else
            t = this.opr.scanFilterE(this.sh.evaluate("{it -> " + exp + "}"))



        return t

    }

    @Override public Table visitTable_ref(@NotNull betaParser.Table_refContext ctx) {
        String id = ctx.TABLE_ID().getText();

        assert memory[id], "Runtime error: Table $id not declared."

        return memory[id]
    }



    @Override public String visitAndOr(@NotNull betaParser.AndOrContext ctx) {
        def left = visit(ctx.bool_exp(0));
        def right = visit(ctx.bool_exp(1));
        if ( ctx.logical.getType() == betaParser.AND ) return "$left && $right";
        return "$left || $right";
    }

    @Override public String visitParens(@NotNull betaParser.ParensContext ctx) {
        return "(" + visit(ctx.bool_exp()) + ")"
    }

    @Override public String visitColTest(@NotNull betaParser.ColTestContext ctx) {
        return visitChildren(ctx);
    }

    @Override public String visitCol_test(@NotNull betaParser.Col_testContext ctx) {
        def left = 'it.' + ctx.left.text;
        def right = (ctx.right.getType()== betaParser.IDENTIFIER)? 'it.' + ctx.right.text: ctx.right.text
        def test =  ( ctx.test.getType() == betaParser.EQ )? "==": ctx.test.text
        return "$left $test $right";

        }

    @Override public Table visitExpr(@NotNull betaParser.ExprContext ctx) { return visitChildren(ctx); }

    /*
    @Override public Table visitOperation(@NotNull betaParser.OperationContext ctx) {
        return visitChildren(ctx);
    }
*/
}

