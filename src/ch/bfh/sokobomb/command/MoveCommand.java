package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * Move an object
 *
 * @author Denis Simonet
 */
abstract public class MoveCommand extends Command {

	protected TileCoordinate target;
	protected PlayField field;

	public MoveCommand(PlayField field, TileCoordinate target) {
		this.field  = field;
		this.target = target;
	}
}