grammar MiniPascal;

// Lexer rules (tokens)
COMMENT : '{' .*? '}' -> skip ;
WS      : [ \t\r\n]+ -> skip ;

PROGRAM   : 'program';
VAR       : 'var';
CONST     : 'const';
BEGIN     : 'begin';
END       : 'end';
FUNCTION  : 'function';
PROCEDURE : 'procedure';
IF        : 'if';
THEN      : 'then';
ELSE      : 'else';
FOR       : 'for';
TO        : 'to';
DOWNTO    : 'downto';
DO        : 'do';
WHILE     : 'while';
REPEAT    : 'repeat';
UNTIL     : 'until';
READ      : 'read';
READLN    : 'readln';
WRITE     : 'write';
WRITELN   : 'writeln';
ARRAY     : 'array';
OF        : 'of';
NOT       : 'not';
AND       : 'and';
OR        : 'or';
DIV_KW    : 'div';
MOD       : 'mod';
TRUE      : 'true';
FALSE     : 'false';
INTEGER   : 'integer';
REAL      : 'real';
STRING    : 'string';
BOOLEAN   : 'boolean';

ID  : [a-zA-Z_][a-zA-Z0-9_]* ;
NUM : [0-9]+ ;
STRING_LITERAL : '\'' ( ~('\'' ) | '\'\'' )* '\'' ;

PLUS    : '+' ;
MINUS   : '-' ;
MULT    : '*' ;
DIV     : '/' ;
ASSIGN  : ':=' ;
EQ      : '=' ;
NEQ     : '<>' ;
LT      : '<' ;
GT      : '>' ;
LTE     : '<=' ;
GTE     : '>=' ;
LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACK  : '[' ;
RBRACK  : ']' ;
SEMICOLON : ';' ;
COLON   : ':' ;
COMMA   : ',' ;
DOT     : '.' ;

// Parser rules
program : PROGRAM ID SEMICOLON block DOT ;

block : (constDecl | varDecl | procDecl | funcDecl)* compoundStatement ;

constDecl : CONST constAssign (COMMA constAssign)* SEMICOLON ;
constAssign : ID EQ NUM ;

varDecl : VAR varAssignDecl+ ;
varAssignDecl : varAssign (COMMA varAssign)* SEMICOLON ;
varAssign : ID COLON type ;

type : INTEGER
     | REAL
     | STRING
     | BOOLEAN
     | ARRAY LBRACK NUM '..' NUM (COMMA NUM '..' NUM)? RBRACK OF type ;

procDecl : PROCEDURE ID SEMICOLON block SEMICOLON ;

funcDecl : FUNCTION ID LPAREN paramList? RPAREN COLON type SEMICOLON block SEMICOLON ;

paramList : param (COMMA param)* ;
param : ('var')? ID COLON type ;

compoundStatement : BEGIN statementList END ;
statementList : (statement SEMICOLON)* ;

statement
    : assignment
    | ifStatement
    | whileStatement
    | forStatement
    | repeatStatement
    | writeStatement
    | readStatement
    | compoundStatement
    ;

assignment : ID (LBRACK expr (COMMA expr)? RBRACK)? ASSIGN expr ;

ifStatement : IF condition THEN statement (ELSE statement)? ;

whileStatement : WHILE condition DO statement ;

repeatStatement : REPEAT statementList UNTIL condition ;

forStatement : FOR ID ASSIGN expr (TO | DOWNTO) expr DO statement ;

writeStatement : (WRITE | WRITELN) LPAREN STRING_LITERAL (COMMA ID)? RPAREN ;

readStatement : (READ | READLN) LPAREN ID RPAREN ;

condition : expr relOp expr ;

relOp : EQ | NEQ | LT | GT | LTE | GTE ;

expr : simpleExpr ((PLUS | MINUS) simpleExpr)* ;

simpleExpr : term ((MULT | DIV | DIV_KW | MOD | AND) term)* ;

term : factor ;

factor
    : NUM
    | TRUE
    | FALSE
    | STRING_LITERAL
    | ID (LBRACK expr (COMMA expr)? RBRACK)?
    | LPAREN expr RPAREN
    | NOT factor
    ;
