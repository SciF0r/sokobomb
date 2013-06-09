package ch.bfh.sokobomb.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ch.bfh.sokobomb.exception.LexerException;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

public class LevelInformationLexer implements Lexer {

	private String  source; // source string for lexical analysis
	private int     index;  // current index in source
	private int     length; // length of source
	private int		time;
	private boolean digitFound;
	private boolean endOfLine;
	
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
			this.index     = 0;
			this.length    = this.source.length();
			this.digitFound = false;
			this.time		= 0;
			this.endOfLine  = false;
		} catch (IOException e) {
			throw new LexerException(
				e.getMessage()
			);
		}
	}

	@Override
	public Token nextToken() throws RuntimeException, LexerException {
Token token = new Token();
		
		if (this.index >= this.length || this.endOfLine) {
			token.type = Token.EOF;
			return token;
		}
		else {
			char c = this.source.charAt(this.index);

			switch (c) {
				case '1':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 1;
					
					token.type = Token.DIGIT;
					break;
				case '2':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 2;
					
					token.type = Token.DIGIT;
					break;
				case '3':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 3;
					
					token.type = Token.DIGIT;
					break;
				case '4':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 4;
					
					token.type = Token.DIGIT;
					break;
				case '5':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 5;
					
					token.type = Token.DIGIT;
					break;
				case '6':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 6;
				
					token.type = Token.DIGIT;
					break;
				case '7':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 7;
					
					token.type = Token.DIGIT;
					break;
				case '8':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 8;
						
					token.type = Token.DIGIT;
					break;
				case '9':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 9;
						
				case '0':
					if (!digitFound)
						digitFound = true;
					
						time = time * 10 + 0;
						
					token.type = Token.DIGIT;
					break;
				case ' ':
					this.index++;
					return nextToken();
				case '\n':
					// End of level informations
					this.endOfLine = true;
					this.index++;
					return nextToken();
				case '\r':
					// End of level informations
					this.endOfLine = true;
					this.index++;
					return nextToken();
				case 't':
					this.index++;
					return nextToken();
				case 'i':
					this.index++;
					return nextToken();
				case 'm':
					this.index++;
					return nextToken();
				case 'e':
					this.index++;
					return nextToken();
				case '=':
					this.index++;
					return nextToken();
				default:
					throw new LexerException(
						"Illegal character" + c 
					);
			}
			
		}

		this.index++;

		return token;
	
	}
	
	public int getTime(){
		return time;
	}

}
