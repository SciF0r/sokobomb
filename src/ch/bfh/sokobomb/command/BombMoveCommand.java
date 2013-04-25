package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;

public class BombMoveCommand extends MoveCommand {

	private Bomb bomb;

	public BombMoveCommand(TileCoordinate target, Bomb bomb) {
		super(target);

		this.bomb = bomb;
	}

	@Override
	public void execute(Field field) {
		this.bomb.setPosition(this.target);
	}
}