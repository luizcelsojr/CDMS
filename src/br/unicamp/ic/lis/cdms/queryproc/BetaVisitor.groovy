package br.unicamp.ic.lis.cdms.queryproc

import br.unicamp.ic.lis.cdms.algebra.Operators
import br.unicamp.ic.lis.cdms.queryproc.parser.betaBaseVisitor
import br.unicamp.ic.lis.cdms.queryproc.parser.betaParser
import br.unicamp.ic.lis.cdms.source.Table
import br.unicamp.ic.lis.cdms.util.Constants
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
        assert memory[ctx.r.text], "Runtime error: Table ${ctx.r.text} not declared."
        def r = memory[ctx.r.text]

        assert memory[ctx.v.text], "Runtime error: Table ${ctx.v.text} not declared."
        def v = memory[ctx.v.text]

        assert memory[ctx.e.text], "Runtime error: Table ${ctx.e.text} not declared."
        def e = memory[ctx.e.text]

        def n = ctx.n.text.toInteger()

        def dir
        switch(ctx.dir.text){
            case 'IN':
                dir = Constants.INBOUND
                break
            case 'OUT':
                dir = Constants.OUTBOUND
                break
            default:
                dir = Constants.BOTH
                break
        }

        def labels = ctx.list_labels().text.split(',')*.trim()

        def set = visit(ctx.set)

        def map = visit(ctx.map)

        List reduce = visit(ctx.reduce)

        def update = visit(ctx.update)

        println "$r, $e, $n, $v, $labels,\n $set, $map, $update"
        println reduce

        Table t = this.opr.beta(r, v, e, n, { true }, dir, labels, [set] as List, [map] as List, reduce[0] as List, [reduce[1]] as List, [update] as List)


        //        rConnSmall = basicOpr.beta(rConnSmall, eConn, new Table(), 4, { true }, Constants.BOTH, ['connects'], ["it.minDist = 0.0f", "it.maxDist = 0.0f"], ["it.minDist = it.minDist + it.E_Weight.toFloat()", "it.maxDist = it.maxDist + it.E_Weight.toFloat()"], ["id_n", "id"], [[aggr: "min", func: "it.minDist", as: "minDist"], [aggr: "max", func: "it.maxDist", as: "maxDist"]], ["current.minDist = [newV.minDist, current.minDist].min()", "current.maxDist = [newV.maxDist, current.maxDist].max()"])


        //Table t = new Table()
        return t
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
        def right = visit(ctx.right)
        def test =  ( ctx.test.getType() == betaParser.EQ )? "==": ctx.test.text
        return "$left $test $right";

        }

    @Override public String visitAssig_expr(@NotNull betaParser.Assig_exprContext ctx) {
        def att = (ctx.attribute.getType()== betaParser.IDENTIFIER)?"it." + ctx.attribute.text: ctx.attribute.text
        return att + '=' + visit(ctx.expr())
        //return visitChildren(ctx);
    }

    @Override public List visitAssig_aggr(@NotNull betaParser.Assig_aggrContext ctx) {

        def by = ctx.list_labels().text.split(',')*.trim()
        def agg = ctx.aggr.text //.toLowercase()
        return [by, [aggr: agg.toLowerCase(), func: "it.${ctx.attribute.text}", as: ctx.attribute.text]]
    }

    @Override public String visitUpdate_agg(@NotNull betaParser.Update_aggContext ctx) {
        def l = ctx.list_labels().text.split(',')*.trim() //.collect{"it." + it}
        def agg = ctx.aggr.text.toLowerCase()
        return "$l.$agg()"
    }

    @Override public String visitUpdate_exp(@NotNull betaParser.Update_expContext ctx) { return 'it.'+ ctx.attribute.text + '=' + visit(ctx.expr()) }


    @Override public String visitValue(@NotNull betaParser.ValueContext ctx) { return (ctx.v.getType()== betaParser.IDENTIFIER)? 'it.' + ctx.v.text: ctx.v.text }

    @Override public String visitExpr_pm(@NotNull betaParser.Expr_pmContext ctx) { return visit(ctx.expr(0)) + ctx.op.text + visit(ctx.expr(1)) }

    @Override public String visitExpr_md(@NotNull betaParser.Expr_mdContext ctx) { return visit(ctx.expr(0)) + ctx.op.text + visit(ctx.expr(1)) }

    @Override public String visitExpr_number(@NotNull betaParser.Expr_numberContext ctx) { return ctx.text }

    @Override public String visitExpr_parens(@NotNull betaParser.Expr_parensContext ctx) { return "(" + visit(ctx.expr()) + ")" }

    @Override public String visitExpr_id(@NotNull betaParser.Expr_idContext ctx) { return 'it.' + ctx.text }

    @Override public String visitExpr_tableid(@NotNull betaParser.Expr_tableidContext ctx) { return "it." + ctx.QUALIFIED_ID().text.replace(".", "_") }

    @Override public String visitExpr_cast(@NotNull betaParser.Expr_castContext ctx) {
        def cast
        switch(ctx.type.getType()){
            case betaParser.CAST_FLOAT:
                cast = "toFloat()"
                break
            case betaParser.CAST_INTEGER:
                cast = "toInteger()"
                break
            default:
                cast = "toInteger()"
                break
        }

        return '(' + visit(ctx.expr()) + ').' + cast;
    }

    @Override public String visitExp_agg(@NotNull betaParser.Exp_aggContext ctx) {
        def l = ctx.list_identifiers().text.split(',')*.trim() //.collect{"it." + it}
        def agg = ctx.aggr.text.toLowerCase()
        return "$l.$agg()"
    }
}

