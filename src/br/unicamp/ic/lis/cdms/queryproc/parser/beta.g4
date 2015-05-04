grammar beta;

query:	(assign_expr NEWLINE*)* return_expr;
assign_expr: TABLE_ID ':=' operation; //attribution expression
return_expr: 'RETURN' operation;
operation: select | sscan | project | beta | table_ref;
select: 'SELECT' '(' TABLE_ID ',' '{' bool_exp? '}' ')';

project: 'PROJECT' '(' TABLE_ID ',' '[' list_labels ']' ')';

sscan: 'SCAN' '(' table=(VTABLE|ETABLE) ',' '{' bool_exp? '}' ')';

beta: 'BETA' '(' r=TABLE_ID ',' v=TABLE_ID ',' e=TABLE_ID ',' n=INT ',' dir=(IN|OUT|BOTH) ',' '[' follow=list_labels ']' ',' '{' set=assig_expr '}' ',' '{' map=assig_expr '}' ',' '{' reduce=assig_aggr '}' ',' '{' update=assig_expr '}' ')';

table_ref: TABLE_ID;

list_labels: (IDENTIFIER ',')* IDENTIFIER;

bool_exp:  bool_exp logical=(AND|OR) bool_exp     # AndOr
              |	col_test                          # ColTest
              |	'(' bool_exp ')'                  # Parens
              ;

col_test: left=value test=(EQ|GT|ST) right=value;

assig_expr: attribute=(IDENTIFIER|QUALIFIED_ID) ':=' expr;

assig_aggr: attribute=(IDENTIFIER|QUALIFIED_ID) ':=' aggr=(MIN|MAX|SUM)'(' expr ')' 'BY' '[' list_labels ']';

update_expr:  attribute=IDENTIFIER ':=' aggr=(MIN|MAX|SUM)'(' '[' list_labels ']' ')'     # update_agg
           |  attribute=IDENTIFIER ':=' expr                                              # update_exp
                 ;

expr:	expr op=('*'|'/') expr                       # expr_md
    |	expr op=('+'|'-') expr                       # expr_pm
    |	INT                                          # expr_number
    |   IDENTIFIER                                   # expr_id
    |   table=QUALIFIED_ID           # expr_tableid
    |   aggr=(MIN|MAX|SUM)'(' '[' list_identifiers ']' ')'       # exp_agg
    |   type=(CAST_FLOAT|CAST_INTEGER) '(' expr ')'  # expr_cast
    |	'(' expr ')'                                 # expr_parens
    ;

value: v=(INT | STRING | IDENTIFIER | QUALIFIED_ID);

list_identifiers: (QUALIFIED_ID ',')* QUALIFIED_ID;

CAST_FLOAT: 'FLOAT';
CAST_INTEGER: 'INTEGER';
AND : 'AND';
OR  : 'OR';
VTABLE: 'V';
ETABLE: 'E';
NEW: 'newV';
CURRENT: 'current';
IN : 'IN';
OUT  : 'OUT';
BOTH  : 'BOTH';
MIN   : 'MIN';
MAX   :  'MAX';
SUM   :  'SUM';
TABLE_ID: [A-Z]+;
IDENTIFIER: [a-zA-Z_]+;
QUALIFIED_ID: (VTABLE|ETABLE|NEW|CURRENT)'.'IDENTIFIER;
NEWLINE:'\r'? '\n';
INT     : [0-9]+ ;
FLOAT: DIGIT+ '.' DIGIT* ;
fragment
DIGIT : [0-9] ;
STRING : '\'' .*? '\'' ;
SINGLE_LINE_COMMENT : '--' ~[\r\n]* -> skip;
WS    : [ \t\r\n]+ -> skip ;
MUL : '*' ; // assigns token name to '*' used above in grammar
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
EQ  : '=' ;
GT  : '>' ;
ST  : '<' ;


