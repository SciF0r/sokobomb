package ch.bfh.sokobomb.exception;

/**
 * Indicates an invalid coordinate
 *
 * @author Denis Simonet
 */
public class NoNextLevelException extends RuntimeException {

	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = 2267000633434945992L;

	public NoNextLevelException(String s) {
		super(s);
	}
}