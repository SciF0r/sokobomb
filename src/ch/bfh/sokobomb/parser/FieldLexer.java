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
	private boolean levelInformation; // skip information section

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
			System.out.println("1:=>" + source);
			this.index     = 0;
			this.length    = this.source.length();
			this.row       = 0;
			this.column    = 0;
			this.wallFound = false;
			this.levelInformation = false;
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
	public Token nextToken() throws RuntimeException, LexerException {
		Token token = new Token();

		if (this.index >= this.length) {
			token.type = Token.EOF;
			return token;
		}
		else {
			char c = this.source.charAt(this.index);

			switch (c) {
			case '#':
				wallFound = true;
				token.type = Token.WALL;
				break;
			case '@':
				token.type = Token.PLAYER_START;
				break;
			case '.':
				token.type = Token.TARGET;
				break;
			case '$':
				token.type = Token.BOMB_START;
				break;
			case '*':
				token.type = Token.BOMB_TARGET;
				break;
			case '+':
				token.type = Token.PLAYER_TARGET;
				break;
			case '&':
				token.type = Token.EMPTY;
				break;
			case ' ':
				token.type = this.wallFound ? Token.FLOOR : Token.EMPTY;
				break;
			case '\r':
				this.index++;
				return nextToken();
			case '\n':
				if(levelInformation) 
					this.levelInformation = false;
				// Next line
				this.row++;
				this.column = 0;
				this.index++;
				this.wallFound = false;
				
				return nextToken();
			case 't':
				this.levelInformation =true;
				this.index++;
				return nextToken();
			default:
				if(levelInformation){
					System.out.println(c);
					this.index++;
					this.nextToken();
				}
				else {
				throw new LexerException(
						"Illegal character: " + c 
						);
				}
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