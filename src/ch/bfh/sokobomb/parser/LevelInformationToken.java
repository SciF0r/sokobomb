package ch.bfh.sokobomb.parser;

/**
 * A token for level information
 *
 * @author Denis Simonet
 */
public class LevelInformationToken implements Token {

	/**
	 * The token type
	 */
	public int type;

	/**
	 * The value
	 */
	public int    intValue;
	public String stringValue;

	/**
	 * Constants to associate the token type
	 */
	public static final int TIME  = 0;
	public static final int TITLE = 1;
	public static final int EOF   = 2; // End of file
}