package ch.bfh.sokobomb.command;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.state.State;

public class PlayerMoveCommand extends MoveCommand {

	public PlayerMoveCommand(TileCoordinate target) {
		super(target);
	}

	@Override
	public void execute(Field field) {
		PlayField playField = (PlayField)field;

		if (playField.mayEnter(this.target)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			playField.setPlayerPosition(this.target);
			playField.addFieldToHistory();
		}
	}
}