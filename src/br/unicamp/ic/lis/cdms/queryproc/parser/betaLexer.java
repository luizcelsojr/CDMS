// Generated from /Users/luizcelso/Dropbox/workspace/CDMS2/src/br/unicamp/ic/lis/cdms/queryproc/parser/beta.g4 by ANTLR 4.5
package br.unicamp.ic.lis.cdms.queryproc.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class betaLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "CAST_FLOAT", "CAST_INTEGER", 
		"AND", "OR", "VTABLE", "ETABLE", "NEW", "CURRENT", "IN", "OUT", "BOTH", 
		"MIN", "MAX", "SUM", "TABLE_ID", "IDENTIFIER", "QUALIFIED_ID", "NEWLINE", 
		"INT", "FLOAT", "DIGIT", "STRING", "SINGLE_LINE_COMMENT", "WS", "MUL", 
		"DIV", "ADD", "SUB", "EQ", "GT", "ST"
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


	public betaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "beta.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2.\u0123\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\6\36\u00d1"+
		"\n\36\r\36\16\36\u00d2\3\37\6\37\u00d6\n\37\r\37\16\37\u00d7\3 \3 \3 "+
		"\3 \5 \u00de\n \3 \3 \3 \3!\5!\u00e4\n!\3!\3!\3\"\6\"\u00e9\n\"\r\"\16"+
		"\"\u00ea\3#\6#\u00ee\n#\r#\16#\u00ef\3#\3#\7#\u00f4\n#\f#\16#\u00f7\13"+
		"#\3$\3$\3%\3%\7%\u00fd\n%\f%\16%\u0100\13%\3%\3%\3&\3&\3&\3&\7&\u0108"+
		"\n&\f&\16&\u010b\13&\3&\3&\3\'\6\'\u0110\n\'\r\'\16\'\u0111\3\'\3\'\3"+
		"(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3\u00fe\2/\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G\2I%K&"+
		"M\'O(Q)S*U+W,Y-[.\3\2\7\3\2C\\\5\2C\\aac|\3\2\62;\4\2\f\f\17\17\5\2\13"+
		"\f\17\17\"\"\u012d\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3"+
		"\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2"+
		"\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E"+
		"\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2"+
		"\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\3]\3\2\2\2\5`\3\2\2\2"+
		"\7g\3\2\2\2\tn\3\2\2\2\13p\3\2\2\2\rr\3\2\2\2\17t\3\2\2\2\21v\3\2\2\2"+
		"\23x\3\2\2\2\25\u0080\3\2\2\2\27\u0082\3\2\2\2\31\u0084\3\2\2\2\33\u0089"+
		"\3\2\2\2\35\u008e\3\2\2\2\37\u0091\3\2\2\2!\u0097\3\2\2\2#\u009f\3\2\2"+
		"\2%\u00a3\3\2\2\2\'\u00a6\3\2\2\2)\u00a8\3\2\2\2+\u00aa\3\2\2\2-\u00af"+
		"\3\2\2\2/\u00b7\3\2\2\2\61\u00ba\3\2\2\2\63\u00be\3\2\2\2\65\u00c3\3\2"+
		"\2\2\67\u00c7\3\2\2\29\u00cb\3\2\2\2;\u00d0\3\2\2\2=\u00d5\3\2\2\2?\u00dd"+
		"\3\2\2\2A\u00e3\3\2\2\2C\u00e8\3\2\2\2E\u00ed\3\2\2\2G\u00f8\3\2\2\2I"+
		"\u00fa\3\2\2\2K\u0103\3\2\2\2M\u010f\3\2\2\2O\u0115\3\2\2\2Q\u0117\3\2"+
		"\2\2S\u0119\3\2\2\2U\u011b\3\2\2\2W\u011d\3\2\2\2Y\u011f\3\2\2\2[\u0121"+
		"\3\2\2\2]^\7<\2\2^_\7?\2\2_\4\3\2\2\2`a\7T\2\2ab\7G\2\2bc\7V\2\2cd\7W"+
		"\2\2de\7T\2\2ef\7P\2\2f\6\3\2\2\2gh\7U\2\2hi\7G\2\2ij\7N\2\2jk\7G\2\2"+
		"kl\7E\2\2lm\7V\2\2m\b\3\2\2\2no\7*\2\2o\n\3\2\2\2pq\7.\2\2q\f\3\2\2\2"+
		"rs\7}\2\2s\16\3\2\2\2tu\7\177\2\2u\20\3\2\2\2vw\7+\2\2w\22\3\2\2\2xy\7"+
		"R\2\2yz\7T\2\2z{\7Q\2\2{|\7L\2\2|}\7G\2\2}~\7E\2\2~\177\7V\2\2\177\24"+
		"\3\2\2\2\u0080\u0081\7]\2\2\u0081\26\3\2\2\2\u0082\u0083\7_\2\2\u0083"+
		"\30\3\2\2\2\u0084\u0085\7U\2\2\u0085\u0086\7E\2\2\u0086\u0087\7C\2\2\u0087"+
		"\u0088\7P\2\2\u0088\32\3\2\2\2\u0089\u008a\7D\2\2\u008a\u008b\7G\2\2\u008b"+
		"\u008c\7V\2\2\u008c\u008d\7C\2\2\u008d\34\3\2\2\2\u008e\u008f\7D\2\2\u008f"+
		"\u0090\7[\2\2\u0090\36\3\2\2\2\u0091\u0092\7H\2\2\u0092\u0093\7N\2\2\u0093"+
		"\u0094\7Q\2\2\u0094\u0095\7C\2\2\u0095\u0096\7V\2\2\u0096 \3\2\2\2\u0097"+
		"\u0098\7K\2\2\u0098\u0099\7P\2\2\u0099\u009a\7V\2\2\u009a\u009b\7G\2\2"+
		"\u009b\u009c\7I\2\2\u009c\u009d\7G\2\2\u009d\u009e\7T\2\2\u009e\"\3\2"+
		"\2\2\u009f\u00a0\7C\2\2\u00a0\u00a1\7P\2\2\u00a1\u00a2\7F\2\2\u00a2$\3"+
		"\2\2\2\u00a3\u00a4\7Q\2\2\u00a4\u00a5\7T\2\2\u00a5&\3\2\2\2\u00a6\u00a7"+
		"\7X\2\2\u00a7(\3\2\2\2\u00a8\u00a9\7G\2\2\u00a9*\3\2\2\2\u00aa\u00ab\7"+
		"p\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\7y\2\2\u00ad\u00ae\7X\2\2\u00ae,"+
		"\3\2\2\2\u00af\u00b0\7e\2\2\u00b0\u00b1\7w\2\2\u00b1\u00b2\7t\2\2\u00b2"+
		"\u00b3\7t\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7v\2\2"+
		"\u00b6.\3\2\2\2\u00b7\u00b8\7K\2\2\u00b8\u00b9\7P\2\2\u00b9\60\3\2\2\2"+
		"\u00ba\u00bb\7Q\2\2\u00bb\u00bc\7W\2\2\u00bc\u00bd\7V\2\2\u00bd\62\3\2"+
		"\2\2\u00be\u00bf\7D\2\2\u00bf\u00c0\7Q\2\2\u00c0\u00c1\7V\2\2\u00c1\u00c2"+
		"\7J\2\2\u00c2\64\3\2\2\2\u00c3\u00c4\7O\2\2\u00c4\u00c5\7K\2\2\u00c5\u00c6"+
		"\7P\2\2\u00c6\66\3\2\2\2\u00c7\u00c8\7O\2\2\u00c8\u00c9\7C\2\2\u00c9\u00ca"+
		"\7Z\2\2\u00ca8\3\2\2\2\u00cb\u00cc\7U\2\2\u00cc\u00cd\7W\2\2\u00cd\u00ce"+
		"\7O\2\2\u00ce:\3\2\2\2\u00cf\u00d1\t\2\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d2"+
		"\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3<\3\2\2\2\u00d4"+
		"\u00d6\t\3\2\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d5\3\2"+
		"\2\2\u00d7\u00d8\3\2\2\2\u00d8>\3\2\2\2\u00d9\u00de\5\'\24\2\u00da\u00de"+
		"\5)\25\2\u00db\u00de\5+\26\2\u00dc\u00de\5-\27\2\u00dd\u00d9\3\2\2\2\u00dd"+
		"\u00da\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00dc\3\2\2\2\u00de\u00df\3\2"+
		"\2\2\u00df\u00e0\7\60\2\2\u00e0\u00e1\5=\37\2\u00e1@\3\2\2\2\u00e2\u00e4"+
		"\7\17\2\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2"+
		"\u00e5\u00e6\7\f\2\2\u00e6B\3\2\2\2\u00e7\u00e9\t\4\2\2\u00e8\u00e7\3"+
		"\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"D\3\2\2\2\u00ec\u00ee\5G$\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef"+
		"\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f5\7\60"+
		"\2\2\u00f2\u00f4\5G$\2\u00f3\u00f2\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3"+
		"\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6F\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8"+
		"\u00f9\t\4\2\2\u00f9H\3\2\2\2\u00fa\u00fe\7)\2\2\u00fb\u00fd\13\2\2\2"+
		"\u00fc\u00fb\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe\u00ff\3\2\2\2\u00fe\u00fc"+
		"\3\2\2\2\u00ff\u0101\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102\7)\2\2\u0102"+
		"J\3\2\2\2\u0103\u0104\7/\2\2\u0104\u0105\7/\2\2\u0105\u0109\3\2\2\2\u0106"+
		"\u0108\n\5\2\2\u0107\u0106\3\2\2\2\u0108\u010b\3\2\2\2\u0109\u0107\3\2"+
		"\2\2\u0109\u010a\3\2\2\2\u010a\u010c\3\2\2\2\u010b\u0109\3\2\2\2\u010c"+
		"\u010d\b&\2\2\u010dL\3\2\2\2\u010e\u0110\t\6\2\2\u010f\u010e\3\2\2\2\u0110"+
		"\u0111\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113\3\2"+
		"\2\2\u0113\u0114\b\'\2\2\u0114N\3\2\2\2\u0115\u0116\7,\2\2\u0116P\3\2"+
		"\2\2\u0117\u0118\7\61\2\2\u0118R\3\2\2\2\u0119\u011a\7-\2\2\u011aT\3\2"+
		"\2\2\u011b\u011c\7/\2\2\u011cV\3\2\2\2\u011d\u011e\7?\2\2\u011eX\3\2\2"+
		"\2\u011f\u0120\7@\2\2\u0120Z\3\2\2\2\u0121\u0122\7>\2\2\u0122\\\3\2\2"+
		"\2\r\2\u00d2\u00d7\u00dd\u00e3\u00ea\u00ef\u00f5\u00fe\u0109\u0111\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}