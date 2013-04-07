package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.parser.Token;

public class DesignState extends State {

	private int currentTile    = Token.WALL;
	private boolean buttonDown = false;

	public DesignState() {
		this.stateId = State.DESIGN;  
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_P:
				Application.getStateController().setState(State.PLAY);
				Application.getFieldController().restoreOldField();
				break;
			case Keyboard.KEY_1:
				this.currentTile = Token.WALL;
				break;
			case Keyboard.KEY_2:
				this.currentTile = Token.FLOOR;
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Token token      = new Token();
		token.type       = this.currentTile;
		token.coordinate = coordinate.getTileCoordinate();

		Application.getFieldController().addItemByToken(token);

		this.buttonDown = true;
	}

	@Override
	public void handleLeftClickRelease(Coordinate coordinate) {
		this.buttonDown = false;
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate) {
		if (this.buttonDown) {
			
		}
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Application.getFieldController().drawField();
	}
}