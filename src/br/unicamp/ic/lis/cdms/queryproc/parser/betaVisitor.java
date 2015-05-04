// Generated from /Users/luizcelso/Dropbox/workspace/CDMS2/src/br/unicamp/ic/lis/cdms/queryproc/parser/beta.g4 by ANTLR 4.5
package br.unicamp.ic.lis.cdms.queryproc.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link betaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface betaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link betaParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(@NotNull betaParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#assign_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(@NotNull betaParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#return_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_expr(@NotNull betaParser.Return_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(@NotNull betaParser.OperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#select}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(@NotNull betaParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#project}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProject(@NotNull betaParser.ProjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#sscan}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSscan(@NotNull betaParser.SscanContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#beta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeta(@NotNull betaParser.BetaContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#table_ref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_ref(@NotNull betaParser.Table_refContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#list_labels}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_labels(@NotNull betaParser.List_labelsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndOr}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOr(@NotNull betaParser.AndOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(@NotNull betaParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColTest}
	 * labeled alternative in {@link betaParser#bool_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColTest(@NotNull betaParser.ColTestContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#col_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCol_test(@NotNull betaParser.Col_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#assig_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssig_expr(@NotNull betaParser.Assig_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#assig_aggr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssig_aggr(@NotNull betaParser.Assig_aggrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code update_agg}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate_agg(@NotNull betaParser.Update_aggContext ctx);
	/**
	 * Visit a parse tree produced by the {@code update_exp}
	 * labeled alternative in {@link betaParser#update_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate_exp(@NotNull betaParser.Update_expContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_pm}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_pm(@NotNull betaParser.Expr_pmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_number}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_number(@NotNull betaParser.Expr_numberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_md}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_md(@NotNull betaParser.Expr_mdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_parens}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_parens(@NotNull betaParser.Expr_parensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_id}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_id(@NotNull betaParser.Expr_idContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_tableid}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_tableid(@NotNull betaParser.Expr_tableidContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_cast}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_cast(@NotNull betaParser.Expr_castContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exp_agg}
	 * labeled alternative in {@link betaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_agg(@NotNull betaParser.Exp_aggContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(@NotNull betaParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link betaParser#list_identifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_identifiers(@NotNull betaParser.List_identifiersContext ctx);
}