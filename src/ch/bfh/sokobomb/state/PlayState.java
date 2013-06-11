package ch.bfh.sokobomb.state;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.command.PlayerMoveCommand;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.Header;
import ch.bfh.sokobomb.model.Time;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

public class PlayState extends State {

	//private Header title;

	public PlayState() {
		this.stateId = State.PLAY;

		Time   time  = Application.getFieldController().getTime();
		String title = Application.getFieldController().getTitle();
		
		if (time == null) {
			throw new RuntimeException("Invalid level: Time is missing");
		}

		if (title == null) {
			throw new RuntimeException("Invalid level: Title is missing");
		}

		Application.getFieldController().getField().setHeader( new Header(title, time));
	}

	@Override
	public void handleKeyPress(int key) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		switch (key) {
			case Keyboard.KEY_RIGHT:
				field.movePlayer(1, 0);
				break;
			case Keyboard.KEY_LEFT:
				field.movePlayer(-1, 0);
				break;
			case Keyboard.KEY_UP:
				field.movePlayer(0, -1);
				break;
			case Keyboard.KEY_DOWN:
				field.movePlayer(0, 1);
				break;
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PAUSE);
				break;
			case Keyboard.KEY_U:
				field.undo();
				break;
			case Keyboard.KEY_R:
				field.restartLevel();
				Application.getStateController().setState(State.PLAY);
				break;
			case Keyboard.KEY_S:
				Application.getStateController().setState(State.SOLVING);
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		this.commands.add(new PlayerMoveCommand(
			(PlayField)Application.getFieldController().getField(),
			new Coordinate(
				coordinate.getX(),
				coordinate.getY()
			).getTileCoordinate()
		));
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		Application.getFieldController().getField().getHeader().draw();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		if (field.mayEnter(coordinate)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			field.setPlayerPosition(coordinate);
			field.addFieldToHistory();
		}
	}

	@Override
	protected void startDo() {
		System.out.println("Start");
		Application.getFieldController().getField().getTime().start();
	}

	@Override
	protected void stopDo() {
		System.out.println("Stop");
		Application.getFieldController().getField().getTime().stop();
	}
}