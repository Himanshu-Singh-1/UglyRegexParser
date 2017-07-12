Project Description:
Aim of the project is to build a recursive descent parser.

Project Specifications:
Ugly Regexp is define inductively as follows:
1. 
A primitive ugly-regexp is chars(x_1, ...,x_n) with n>=1. The chars and comma , occur
literally, whereas the x_1,...,x_n represent any single character.

2.
If U1 and U2 are ugly-regexp's, then so are:
	(U1) representing the same ugly regexp as U1.
	*U1 representing the Kleene closure of ugly-regexp U1.
	U1 + U2 representing the alternation of ugly-regexp's U1 or U2.
	U1.U2 representing the concatenation of ugly-regexp's U1 and U2.
	
The operators precedence are strictly ordered . (lowest), +, *(highest). The . and + binary operators
are left-associative. The * prefix operator is allowed to nest i.e, **U1 is legal. Paranthesis
are used in a ususal way to override the default associativity and precendece.

An ugly-regexp is terminated by a newline. Other liner-whitespaces are ignored. It is assumed that
and ugly-regexp contains only ASCII characters. 

An ugly-regexp U can be translaedt into standard regexp syntax using a translation function T(U) which is
defined as follows:

	if U is of the form chars(x_1,...,x_n), then T(U) is [x_1....x_n].
	If U is of the form (U1), then T(U) is (T(U1)).
	If U is of the form U1+U2, then T(U) is (T(U1)|T(U2)).
	If U is of the form U1.U2, then T(U) is (T(U1)T(U2)).
	
This program parses an ugly-regxp and translates it into standard regexp syntax. 

Files included:

1. Makefile
2. Coords.java: a struct which tracks the source position(filename, line-number, column-number) of a token.
3. Token: a struct which defines a token. Token.Kind enum defines all the different types of tokens
	for this project.
4. Scanner: a crude scanner which delivers tokens of kind Token.Kind while ignoring linear whitespaces.
5. UglyRegexpParser: parses ugly-regexp expressions from input file and translates them into 
	standard regular expressions.
6. Input file.
7. README.

Compiling the program: ant buildfile is provided. 
Running the program: run with  argument as the filepath of input file.
