package ch.bfh.sokobomb.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ch.bfh.sokobomb.exception.LexerException;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * A lexer for ASCII sokoban notation
 *
 * @author Denis Simonet
 */
public class FieldLexer implements Lexer {

	private String  source; // source string for lexical analysis
	private int     index;  // current index in source
	private int     length; // length of source
	private int     row;   // Current line
	private int     column; // Current column
	private boolean wallFound;

	/**
	 * Initialize the lexer
	 *
	 * Reads the level file and stores it in the class
	 *
	 * @param path
	 * @throws LexerException 
	 */
	@Override
	public void initLexer(String path) throws LexerException {
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

			this.source    = sourceBuilder.toString();

			// Skip first line as it is reserved for meta information
			this.index     = this.source.indexOf("\n");
			this.length    = this.source.length();
			this.row       = 0;
			this.column    = 0;
			this.wallFound = false;
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
	@Override
	public FieldToken nextToken() throws RuntimeException, LexerException {
		FieldToken token = new FieldToken();

		if (this.index >= this.length) {
			token.type = FieldToken.EOF;
			return token;
		}
		else {
			char c = this.source.charAt(this.index);

			switch (c) {
			case '#':
				wallFound = true;
				token.type = FieldToken.WALL;
				break;
			case '@':
				token.type = FieldToken.PLAYER_START;
				break;
			case '.':
				token.type = FieldToken.TARGET;
				break;
			case '$':
				token.type = FieldToken.BOMB_START;
				break;
			case '*':
				token.type = FieldToken.BOMB_TARGET;
				break;
			case '+':
				token.type = FieldToken.PLAYER_TARGET;
				break;
			case '&':
				token.type = FieldToken.EMPTY;
				break;
			case ' ':
				token.type = this.wallFound ? FieldToken.FLOOR : FieldToken.EMPTY;
				break;
			case '\r':
				this.index++;
				return nextToken();
			case '\n':
				// Next line
				this.row++;
				this.column = 0;
				this.index++;
				this.wallFound = false;
				
				return nextToken();
			case 't':
				//Skip level information
				
				return nextToken();
			default:
				throw new LexerException(
					"Illegal character: " + c 
				);
			}
		}

		token.coordinate = new TileCoordinate(
			this.column++,
			this.row
		);

		this.index++;

		return token;
	}
}