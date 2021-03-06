package ch.bfh.sokobomb.parser;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * A token for the field
 *
 * @author Denis Simonet
 */
public class FieldToken implements Token {

	/**
	 * The token type
	 */
	public int type;

	/**
	 * The coordinate
	 */
	public TileCoordinate coordinate;

	/**
	 * Constants to associate the token type
	 */
	public static final int WALL          = 0; // #
	public static final int PLAYER_START  = 1; // @
	public static final int TARGET        = 2; // .
	public static final int BOMB_START    = 3; // $
	public static final int BOMB_TARGET   = 4; // *
	public static final int PLAYER_TARGET = 5; // +
	public static final int FLOOR         = 6; // Space
	public static final int EOF           = 7; // End Of File
	public static final int EMPTY         = 8; // Empty
	public static final int DIGIT         = 9; // [0-9]
}