package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

abstract public class MoveCommand extends Command {

	protected TileCoordinate target;

	public MoveCommand(TileCoordinate target) {
		this.target = target;
	}
}