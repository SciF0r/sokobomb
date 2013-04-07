package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.controller.OpenGLController;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.model.Floor;
import ch.bfh.sokobomb.model.Wall;

public class DesignState extends State {

	private boolean buttonDown = false;
	private FieldItem tileAtMouse;

	public DesignState() {
		this.stateId = State.DESIGN;
		this.tileAtMouse = new Wall();
		this.drawTile();
	}

	/**
	 * @param coordinate Sets a new tile position
	 */
	private void drawTile() {
		this.tileAtMouse.setPosition(
			OpenGLController.getMousePosition().getTileCoordinate()
		);

		try {
			this.tileAtMouse.draw();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Adds the current tile to field
	 */
	private void addTileToField() {
		try {
			Application.getFieldController().addItem(
				(FieldItem)this.tileAtMouse.clone()
			);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_P:
				Application.getStateController().setState(State.PLAY);
				Application.getFieldController().restoreOldField();
				break;
			case Keyboard.KEY_U:
				Application.getFieldController().getField().undo();
				break;
			case Keyboard.KEY_1:
				this.tileAtMouse = new Wall();
				this.drawTile();
				break;
			case Keyboard.KEY_2:
				this.tileAtMouse = new Floor();
				this.drawTile();
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Application.getFieldController().getField().addFieldToHistory();
		this.addTileToField();
		this.buttonDown = true;
	}

	@Override
	public void handleLeftClickRelease(Coordinate coordinate) {
		this.buttonDown = false;
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate) {
		if (
			this.buttonDown
			&&
			this.tileAtMouse != null
			&&
			!this.tileAtMouse.getCoordinate().equals(coordinate)
		) {
			this.addTileToField();
		}

		this.tileAtMouse.setPosition(coordinate.getTileCoordinate());
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Application.getFieldController().drawField();

		if (this.tileAtMouse != null) {
			this.tileAtMouse.draw();
		}
	}
}