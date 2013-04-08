package ch.bfh.sokobomb.exception;

/**
 * Indicates an invalid coordinate
 *
 * @author Denis Simonet
 */
public class InvalidCoordinateException extends RuntimeException {

	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = -3153307956424263199L;

	public InvalidCoordinateException(String s) {
		super(s);
	}
}