package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.exception.LexerException;
import ch.bfh.sokobomb.field.Field;

/**
 * Parses the tokens from the lexer
 *
 * @author Denis Simonet
 */
public class Parser {

	private FieldLexer            fieldLexer;
	private LevelInformationLexer informationLexer;

	/**
	 * Creates a new lexer object
	 */
	public Parser() {
		this.fieldLexer       = new FieldLexer();
		this.informationLexer = new LevelInformationLexer();
	}

	/**
	 * Parse a file
	 *
	 * @param path
	 */
	public void parseLevelInformation(String path, Field field) {
		try {
			LevelInformationToken token;

			this.informationLexer.initLexer(path);

			while ((token = this.informationLexer.nextToken()).type != LevelInformationToken.EOF) {
				field.setAttributeByToken(token);
			}
		}
		catch (LexerException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Parse a file
	 *
	 * @param path
	 */
	public void parseField(String path, Field field) {
		try {
			FieldToken token;

			this.fieldLexer.initLexer(path);

			while ((token = this.fieldLexer.nextToken()).type != FieldToken.EOF) {
				if (token.type == FieldToken.EMPTY) {
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