package ch.bfh.sokobomb.exception;

/**
 * Indicates that no solution could be found
 *
 * @author Denis Simonet
 */
public class NoSolutionFoundException extends Exception {

	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = 8313394987340555521L;

	public NoSolutionFoundException(String s) {
		super(s);
	}
}