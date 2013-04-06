package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.model.Coordinate;

public class DesignState extends DesignFieldState {

	public DesignState() {
		super.stateId = State.DESIGN;
	}
	@Override
	public void handleKeyPress(int key) {
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.getField().drawField();
	}
}