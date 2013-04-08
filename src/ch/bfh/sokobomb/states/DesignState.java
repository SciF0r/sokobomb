package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.controller.OpenGLController;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.DeltaCoordinate;
import ch.bfh.sokobomb.model.tiles.Floor;
import ch.bfh.sokobomb.model.tiles.Tile;
import ch.bfh.sokobomb.model.tiles.Wall;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.path.DijkstraNode;

/**
 * This state allows you to draw a field
 *
 * @author Denis Simonet
 */
public class DesignState extends State {

	private boolean buttonDown = false;
	private Tile tileAtMouse;

	public DesignState() {
		this.stateId = State.DESIGN;
		this.tileAtMouse = new Wall(
			Token.WALL,
			OpenGLController.getMousePosition().getTileCoordinate()
		);
		this.drawTile();
	}

	/**
	 * @param coordinate Sets a new tile position
	 */
	private void drawTile() {
		if (this.tileAtMouse == null) {
			return;
		}

		try {
			this.tileAtMouse.setPosition(
				OpenGLController.getMousePosition().getTileCoordinate()
			);
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
			Application.getFieldController().addNode(
				(DijkstraNode)this.tileAtMouse.clone()
			);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.HOME);
				break;
			case Keyboard.KEY_R:
				Application.getFieldController().getField().resetObject();
				break;
			case Keyboard.KEY_U:
				Application.getFieldController().getField().undo();
				break;
			case Keyboard.KEY_0:
				this.tileAtMouse = null;
				break;
			case Keyboard.KEY_1:
				this.tileAtMouse = new Wall(Token.WALL, OpenGLController.getMousePosition().getTileCoordinate());
				this.drawTile();
				break;
			case Keyboard.KEY_2:
				this.tileAtMouse = new Floor(Token.FLOOR, OpenGLController.getMousePosition().getTileCoordinate());
				this.drawTile();
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Application.getFieldController().getField().addFieldToHistory();
		this.buttonDown = true;

		if (this.tileAtMouse == null) {
			Application.getFieldController().removeNode(coordinate.getTileCoordinate());
		}
		else {
			this.addTileToField();
		}
	}

	@Override
	public void handleLeftClickRelease(Coordinate coordinate) {
		this.buttonDown = false;
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate, DeltaCoordinate delta) {
		if (this.buttonDown) {
			if (this.tileAtMouse != null && !this.tileAtMouse.getCoordinate().equals(coordinate)) {
				this.addTileToField();
			}
			else if (this.tileAtMouse == null) {
				Application.getFieldController().removeNode(coordinate.getTileCoordinate());
			}
		}

		if (this.tileAtMouse != null) {
			this.tileAtMouse.setPosition(coordinate.getTileCoordinate());
		}
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