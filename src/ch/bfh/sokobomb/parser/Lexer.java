package ch.bfh.sokobomb.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ch.bfh.sokobomb.exception.LexerException;

/**
 * A lexer for ASCII sokoban notation
 *
 * @author Denis Simonet
 */
public class Lexer {

	private String source; // source string for lexical analysis
	private int    index;  // current index in source
	private int    length; // length of source
	private int    row;   // Current line
	private int    column; // Current column

	/**
	 * Initialize the lexer
	 *
	 * Reads the level file and stores it in the class
	 *
	 * @param path
	 */
	public void initLexer(String path) {
		File file = new File(path);
		if (!file.exists()) {
			throw new LexerException(
				"File not found"
			);
		}

		if (!(file.isFile() && file.canRead())) {
			throw new LexerException(
				"Cannot read from file"
			);
		}

		try {
			FileInputStream fis         = new FileInputStream(file);
			StringBuilder sourceBuilder = new StringBuilder();

			while (fis.available() > 0) {
				sourceBuilder.append((char)fis.read());
			}

			fis.close();

			this.source = sourceBuilder.toString();
			this.index  = 0;
			this.length = this.source.length();
			this.row   = 0;
			this.column = 0;
		} catch (IOException e) {
			throw new LexerException(
				e.getMessage()
			);
		}
	}

	/**
	 * Returns the next token
	 *
	 * @return A token
	 * @throws LexerException
	 */
	public Token nextToken() throws RuntimeException {
		Token token = new Token();
		
		if (this.index >= this.length) {
			token.type = Token.EOF;
			return token;
		}
		else {
			char c = this.source.charAt(this.index);

			switch (c) {
				case '#':
					token.type = Token.WALL;
					token.z = 0;
					break;
				case '@':
					token.type = Token.PLAYER_START;
					token.z = 2;
					break;
				case '.':
					token.type = Token.TARGET;
					token.z = 1;
					break;
				case '$':
					token.type = Token.BOMB_START;
					token.z = 1;
					break;
				case '*':
					token.type = Token.BOMB_TARGET;
					token.z = 1;
					break;
				case '+':
					token.type = Token.PLAYER_TARGET;
					token.z = 2;
					break;
				case ' ':
					token.type = Token.FLOOR;
					token.z = 0;
					break;
				case '\n':
					// Next line
					this.row++;
					this.column = 0;
					this.index++;
					return nextToken();
				default:
					throw new LexerException(
						"Illegal character" 
					);
			}
		}

		token.x = this.column++;
		token.y = this.row;

		this.index++;

		return token;
	}
}