// Generated from /Users/luizcelso/Dropbox/workspace/CDMS2/src/br/unicamp/ic/lis/cdms/queryproc/parser/beta.g4 by ANTLR 4.5
package br.unicamp.ic.lis.cdms.queryproc.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link betaParser}.
 */
public interface betaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link betaParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(@NotNull betaParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(@NotNull betaParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expr(@NotNull betaParser.Assign_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expr(@NotNull betaParser.Assign_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#return_expr}.
	 * @param ctx the parse tree
	 */
	void enterReturn_expr(@NotNull betaParser.Return_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#return_expr}.
	 * @param ctx the parse tree
	 */
	void exitReturn_expr(@NotNull betaParser.Return_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(@NotNull betaParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(@NotNull betaParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#select}.
	 * @param ctx the parse tree
	 */
	void enterSelect(@NotNull betaParser.SelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#select}.
	 * @param ctx the parse tree
	 */
	void exitSelect(@NotNull betaParser.SelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#project}.
	 * @param ctx the parse tree
	 */
	void enterProject(@NotNull betaParser.ProjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#project}.
	 * @param ctx the parse tree
	 */
	void exitProject(@NotNull betaParser.ProjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#sscan}.
	 * @param ctx the parse tree
	 */
	void enterSscan(@NotNull betaParser.SscanContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#sscan}.
	 * @param ctx the parse tree
	 */
	void exitSscan(@NotNull betaParser.SscanContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#beta}.
	 * @param ctx the parse tree
	 */
	void enterBeta(@NotNull betaParser.BetaContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#beta}.
	 * @param ctx the parse tree
	 */
	void exitBeta(@NotNull betaParser.BetaContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#table_ref}.
	 * @param ctx the parse tree
	 */
	void enterTable_ref(@NotNull betaParser.Table_refContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#table_ref}.
	 * @param ctx the parse tree
	 */
	void exitTable_ref(@NotNull betaParser.Table_refContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#list_labels}.
	 * @param ctx the parse tree
	 */
	void enterList_labels(@NotNull betaParser.List_labelsContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#list_labels}.
	 * @param ctx the parse tree
	 */
	void exitList_labels(@NotNull betaParser.List_labelsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndOr}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void enterAndOr(@NotNull betaParser.AndOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndOr}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void exitAndOr(@NotNull betaParser.AndOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void enterParens(@NotNull betaParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void exitParens(@NotNull betaParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColTest}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void enterColTest(@NotNull betaParser.ColTestContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColTest}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 */
	void exitColTest(@NotNull betaParser.ColTestContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#col_test}.
	 * @param ctx the parse tree
	 */
	void enterCol_test(@NotNull betaParser.Col_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#col_test}.
	 * @param ctx the parse tree
	 */
	void exitCol_test(@NotNull betaParser.Col_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#assig_expr}.
	 * @param ctx the parse tree
	 */
	void enterAssig_expr(@NotNull betaParser.Assig_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#assig_expr}.
	 * @param ctx the parse tree
	 */
	void exitAssig_expr(@NotNull betaParser.Assig_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#assig_aggr}.
	 * @param ctx the parse tree
	 */
	void enterAssig_aggr(@NotNull betaParser.Assig_aggrContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#assig_aggr}.
	 * @param ctx the parse tree
	 */
	void exitAssig_aggr(@NotNull betaParser.Assig_aggrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code update_agg}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_agg(@NotNull betaParser.Update_aggContext ctx);
	/**
	 * Exit a parse tree produced by the {@code update_agg}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_agg(@NotNull betaParser.Update_aggContext ctx);
	/**
	 * Enter a parse tree produced by the {@code update_exp}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_exp(@NotNull betaParser.Update_expContext ctx);
	/**
	 * Exit a parse tree produced by the {@code update_exp}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_exp(@NotNull betaParser.Update_expContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_pm}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_pm(@NotNull betaParser.Expr_pmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_pm}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_pm(@NotNull betaParser.Expr_pmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_number}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_number(@NotNull betaParser.Expr_numberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_number}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_number(@NotNull betaParser.Expr_numberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_md}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_md(@NotNull betaParser.Expr_mdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_md}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_md(@NotNull betaParser.Expr_mdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_parens}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_parens(@NotNull betaParser.Expr_parensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_parens}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_parens(@NotNull betaParser.Expr_parensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_id}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_id(@NotNull betaParser.Expr_idContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_id}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_id(@NotNull betaParser.Expr_idContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_tableid}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_tableid(@NotNull betaParser.Expr_tableidContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_tableid}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_tableid(@NotNull betaParser.Expr_tableidContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_cast}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_cast(@NotNull betaParser.Expr_castContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_cast}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_cast(@NotNull betaParser.Expr_castContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exp_agg}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExp_agg(@NotNull betaParser.Exp_aggContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exp_agg}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExp_agg(@NotNull betaParser.Exp_aggContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull betaParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull betaParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link betaParser#list_identifiers}.
	 * @param ctx the parse tree
	 */
	void enterList_identifiers(@NotNull betaParser.List_identifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link betaParser#list_identifiers}.
	 * @param ctx the parse tree
	 */
	void exitList_identifiers(@NotNull betaParser.List_identifiersContext ctx);
}