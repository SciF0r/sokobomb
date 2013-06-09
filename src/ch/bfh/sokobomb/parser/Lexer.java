package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.exception.LexerException;

public interface Lexer {
	
	public void initLexer(String path) throws LexerException;
	
	public Token nextToken() throws RuntimeException, LexerException;

}
