package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.model.Field;

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
		Token token;

		this.lexer.initLexer(path);

		int width  = 0;
		int height = 0;
		while ((token = this.lexer.nextToken()).type != Token.EOF) {
			if (token.type == Token.EMPTY) {
				continue;
			}

			field.addItemByToken(token);
			width = Math.max(width, token.coordinate.getX() + 1);
			height = Math.max(height, token.coordinate.getY() + 1);
		}

		field.buildCache(width, height);
	}
}