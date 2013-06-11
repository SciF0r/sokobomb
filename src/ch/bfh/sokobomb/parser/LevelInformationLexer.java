package ch.bfh.sokobomb.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ch.bfh.sokobomb.exception.LexerException;

/**
 * A lexer for level meta information
 *
 * @author Christoph Bruderer
 */
public class LevelInformationLexer implements Lexer {

	private String[] source; // source string for lexical analysis
	private int      index, length;

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
				char c = (char)fis.read();

				if (c == '\n' || c == '\r') {
					break;
				}

				sourceBuilder.append(c);
			}

			fis.close();

			this.source = sourceBuilder.toString().split(";");
			this.index  = 0;
			this.length = this.source.length;
		} catch (IOException e) {
			throw new LexerException(
				e.getMessage()
			);
		}
	}

	@Override
	public LevelInformationToken nextToken() throws RuntimeException, LexerException {
		LevelInformationToken token = new LevelInformationToken();

			if (this.index >= this.length) {
			token.type = LevelInformationToken.EOF;
		}
		else {
			String attribute[] = this.source[this.index].split("=", 2);


			if (attribute[0].equals("time")) {
				token.type = LevelInformationToken.TIME;
								
				token.intValue = Integer.parseInt(attribute[1]);
			}
			else if (attribute[0].equals("title")) {
				token.type        = LevelInformationToken.TITLE;
				
				token.stringValue = attribute[1];
			}
			else {
				throw new LexerException(
					"Illegal attribute: " + attribute[0] 
				);
			}
		}

		this.index++;
		return token;
	}
}