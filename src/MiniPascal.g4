// MiniPascal.g4

grammar MiniPascal;

// Reglas iniciales
program     : PROGRAM ID SEMICOLON block DOT ;
block       : varDeclPart subprogramDeclPart compoundStatement ;

// Declaraciones
varDeclPart : (VAR varDecl+)? ;
varDecl     : ID (COMMA ID)* COLON (baseType | arrayType) SEMICOLON ;
baseType    : INTEGER | CHAR | BOOLEAN | STRING ;
arrayType   : ARRAY LBRACK range (COMMA range)? RBRACK OF baseType ;
range       : NUMBER DOTDOT NUMBER ;

// Subprogramas
subprogramDeclPart : (procedureDecl | functionDecl)* ;
procedureDecl      : PROCEDURE ID formalParams? SEMICOLON block SEMICOLON ;
functionDecl       : FUNCTION ID formalParams? COLON baseType SEMICOLON block SEMICOLON ;
formalParams       : LPAREN param (SEMICOLON param)* RPAREN ;
param              : (VAR? ID (COMMA ID)* COLON baseType) ;

// Sentencias
compoundStatement : BEGIN statementList END ;
statementList     : (statement SEMICOLON)* ;
statement         : assignment
                 | ifStatement
                 | whileStatement
                 | forStatement
                 | repeatStatement
                 | readStatement
                 | writeStatement
                 | functionCall
                 | compoundStatement
                 ;
assignment        : variable ASSIGN expr ;
ifStatement       : IF expr THEN statement (ELSE statement)? ;
whileStatement    : WHILE expr DO statement ;
forStatement      : FOR ID ASSIGN expr (TO | DOWNTO) expr DO statement ;
repeatStatement   : REPEAT statementList UNTIL expr ;
readStatement     : (READ | READLN) LPAREN variable RPAREN ;
writeStatement    : (WRITE | WRITELN) LPAREN STRING_LITERAL (COMMA expr)? RPAREN ;
functionCall      : ID LPAREN (expr (COMMA expr)*)? RPAREN ;

// Expresiones
expr        : simpleExpr ((EQUAL | NEQUAL | LT | LTE | GT | GTE) simpleExpr)? ;
simpleExpr  : term ((ADD | SUB | OR) term)* ;
term        : factor ((MUL | DIV | MOD | AND) factor)* ;
factor      : variable
            | NUMBER
            | CHAR_LITERAL
            | STRING_LITERAL
            | TRUE
            | FALSE
            | LPAREN expr RPAREN
            | NOT factor
            ;

variable    : ID (LBRACK expr (COMMA expr)? RBRACK)? ;

// Palabras reservadas y operadores
PROGRAM     : 'program' ;
VAR         : 'var' ;
INTEGER     : 'integer' ;
CHAR        : 'char' ;
BOOLEAN     : 'boolean' ;
STRING      : 'string' ;
ARRAY       : 'array' ;
OF          : 'of' ;
BEGIN       : 'begin' ;
END         : 'end' ;
IF          : 'if' ;
THEN        : 'then' ;
ELSE        : 'else' ;
WHILE       : 'while' ;
DO          : 'do' ;
FOR         : 'for' ;
TO          : 'to' ;
DOWNTO      : 'downto' ;
REPEAT      : 'repeat' ;
UNTIL       : 'until' ;
FUNCTION    : 'function' ;
PROCEDURE   : 'procedure' ;
READ        : 'read' ;
READLN      : 'readln' ;
WRITE       : 'write' ;
WRITELN     : 'writeln' ;
TRUE        : 'true' ;
FALSE       : 'false' ;
NOT         : 'not' ;
AND         : 'and' ;
OR          : 'or' ;
MOD         : 'mod' ;

ASSIGN      : ':=' ;
ADD         : '+' ;
SUB         : '-' ;
MUL         : '*' ;
DIV         : '/' ;
EQUAL       : '=' ;
NEQUAL      : '<>' ;
LT          : '<' ;
LTE         : '<=' ;
GT          : '>' ;
GTE         : '>=' ;
COMMA       : ',' ;
SEMICOLON   : ';' ;
COLON       : ':' ;
DOT         : '.' ;
DOTDOT      : '..' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
LBRACK      : '[' ;
RBRACK      : ']' ;

// Literales y tokens
NUMBER          : [0-9]+ ;
CHAR_LITERAL    : '\'' . '\'' ;
STRING_LITERAL  : '\'' (~('\'' | '\r' | '\n'))* '\'' ;
ID              : [a-zA-Z_] [a-zA-Z0-9_]* ;

// Comentarios y espacios
COMMENT : '{' .*? '}' -> skip ;
WS      : [ \t\r\n]+ -> skip ;