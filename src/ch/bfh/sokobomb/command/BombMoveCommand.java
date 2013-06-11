package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;

/**
 * Let a bomb move around
 *
 * @author Denis Simonet
 *
 */
public class BombMoveCommand extends MoveCommand {

	private Bomb bomb;

	/**
	 * Additionally stores a bomb
	 *
	 * @param field
	 * @param target
	 * @param bomb
	 */
	public BombMoveCommand(PlayField field, TileCoordinate target, Bomb bomb) {
		super(field, target);

		this.bomb = bomb;
	}

	@Override
	public void execute() {
		this.field.addFieldToHistory();
		this.bomb.setPosition(this.target);
	}

	@Override
	public void undo() {
		this.field.undo();
	}
}