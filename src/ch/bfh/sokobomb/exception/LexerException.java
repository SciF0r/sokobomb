package ch.bfh.sokobomb.exception;

/**
 * Obviously used in the Lexer
 *
 * @author Denis Simonet
 */
public class LexerException extends RuntimeException {

	/**
	 * A generated serial id
	 */
	private static final long serialVersionUID = 5350066228330164575L;

	public LexerException(String s) {
		super(s);
	}
}