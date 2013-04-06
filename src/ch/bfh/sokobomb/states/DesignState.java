package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.util.Tiles;

public class DesignState extends State {

	private int currentTile = 6;

	public DesignState() {
		this.stateId = State.DESIGN;  
	}
	@Override
	public void handleKeyPress(int key) {
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Token token      = new Token();
		token.type       = this.currentTile;
		token.coordinate = new Coordinate(
			coordinate.getX() / Tiles.WIDTH,
			coordinate.getY() / Tiles.HEIGHT
		);
		Application.getFieldController().addItemByToken(token);
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