
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import java.util.List;
import java.util.LinkedList;
import rs.ac.bg.etf.pp1.test.*;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;

%%

%{
	// Lista leksickih gresaka
	public static List<CompilerError> leksickeGreske = new LinkedList<CompilerError>();
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}	
	
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }
	
"program" 	{return new_symbol(sym.PROGRAM, yytext());}
"break" 	{return new_symbol(sym.BREAK, yytext());}
"class" 	{return new_symbol(sym.CLASS, yytext());}
"enum" 		{return new_symbol(sym.ENUM, yytext());}
"else" 		{return new_symbol(sym.ELSE, yytext());}
"const" 	{return new_symbol(sym.CONST, yytext());}
"if" 		{return new_symbol(sym.IF, yytext());}
"switch" 	{return new_symbol(sym.SWITCH, yytext());}
"do" 		{return new_symbol(sym.DO, yytext());}
"while" 	{return new_symbol(sym.WHILE, yytext());}
"new" 		{return new_symbol(sym.NEW, yytext());}
"print" 	{return new_symbol(sym.PRINT, yytext());}
"read" 		{return new_symbol(sym.READ, yytext());}
"return" 	{return new_symbol(sym.RETURN, yytext());}
"void" 		{return new_symbol(sym.VOID, yytext());}
"extends" 	{return new_symbol(sym.EXTENDS, yytext());}
"continue" 	{return new_symbol(sym.CONTINUE, yytext());}
"case" 		{return new_symbol(sym.CASE, yytext());}

"+" 		{return new_symbol(sym.PLUS, yytext());}
"-" 		{return new_symbol(sym.MINUS, yytext());}
"*" 		{return new_symbol(sym.MUL, yytext());}
"/" 		{return new_symbol(sym.DIV, yytext());}
"%" 		{return new_symbol(sym.MOD, yytext());}
"==" 		{return new_symbol(sym.EQTO, yytext());}
"!=" 		{return new_symbol(sym.NEQTO, yytext());}
">" 		{return new_symbol(sym.GTE, yytext());}
">=" 		{return new_symbol(sym.GTEQ, yytext());}
"<" 		{return new_symbol(sym.LTE, yytext());}
"<=" 		{return new_symbol(sym.LTEQ, yytext());}
"&&" 		{return new_symbol(sym.AND, yytext());}
"||" 		{return new_symbol(sym.OR, yytext());}
"=" 		{return new_symbol(sym.EQUALS, yytext());}
"++" 		{return new_symbol(sym.INC, yytext());}
"--" 		{return new_symbol(sym.DEC, yytext());}
";" 		{return new_symbol(sym.SEMI, yytext());}
"," 		{return new_symbol(sym.COMMA, yytext());}
"." 		{return new_symbol(sym.DOT, yytext());}
"(" 		{return new_symbol(sym.LPAREN, yytext());}
")" 		{return new_symbol(sym.RPAREN, yytext());}
"[" 		{return new_symbol(sym.LSQBRA, yytext());}
"]" 		{return new_symbol(sym.RSQBRA, yytext());}
"{" 		{return new_symbol(sym.LBRACE, yytext());}
"}" 		{return new_symbol(sym.RBRACE, yytext());}
"?" 		{return new_symbol(sym.TERNARY, yytext());}
":" 		{return new_symbol(sym.COLON, yytext());}

"//" 				{yybegin(COMMENT);}
<COMMENT> . 		{yybegin(COMMENT);}
<COMMENT> "\r\n" 	{yybegin(YYINITIAL);}

[0-9]+  							{ return new_symbol(sym.NUMC, new Integer (yytext())); }
"true"								{ return new_symbol(sym.BOOLC, new Boolean(yytext())); }
"false"								{ return new_symbol(sym.BOOLC, new Boolean(yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 		{return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" 					{return new_symbol (sym.CHARC, new Character (yytext().charAt(1)));}


. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+", u koloni " + (yycolumn+1));
	leksickeGreske.add(new CompilerError((yyline+1), "Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+", u koloni " + (yycolumn+1), CompilerErrorType.LEXICAL_ERROR)); }
	
	
	



