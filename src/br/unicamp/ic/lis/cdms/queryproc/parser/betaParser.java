// Generated from /Users/luizcelso/Dropbox/workspace/CDMS2/src/br/unicamp/ic/lis/cdms/queryproc/parser/beta.g4 by ANTLR 4.5
package br.unicamp.ic.lis.cdms.queryproc.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class betaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, CAST_FLOAT=15, CAST_INTEGER=16, 
		AND=17, OR=18, VTABLE=19, ETABLE=20, NEW=21, CURRENT=22, IN=23, OUT=24, 
		BOTH=25, MIN=26, MAX=27, SUM=28, TABLE_ID=29, IDENTIFIER=30, QUALIFIED_ID=31, 
		NEWLINE=32, INT=33, FLOAT=34, STRING=35, SINGLE_LINE_COMMENT=36, WS=37, 
		MUL=38, DIV=39, ADD=40, SUB=41, EQ=42, GT=43, ST=44;
	public static final int
		RULE_query = 0, RULE_assign_expr = 1, RULE_return_expr = 2, RULE_operation = 3, 
		RULE_select = 4, RULE_project = 5, RULE_sscan = 6, RULE_beta = 7, RULE_table_ref = 8, 
		RULE_list_labels = 9, RULE_bool_exp = 10, RULE_col_test = 11, RULE_assig_expr = 12, 
		RULE_assig_aggr = 13, RULE_update_expr = 14, RULE_expr = 15, RULE_value = 16, 
		RULE_list_identifiers = 17;
	public static final String[] ruleNames = {
		"query", "assign_expr", "return_expr", "operation", "select", "project", 
		"sscan", "beta", "table_ref", "list_labels", "bool_exp", "col_test", "assig_expr", 
		"assig_aggr", "update_expr", "expr", "value", "list_identifiers"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':='", "'RETURN'", "'SELECT'", "'('", "','", "'{'", "'}'", "')'", 
		"'PROJECT'", "'['", "']'", "'SCAN'", "'BETA'", "'BY'", "'FLOAT'", "'INTEGER'", 
		"'AND'", "'OR'", "'V'", "'E'", "'newV'", "'current'", "'IN'", "'OUT'", 
		"'BOTH'", "'MIN'", "'MAX'", "'SUM'", null, null, null, null, null, null, 
		null, null, null, "'*'", "'/'", "'+'", "'-'", "'='", "'>'", "'<'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "CAST_FLOAT", "CAST_INTEGER", "AND", "OR", "VTABLE", 
		"ETABLE", "NEW", "CURRENT", "IN", "OUT", "BOTH", "MIN", "MAX", "SUM", 
		"TABLE_ID", "IDENTIFIER", "QUALIFIED_ID", "NEWLINE", "INT", "FLOAT", "STRING", 
		"SINGLE_LINE_COMMENT", "WS", "MUL", "DIV", "ADD", "SUB", "EQ", "GT", "ST"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	@NotNull
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "beta.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public betaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class QueryContext extends ParserRuleContext {
		public Return_exprContext return_expr() {
			return getRuleContext(Return_exprContext.class,0);
		}
		public List<Assign_exprContext> assign_expr() {
			return getRuleContexts(Assign_exprContext.class);
		}
		public Assign_exprContext assign_expr(int i) {
			return getRuleContext(Assign_exprContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(betaParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(betaParser.NEWLINE, i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TABLE_ID) {
				{
				{
				setState(36); 
				assign_expr();
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NEWLINE) {
					{
					{
					setState(37); 
					match(NEWLINE);
					}
					}
					setState(42);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48); 
			return_expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_exprContext extends ParserRuleContext {
		public TerminalNode TABLE_ID() { return getToken(betaParser.TABLE_ID, 0); }
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public Assign_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterAssign_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitAssign_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitAssign_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_exprContext assign_expr() throws RecognitionException {
		Assign_exprContext _localctx = new Assign_exprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_assign_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); 
			match(TABLE_ID);
			setState(51); 
			match(T__0);
			setState(52); 
			operation();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_exprContext extends ParserRuleContext {
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public Return_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterReturn_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitReturn_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitReturn_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_exprContext return_expr() throws RecognitionException {
		Return_exprContext _localctx = new Return_exprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_return_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); 
			match(T__1);
			setState(55); 
			operation();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationContext extends ParserRuleContext {
		public SelectContext select() {
			return getRuleContext(SelectContext.class,0);
		}
		public SscanContext sscan() {
			return getRuleContext(SscanContext.class,0);
		}
		public ProjectContext project() {
			return getRuleContext(ProjectContext.class,0);
		}
		public BetaContext beta() {
			return getRuleContext(BetaContext.class,0);
		}
		public Table_refContext table_ref() {
			return getRuleContext(Table_refContext.class,0);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_operation);
		try {
			setState(62);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(57); 
				select();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(58); 
				sscan();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(59); 
				project();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 4);
				{
				setState(60); 
				beta();
				}
				break;
			case TABLE_ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(61); 
				table_ref();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectContext extends ParserRuleContext {
		public TerminalNode TABLE_ID() { return getToken(betaParser.TABLE_ID, 0); }
		public Bool_expContext bool_exp() {
			return getRuleContext(Bool_expContext.class,0);
		}
		public SelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitSelect(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectContext select() throws RecognitionException {
		SelectContext _localctx = new SelectContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_select);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64); 
			match(T__2);
			setState(65); 
			match(T__3);
			setState(66); 
			match(TABLE_ID);
			setState(67); 
			match(T__4);
			setState(68); 
			match(T__5);
			setState(70);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << IDENTIFIER) | (1L << QUALIFIED_ID) | (1L << INT) | (1L << STRING))) != 0)) {
				{
				setState(69); 
				bool_exp(0);
				}
			}

			setState(72); 
			match(T__6);
			setState(73); 
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProjectContext extends ParserRuleContext {
		public TerminalNode TABLE_ID() { return getToken(betaParser.TABLE_ID, 0); }
		public List_labelsContext list_labels() {
			return getRuleContext(List_labelsContext.class,0);
		}
		public ProjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_project; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterProject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitProject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitProject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProjectContext project() throws RecognitionException {
		ProjectContext _localctx = new ProjectContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_project);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); 
			match(T__8);
			setState(76); 
			match(T__3);
			setState(77); 
			match(TABLE_ID);
			setState(78); 
			match(T__4);
			setState(79); 
			match(T__9);
			setState(80); 
			list_labels();
			setState(81); 
			match(T__10);
			setState(82); 
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SscanContext extends ParserRuleContext {
		public Token table;
		public TerminalNode VTABLE() { return getToken(betaParser.VTABLE, 0); }
		public TerminalNode ETABLE() { return getToken(betaParser.ETABLE, 0); }
		public Bool_expContext bool_exp() {
			return getRuleContext(Bool_expContext.class,0);
		}
		public SscanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sscan; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterSscan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitSscan(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitSscan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SscanContext sscan() throws RecognitionException {
		SscanContext _localctx = new SscanContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_sscan);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); 
			match(T__11);
			setState(85); 
			match(T__3);
			setState(86);
			((SscanContext)_localctx).table = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==VTABLE || _la==ETABLE) ) {
				((SscanContext)_localctx).table = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(87); 
			match(T__4);
			setState(88); 
			match(T__5);
			setState(90);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << IDENTIFIER) | (1L << QUALIFIED_ID) | (1L << INT) | (1L << STRING))) != 0)) {
				{
				setState(89); 
				bool_exp(0);
				}
			}

			setState(92); 
			match(T__6);
			setState(93); 
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BetaContext extends ParserRuleContext {
		public Token r;
		public Token v;
		public Token e;
		public Token n;
		public Token dir;
		public List_labelsContext follow;
		public Assig_exprContext set;
		public Assig_exprContext map;
		public Assig_aggrContext reduce;
		public Assig_exprContext update;
		public List<TerminalNode> TABLE_ID() { return getTokens(betaParser.TABLE_ID); }
		public TerminalNode TABLE_ID(int i) {
			return getToken(betaParser.TABLE_ID, i);
		}
		public TerminalNode INT() { return getToken(betaParser.INT, 0); }
		public List_labelsContext list_labels() {
			return getRuleContext(List_labelsContext.class,0);
		}
		public List<Assig_exprContext> assig_expr() {
			return getRuleContexts(Assig_exprContext.class);
		}
		public Assig_exprContext assig_expr(int i) {
			return getRuleContext(Assig_exprContext.class,i);
		}
		public Assig_aggrContext assig_aggr() {
			return getRuleContext(Assig_aggrContext.class,0);
		}
		public TerminalNode IN() { return getToken(betaParser.IN, 0); }
		public TerminalNode OUT() { return getToken(betaParser.OUT, 0); }
		public TerminalNode BOTH() { return getToken(betaParser.BOTH, 0); }
		public BetaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beta; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterBeta(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitBeta(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitBeta(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BetaContext beta() throws RecognitionException {
		BetaContext _localctx = new BetaContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_beta);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); 
			match(T__12);
			setState(96); 
			match(T__3);
			setState(97); 
			((BetaContext)_localctx).r = match(TABLE_ID);
			setState(98); 
			match(T__4);
			setState(99); 
			((BetaContext)_localctx).v = match(TABLE_ID);
			setState(100); 
			match(T__4);
			setState(101); 
			((BetaContext)_localctx).e = match(TABLE_ID);
			setState(102); 
			match(T__4);
			setState(103); 
			((BetaContext)_localctx).n = match(INT);
			setState(104); 
			match(T__4);
			setState(105);
			((BetaContext)_localctx).dir = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IN) | (1L << OUT) | (1L << BOTH))) != 0)) ) {
				((BetaContext)_localctx).dir = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(106); 
			match(T__4);
			setState(107); 
			match(T__9);
			setState(108); 
			((BetaContext)_localctx).follow = list_labels();
			setState(109); 
			match(T__10);
			setState(110); 
			match(T__4);
			setState(111); 
			match(T__5);
			setState(112); 
			((BetaContext)_localctx).set = assig_expr();
			setState(113); 
			match(T__6);
			setState(114); 
			match(T__4);
			setState(115); 
			match(T__5);
			setState(116); 
			((BetaContext)_localctx).map = assig_expr();
			setState(117); 
			match(T__6);
			setState(118); 
			match(T__4);
			setState(119); 
			match(T__5);
			setState(120); 
			((BetaContext)_localctx).reduce = assig_aggr();
			setState(121); 
			match(T__6);
			setState(122); 
			match(T__4);
			setState(123); 
			match(T__5);
			setState(124); 
			((BetaContext)_localctx).update = assig_expr();
			setState(125); 
			match(T__6);
			setState(126); 
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_refContext extends ParserRuleContext {
		public TerminalNode TABLE_ID() { return getToken(betaParser.TABLE_ID, 0); }
		public Table_refContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_ref; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterTable_ref(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitTable_ref(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitTable_ref(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Table_refContext table_ref() throws RecognitionException {
		Table_refContext _localctx = new Table_refContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_table_ref);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128); 
			match(TABLE_ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_labelsContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(betaParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(betaParser.IDENTIFIER, i);
		}
		public List_labelsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_labels; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterList_labels(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitList_labels(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitList_labels(this);
			else return visitor.visitChildren(this);
		}
	}

	public final List_labelsContext list_labels() throws RecognitionException {
		List_labelsContext _localctx = new List_labelsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_list_labels);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(130); 
					match(IDENTIFIER);
					setState(131); 
					match(T__4);
					}
					} 
				}
				setState(136);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(137); 
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bool_expContext extends ParserRuleContext {
		public Bool_expContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_exp; }
	 
		public Bool_expContext() { }
		public void copyFrom(Bool_expContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndOrContext extends Bool_expContext {
		public Token logical;
		public List<Bool_expContext> bool_exp() {
			return getRuleContexts(Bool_expContext.class);
		}
		public Bool_expContext bool_exp(int i) {
			return getRuleContext(Bool_expContext.class,i);
		}
		public TerminalNode AND() { return getToken(betaParser.AND, 0); }
		public TerminalNode OR() { return getToken(betaParser.OR, 0); }
		public AndOrContext(Bool_expContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterAndOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitAndOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitAndOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensContext extends Bool_expContext {
		public Bool_expContext bool_exp() {
			return getRuleContext(Bool_expContext.class,0);
		}
		public ParensContext(Bool_expContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ColTestContext extends Bool_expContext {
		public Col_testContext col_test() {
			return getRuleContext(Col_testContext.class,0);
		}
		public ColTestContext(Bool_expContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterColTest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitColTest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitColTest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_expContext bool_exp() throws RecognitionException {
		return bool_exp(0);
	}

	private Bool_expContext bool_exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Bool_expContext _localctx = new Bool_expContext(_ctx, _parentState);
		Bool_expContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_bool_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			switch (_input.LA(1)) {
			case IDENTIFIER:
			case QUALIFIED_ID:
			case INT:
			case STRING:
				{
				_localctx = new ColTestContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(140); 
				col_test();
				}
				break;
			case T__3:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(141); 
				match(T__3);
				setState(142); 
				bool_exp(0);
				setState(143); 
				match(T__7);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(152);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AndOrContext(new Bool_expContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_bool_exp);
					setState(147);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(148);
					((AndOrContext)_localctx).logical = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==AND || _la==OR) ) {
						((AndOrContext)_localctx).logical = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(149); 
					bool_exp(4);
					}
					} 
				}
				setState(154);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Col_testContext extends ParserRuleContext {
		public ValueContext left;
		public Token test;
		public ValueContext right;
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode EQ() { return getToken(betaParser.EQ, 0); }
		public TerminalNode GT() { return getToken(betaParser.GT, 0); }
		public TerminalNode ST() { return getToken(betaParser.ST, 0); }
		public Col_testContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_col_test; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterCol_test(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitCol_test(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitCol_test(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Col_testContext col_test() throws RecognitionException {
		Col_testContext _localctx = new Col_testContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_col_test);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); 
			((Col_testContext)_localctx).left = value();
			setState(156);
			((Col_testContext)_localctx).test = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << GT) | (1L << ST))) != 0)) ) {
				((Col_testContext)_localctx).test = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(157); 
			((Col_testContext)_localctx).right = value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assig_exprContext extends ParserRuleContext {
		public Token attribute;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public TerminalNode QUALIFIED_ID() { return getToken(betaParser.QUALIFIED_ID, 0); }
		public Assig_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assig_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterAssig_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitAssig_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitAssig_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assig_exprContext assig_expr() throws RecognitionException {
		Assig_exprContext _localctx = new Assig_exprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_assig_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			((Assig_exprContext)_localctx).attribute = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==QUALIFIED_ID) ) {
				((Assig_exprContext)_localctx).attribute = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(160); 
			match(T__0);
			setState(161); 
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assig_aggrContext extends ParserRuleContext {
		public Token attribute;
		public Token aggr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List_labelsContext list_labels() {
			return getRuleContext(List_labelsContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public TerminalNode QUALIFIED_ID() { return getToken(betaParser.QUALIFIED_ID, 0); }
		public TerminalNode MIN() { return getToken(betaParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(betaParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(betaParser.SUM, 0); }
		public Assig_aggrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assig_aggr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterAssig_aggr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitAssig_aggr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitAssig_aggr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assig_aggrContext assig_aggr() throws RecognitionException {
		Assig_aggrContext _localctx = new Assig_aggrContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_assig_aggr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			((Assig_aggrContext)_localctx).attribute = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==QUALIFIED_ID) ) {
				((Assig_aggrContext)_localctx).attribute = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(164); 
			match(T__0);
			setState(165);
			((Assig_aggrContext)_localctx).aggr = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MIN) | (1L << MAX) | (1L << SUM))) != 0)) ) {
				((Assig_aggrContext)_localctx).aggr = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(166); 
			match(T__3);
			setState(167); 
			expr(0);
			setState(168); 
			match(T__7);
			setState(169); 
			match(T__13);
			setState(170); 
			match(T__9);
			setState(171); 
			list_labels();
			setState(172); 
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Update_exprContext extends ParserRuleContext {
		public Update_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update_expr; }
	 
		public Update_exprContext() { }
		public void copyFrom(Update_exprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Update_aggContext extends Update_exprContext {
		public Token attribute;
		public Token aggr;
		public List_labelsContext list_labels() {
			return getRuleContext(List_labelsContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public TerminalNode MIN() { return getToken(betaParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(betaParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(betaParser.SUM, 0); }
		public Update_aggContext(Update_exprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterUpdate_agg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitUpdate_agg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitUpdate_agg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Update_expContext extends Update_exprContext {
		public Token attribute;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public Update_expContext(Update_exprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterUpdate_exp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitUpdate_exp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitUpdate_exp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Update_exprContext update_expr() throws RecognitionException {
		Update_exprContext _localctx = new Update_exprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_update_expr);
		int _la;
		try {
			setState(186);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new Update_aggContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(174); 
				((Update_aggContext)_localctx).attribute = match(IDENTIFIER);
				setState(175); 
				match(T__0);
				setState(176);
				((Update_aggContext)_localctx).aggr = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MIN) | (1L << MAX) | (1L << SUM))) != 0)) ) {
					((Update_aggContext)_localctx).aggr = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(177); 
				match(T__3);
				setState(178); 
				match(T__9);
				setState(179); 
				list_labels();
				setState(180); 
				match(T__10);
				setState(181); 
				match(T__7);
				}
				break;
			case 2:
				_localctx = new Update_expContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(183); 
				((Update_expContext)_localctx).attribute = match(IDENTIFIER);
				setState(184); 
				match(T__0);
				setState(185); 
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Expr_numberContext extends ExprContext {
		public TerminalNode INT() { return getToken(betaParser.INT, 0); }
		public Expr_numberContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_number(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_number(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_number(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_pmContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_pmContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_pm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_pm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_pm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_mdContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_mdContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_md(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_md(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_md(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_idContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public Expr_idContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_id(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_id(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_parensContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Expr_parensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_parens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_parens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_parens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_tableidContext extends ExprContext {
		public Token table;
		public TerminalNode QUALIFIED_ID() { return getToken(betaParser.QUALIFIED_ID, 0); }
		public Expr_tableidContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_tableid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_tableid(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_tableid(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Exp_aggContext extends ExprContext {
		public Token aggr;
		public List_identifiersContext list_identifiers() {
			return getRuleContext(List_identifiersContext.class,0);
		}
		public TerminalNode MIN() { return getToken(betaParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(betaParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(betaParser.SUM, 0); }
		public Exp_aggContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExp_agg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExp_agg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExp_agg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_castContext extends ExprContext {
		public Token type;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CAST_FLOAT() { return getToken(betaParser.CAST_FLOAT, 0); }
		public TerminalNode CAST_INTEGER() { return getToken(betaParser.CAST_INTEGER, 0); }
		public Expr_castContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterExpr_cast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitExpr_cast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitExpr_cast(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			switch (_input.LA(1)) {
			case INT:
				{
				_localctx = new Expr_numberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(189); 
				match(INT);
				}
				break;
			case IDENTIFIER:
				{
				_localctx = new Expr_idContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(190); 
				match(IDENTIFIER);
				}
				break;
			case QUALIFIED_ID:
				{
				_localctx = new Expr_tableidContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(191); 
				((Expr_tableidContext)_localctx).table = match(QUALIFIED_ID);
				}
				break;
			case MIN:
			case MAX:
			case SUM:
				{
				_localctx = new Exp_aggContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(192);
				((Exp_aggContext)_localctx).aggr = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MIN) | (1L << MAX) | (1L << SUM))) != 0)) ) {
					((Exp_aggContext)_localctx).aggr = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(193); 
				match(T__3);
				setState(194); 
				match(T__9);
				setState(195); 
				list_identifiers();
				setState(196); 
				match(T__10);
				setState(197); 
				match(T__7);
				}
				break;
			case CAST_FLOAT:
			case CAST_INTEGER:
				{
				_localctx = new Expr_castContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(199);
				((Expr_castContext)_localctx).type = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==CAST_FLOAT || _la==CAST_INTEGER) ) {
					((Expr_castContext)_localctx).type = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(200); 
				match(T__3);
				setState(201); 
				expr(0);
				setState(202); 
				match(T__7);
				}
				break;
			case T__3:
				{
				_localctx = new Expr_parensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(204); 
				match(T__3);
				setState(205); 
				expr(0);
				setState(206); 
				match(T__7);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(218);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(216);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_mdContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(210);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(211);
						((Expr_mdContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((Expr_mdContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(212); 
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new Expr_pmContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(213);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(214);
						((Expr_pmContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((Expr_pmContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(215); 
						expr(8);
						}
						break;
					}
					} 
				}
				setState(220);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public Token v;
		public TerminalNode INT() { return getToken(betaParser.INT, 0); }
		public TerminalNode STRING() { return getToken(betaParser.STRING, 0); }
		public TerminalNode IDENTIFIER() { return getToken(betaParser.IDENTIFIER, 0); }
		public TerminalNode QUALIFIED_ID() { return getToken(betaParser.QUALIFIED_ID, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			((ValueContext)_localctx).v = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IDENTIFIER) | (1L << QUALIFIED_ID) | (1L << INT) | (1L << STRING))) != 0)) ) {
				((ValueContext)_localctx).v = (Token)_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_identifiersContext extends ParserRuleContext {
		public List<TerminalNode> QUALIFIED_ID() { return getTokens(betaParser.QUALIFIED_ID); }
		public TerminalNode QUALIFIED_ID(int i) {
			return getToken(betaParser.QUALIFIED_ID, i);
		}
		public List_identifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_identifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).enterList_identifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof betaListener ) ((betaListener)listener).exitList_identifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof betaVisitor ) return ((betaVisitor<? extends T>)visitor).visitList_identifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final List_identifiersContext list_identifiers() throws RecognitionException {
		List_identifiersContext _localctx = new List_identifiersContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_list_identifiers);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(223); 
					match(QUALIFIED_ID);
					setState(224); 
					match(T__4);
					}
					} 
				}
				setState(229);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(230); 
			match(QUALIFIED_ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10: 
			return bool_exp_sempred((Bool_expContext)_localctx, predIndex);
		case 15: 
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean bool_exp_sempred(Bool_expContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: 
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: 
			return precpred(_ctx, 8);
		case 2: 
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3.\u00eb\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\7\2)\n\2\f\2\16\2,\13\2\7\2.\n\2\f\2\16\2\61\13\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\5\5A\n\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\5\6I\n\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b]\n\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\7\13\u0087"+
		"\n\13\f\13\16\13\u008a\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0094"+
		"\n\f\3\f\3\f\3\f\7\f\u0099\n\f\f\f\16\f\u009c\13\f\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00bd"+
		"\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00d3\n\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\7\21\u00db\n\21\f\21\16\21\u00de\13\21\3\22\3\22\3\23\3\23"+
		"\7\23\u00e4\n\23\f\23\16\23\u00e7\13\23\3\23\3\23\3\23\2\4\26 \24\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\f\3\2\25\26\3\2\31\33\3\2\23"+
		"\24\3\2,.\3\2 !\3\2\34\36\3\2\21\22\3\2()\3\2*+\5\2 !##%%\u00ec\2/\3\2"+
		"\2\2\4\64\3\2\2\2\68\3\2\2\2\b@\3\2\2\2\nB\3\2\2\2\fM\3\2\2\2\16V\3\2"+
		"\2\2\20a\3\2\2\2\22\u0082\3\2\2\2\24\u0088\3\2\2\2\26\u0093\3\2\2\2\30"+
		"\u009d\3\2\2\2\32\u00a1\3\2\2\2\34\u00a5\3\2\2\2\36\u00bc\3\2\2\2 \u00d2"+
		"\3\2\2\2\"\u00df\3\2\2\2$\u00e5\3\2\2\2&*\5\4\3\2\')\7\"\2\2(\'\3\2\2"+
		"\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+.\3\2\2\2,*\3\2\2\2-&\3\2\2\2.\61\3\2"+
		"\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\5\6\4\2\63"+
		"\3\3\2\2\2\64\65\7\37\2\2\65\66\7\3\2\2\66\67\5\b\5\2\67\5\3\2\2\289\7"+
		"\4\2\29:\5\b\5\2:\7\3\2\2\2;A\5\n\6\2<A\5\16\b\2=A\5\f\7\2>A\5\20\t\2"+
		"?A\5\22\n\2@;\3\2\2\2@<\3\2\2\2@=\3\2\2\2@>\3\2\2\2@?\3\2\2\2A\t\3\2\2"+
		"\2BC\7\5\2\2CD\7\6\2\2DE\7\37\2\2EF\7\7\2\2FH\7\b\2\2GI\5\26\f\2HG\3\2"+
		"\2\2HI\3\2\2\2IJ\3\2\2\2JK\7\t\2\2KL\7\n\2\2L\13\3\2\2\2MN\7\13\2\2NO"+
		"\7\6\2\2OP\7\37\2\2PQ\7\7\2\2QR\7\f\2\2RS\5\24\13\2ST\7\r\2\2TU\7\n\2"+
		"\2U\r\3\2\2\2VW\7\16\2\2WX\7\6\2\2XY\t\2\2\2YZ\7\7\2\2Z\\\7\b\2\2[]\5"+
		"\26\f\2\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\t\2\2_`\7\n\2\2`\17\3\2\2"+
		"\2ab\7\17\2\2bc\7\6\2\2cd\7\37\2\2de\7\7\2\2ef\7\37\2\2fg\7\7\2\2gh\7"+
		"\37\2\2hi\7\7\2\2ij\7#\2\2jk\7\7\2\2kl\t\3\2\2lm\7\7\2\2mn\7\f\2\2no\5"+
		"\24\13\2op\7\r\2\2pq\7\7\2\2qr\7\b\2\2rs\5\32\16\2st\7\t\2\2tu\7\7\2\2"+
		"uv\7\b\2\2vw\5\32\16\2wx\7\t\2\2xy\7\7\2\2yz\7\b\2\2z{\5\34\17\2{|\7\t"+
		"\2\2|}\7\7\2\2}~\7\b\2\2~\177\5\32\16\2\177\u0080\7\t\2\2\u0080\u0081"+
		"\7\n\2\2\u0081\21\3\2\2\2\u0082\u0083\7\37\2\2\u0083\23\3\2\2\2\u0084"+
		"\u0085\7 \2\2\u0085\u0087\7\7\2\2\u0086\u0084\3\2\2\2\u0087\u008a\3\2"+
		"\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008b\u008c\7 \2\2\u008c\25\3\2\2\2\u008d\u008e\b\f\1\2"+
		"\u008e\u0094\5\30\r\2\u008f\u0090\7\6\2\2\u0090\u0091\5\26\f\2\u0091\u0092"+
		"\7\n\2\2\u0092\u0094\3\2\2\2\u0093\u008d\3\2\2\2\u0093\u008f\3\2\2\2\u0094"+
		"\u009a\3\2\2\2\u0095\u0096\f\5\2\2\u0096\u0097\t\4\2\2\u0097\u0099\5\26"+
		"\f\6\u0098\u0095\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a"+
		"\u009b\3\2\2\2\u009b\27\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u009e\5\"\22"+
		"\2\u009e\u009f\t\5\2\2\u009f\u00a0\5\"\22\2\u00a0\31\3\2\2\2\u00a1\u00a2"+
		"\t\6\2\2\u00a2\u00a3\7\3\2\2\u00a3\u00a4\5 \21\2\u00a4\33\3\2\2\2\u00a5"+
		"\u00a6\t\6\2\2\u00a6\u00a7\7\3\2\2\u00a7\u00a8\t\7\2\2\u00a8\u00a9\7\6"+
		"\2\2\u00a9\u00aa\5 \21\2\u00aa\u00ab\7\n\2\2\u00ab\u00ac\7\20\2\2\u00ac"+
		"\u00ad\7\f\2\2\u00ad\u00ae\5\24\13\2\u00ae\u00af\7\r\2\2\u00af\35\3\2"+
		"\2\2\u00b0\u00b1\7 \2\2\u00b1\u00b2\7\3\2\2\u00b2\u00b3\t\7\2\2\u00b3"+
		"\u00b4\7\6\2\2\u00b4\u00b5\7\f\2\2\u00b5\u00b6\5\24\13\2\u00b6\u00b7\7"+
		"\r\2\2\u00b7\u00b8\7\n\2\2\u00b8\u00bd\3\2\2\2\u00b9\u00ba\7 \2\2\u00ba"+
		"\u00bb\7\3\2\2\u00bb\u00bd\5 \21\2\u00bc\u00b0\3\2\2\2\u00bc\u00b9\3\2"+
		"\2\2\u00bd\37\3\2\2\2\u00be\u00bf\b\21\1\2\u00bf\u00d3\7#\2\2\u00c0\u00d3"+
		"\7 \2\2\u00c1\u00d3\7!\2\2\u00c2\u00c3\t\7\2\2\u00c3\u00c4\7\6\2\2\u00c4"+
		"\u00c5\7\f\2\2\u00c5\u00c6\5$\23\2\u00c6\u00c7\7\r\2\2\u00c7\u00c8\7\n"+
		"\2\2\u00c8\u00d3\3\2\2\2\u00c9\u00ca\t\b\2\2\u00ca\u00cb\7\6\2\2\u00cb"+
		"\u00cc\5 \21\2\u00cc\u00cd\7\n\2\2\u00cd\u00d3\3\2\2\2\u00ce\u00cf\7\6"+
		"\2\2\u00cf\u00d0\5 \21\2\u00d0\u00d1\7\n\2\2\u00d1\u00d3\3\2\2\2\u00d2"+
		"\u00be\3\2\2\2\u00d2\u00c0\3\2\2\2\u00d2\u00c1\3\2\2\2\u00d2\u00c2\3\2"+
		"\2\2\u00d2\u00c9\3\2\2\2\u00d2\u00ce\3\2\2\2\u00d3\u00dc\3\2\2\2\u00d4"+
		"\u00d5\f\n\2\2\u00d5\u00d6\t\t\2\2\u00d6\u00db\5 \21\13\u00d7\u00d8\f"+
		"\t\2\2\u00d8\u00d9\t\n\2\2\u00d9\u00db\5 \21\n\u00da\u00d4\3\2\2\2\u00da"+
		"\u00d7\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2"+
		"\2\2\u00dd!\3\2\2\2\u00de\u00dc\3\2\2\2\u00df\u00e0\t\13\2\2\u00e0#\3"+
		"\2\2\2\u00e1\u00e2\7!\2\2\u00e2\u00e4\7\7\2\2\u00e3\u00e1\3\2\2\2\u00e4"+
		"\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2"+
		"\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00e9\7!\2\2\u00e9%\3\2\2\2\17*/@H\\\u0088"+
		"\u0093\u009a\u00bc\u00d2\u00da\u00dc\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}