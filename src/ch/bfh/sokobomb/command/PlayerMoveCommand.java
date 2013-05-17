package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.state.State;

public class PlayerMoveCommand extends MoveCommand {

	public PlayerMoveCommand(PlayField field, TileCoordinate target) {
		super(field, target);
	}

	@Override
	public void execute() {
		this.field.addFieldToHistory();

		if (this.field.mayEnter(this.target)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			this.field.setPlayerPosition(this.target);
		}
	}

	public void undo() {
		this.field.undo();
	}
}