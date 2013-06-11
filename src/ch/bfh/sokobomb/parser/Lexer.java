package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.exception.LexerException;

/**
 * A lexer interface
 *
 * @author Christoph Bruderer
 */
public interface Lexer {

	/**
	 * Initialize the lexer
	 *
	 * @param path The path to the file to be read
	 * @throws LexerException
	 */
	public void initLexer(String path) throws LexerException;

	/**
	 * Get the next found token
	 *
	 * @return The token
	 * @throws RuntimeException
	 * @throws LexerException
	 */
	public Token nextToken() throws RuntimeException, LexerException;
}