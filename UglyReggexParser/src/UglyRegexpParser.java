
//package edu.binghamton.cs571;

public class UglyRegexpParser {

  Token _lookahead;
  Scanner _scanner;

  UglyRegexpParser(Scanner scanner) {
    _scanner = scanner;
    _lookahead = _scanner.nextToken();
  }


  /** parse a sequence of lines containing ugly-regexp's; for each
   *  ugly regexp print out the corresponding standard regexp.
   *  If there is an error, print diagnostic and continue with
   *  next line.
   */
  public void parse() {
    while (_lookahead.kind != Token.Kind.EOF) {
      try {
        String out = uglyRegexp();
        if (check(Token.Kind.NL)) System.out.println(out);
        match(Token.Kind.NL);
      }
      catch (ParseException e) {
        System.err.println(e.getMessage());
        while (_lookahead.kind != Token.Kind.NL) {
          _lookahead = _scanner.nextToken();
        }
        _lookahead = _scanner.nextToken();
      }
    }
  }

  /** Return standard syntax regexp corresponding to ugly-regexp
   *  read from _scanner.
   */
  //IMPLEMENT THIS FUNCTION and any necessary functions it may call.
  private String uglyRegexp() {
	  String valuesofar ="";
	  valuesofar = expr(valuesofar);
    return(valuesofar); //placeholder for compile
  }
  
  private String start(String valuesofar)
  {
	  if(_lookahead.kind==Token.Kind.CHARS)
	  {
		  match(Token.Kind.CHARS);
		  if(_lookahead.lexeme.equals("("))
		  {
			  valuesofar=  valuesofar + "[";
			  match(Token.Kind.CHAR);
			  if(_lookahead.lexeme.equals(","))          //Handling special case for chars(,)
			  {
				  match(Token.Kind.CHAR);
				  valuesofar=valuesofar+"\\,";
			  }
			  valuesofar = valuesofar + basic();
		  }
		  else
			  System.out.println("Syntax Error");
	  }
	  return(valuesofar);
  }
  
  private String basic()
  {
	  String basicvalue="";
	  if(_lookahead.lexeme.equals(","))
			  {
		  match(_lookahead.kind.CHAR);
		  
		  if(_lookahead.lexeme.equals(")"))    
		  {
			  match(_lookahead.kind.CHAR);
			  if(_lookahead.lexeme.equals("\n"))
			  {
				  System.out.println("Syntax Error");
			  return(basicvalue);
			  }
			  else 
				  basicvalue=basicvalue+basic();
		  }
		  basicvalue = basicvalue + basic();
				  }
	  else if(_lookahead.lexeme.equals(")"))
	  {
		  basicvalue = basicvalue+"]";
		  match(_lookahead.kind.CHAR);
		  return(basicvalue);
	  }
	  else 
	  {
		  basicvalue = basicvalue + _lookahead.lexeme;
		  match(_lookahead.kind.CHAR);
		  basicvalue = basicvalue + basic();
	  }
	  return(basicvalue);
}
  
  private String expr(String valuesofar)
  {
	  valuesofar=valuesofar+term(valuesofar);
	  return(exprRest(valuesofar));
  }
  private String term(String valuesofar)
  {
	  valuesofar=factor(valuesofar);
	  //valuesofar = valuesofar+"(";
	  if(_lookahead.lexeme.equals("+"))                       //Changes done here for extra brackets problem
	  valuesofar="("+termrest(valuesofar)+")";
	  else
		  valuesofar=termrest(valuesofar);
	  return(valuesofar);
  }
  
  /*Factor*/
  private String factor(String valuesofar)
  {
	  if(_lookahead.lexeme.equals("*"))
	  {
		  match(Token.Kind.CHAR);
		  //valuesofar =valuesofar+"(";
		 // //valuesofar=valuesofar+factor(valuesofar);
		  //valuesofar=factor(valuesofar);
		  //valuesofar=valuesofar+")*";
		  valuesofar=factor(valuesofar)+"*";
		  
	  }
	  else if(_lookahead.lexeme.equals("("))
	  {
		  String abc="";
		  match(Token.Kind.CHAR);
		  //valuesofar=expr(valuesofar);           //TODO: Resolve Problem
		  valuesofar= valuesofar +"("+expr(abc);
		  if(_lookahead.lexeme.equals(")"))
		  {
			  match(Token.Kind.CHAR);
		  valuesofar=valuesofar+")";
		  }
	  }
	  else
		  valuesofar=start(valuesofar);
	  

	  return(valuesofar);
  }
  
  //termRest
  private String termrest(String valuesofar)
  {
	  if(_lookahead.lexeme.equals("+"))
	  {
		  match(Token.Kind.CHAR);
		  valuesofar= valuesofar+"|";
		  valuesofar = factor(valuesofar);
		  return(valuesofar);
	  }
	  valuesofar=factor(valuesofar);
	  return(valuesofar);
  }
  
  //exprRest
  private String exprRest(String valuesofar)
  {
	  if(_lookahead.lexeme.equals("."))
	  {
		  valuesofar = valuesofar+"(";
		  match(Token.Kind.CHAR);
		  valuesofar=term(valuesofar);
		  //valuesofar=valuesofar+")";
	  valuesofar = exprRest(valuesofar)+")";
	  }
	  else if(_lookahead.lexeme.equals("*"))
	  {
		  //valuesofar=term(valuesofar);
		  //valuesofar = term(valuesofar);
		  //valuesofar=factor(valuesofar);
	  }
	  return(valuesofar);
  }
  //Utility functions which may be useful for parsing or translation

  /** Return s with first char escaped using a '\' if it is
  * non-alphanumeric.
   */
  private static String quote(String s) {
    return (Character.isLetterOrDigit(s.charAt(0))) ? s : "\\" + s;
  }

  /** Return true iff _lookahead.kind is equal to kind. */
  private boolean check(Token.Kind kind) {
    return check(kind, null);
  }

  /** Return true iff lookahead kind and lexeme are equal to
   *  corresponding args.  Note that if lexeme is null, then it is not
   *  used in the match.
   */
  private boolean check(Token.Kind kind, String lexeme) {
    return (_lookahead.kind == kind &&
            (lexeme == null || _lookahead.lexeme.equals(lexeme)));
  }

  /** If lookahead kind is equal to kind, then set lookahead to next
   *  token; else throw a ParseException.
   */
  private void match(Token.Kind kind) {
	    match(kind, null);
	  }

	  /** If lookahead kind and lexeme are not equal to corresponding
	   *  args, then set lookahead to next token; else throw a
	   *  ParseException.  Note that if lexeme is null, then it is
	   *  not used in the match.
	   */
	  private void match(Token.Kind kind, String lexeme) {
	    if (check(kind, lexeme)) {
	      _lookahead = _scanner.nextToken();
	    }
	    else {
	      String expected = (lexeme == null) ? kind.toString() : lexeme;
	      String message = String.format("%s: syntax error at '%s', expecting '%s'",
	                                     _lookahead.coords, _lookahead.lexeme,
	                                     expected);
	      throw new ParseException(message);
	    }
	  }

	  private static class ParseException extends RuntimeException {
		  ParseException(String message) {
		      super(message);
		    }
		  }


		  /** main program: parses and translates ugly-regexp's contained in
		   *  the file specified by it's single command-line argument.
		   */
		  public static void main(String[] args) {
		    if (args.length != 1) {
		      System.err.format("usage: java %s FILENAME\n",
		                        UglyRegexpParser.class.getName());
		      System.exit(1);
		    }
		    Scanner scanner =
		      ("-".equals(args[0])) ? new Scanner() : new Scanner(args[0]);
		    (new UglyRegexpParser(scanner)).parse();
		  }


		}
