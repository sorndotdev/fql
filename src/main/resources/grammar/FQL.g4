grammar FQL;

start: expression EOF;

expression: additiveExpression;
additiveExpression: multiplicativeExpression ( (ADD | SUB) multiplicativeExpression)*;
multiplicativeExpression: unaryExpression ( (MUL | DIV) unaryExpression)*;
unaryExpression: (ADD | SUB) unaryExpression | primaryExpression;
primaryExpression: '(' expression ')' | functionCall | literal;

functionCall: functionName '(' arguments? ')';
arguments: expression (',' expression)*;

functionName:
    DATA | PERIOD | RANGE | LAST | LATEST
;

literal: NUMBER | STRING;

DATA: 'DATA';
PERIOD: 'PERIOD';
RANGE: 'RANGE';
LAST: 'LAST';
LATEST: 'LATEST';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';

STRING
  : '"' [A-Za-z0-9_.\-/: *^$#@()<>\u005B\u005D]+ '"'
  ;
NUMBER: [0-9]+ ('.' [0-9]+)?;
WS: [ \t\r\n]+ -> skip;