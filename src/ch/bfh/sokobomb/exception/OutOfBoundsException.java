package ch.bfh.sokobomb.exception;

/**
 * Indicates an invalid coordinate
 *
 * @author Denis Simonet
 */
public class OutOfBoundsException extends Exception {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = -3153307956424263199L;

	public OutOfBoundsException(String s) {
		super(s);
	}
}