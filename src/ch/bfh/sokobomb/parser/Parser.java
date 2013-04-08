package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.exception.LexerException;
import ch.bfh.sokobomb.field.Field;

/**
 * Parses the tokens from the lexer
 *
 * @author Denis Simonet
 */
public class Parser {

	private Lexer lexer;

	/**
	 * Creates a new lexer object
	 */
	public Parser() {
		this.lexer = new Lexer();
	}

	/**
	 * Parse a file
	 *
	 * @param path
	 */
	public void parse(String path, Field field) {
		try {
			Token token;

			this.lexer.initLexer(path);
	
	
			while ((token = this.lexer.nextToken()).type != Token.EOF) {
				if (token.type == Token.EMPTY) {
					continue;
				}
	
				field.addTileByToken(token);
			}
		}
		catch (LexerException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}