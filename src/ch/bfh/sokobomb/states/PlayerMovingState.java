package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.path.Path;

/**
 * This state moves the player along a given path
 *
 * The path is calculated by Dijkstra
 *
 * @author Denis Simonet
 */
public class PlayerMovingState extends PlayFieldState {

	private Path path;
	private long timestamp;

	public PlayerMovingState() {
		super.stateId = State.PLAYER_MOVING;
	}

	@Override
	public void doEntry() {
		this.timestamp = 0;
	}

	@Override
	public void handleKeyPress(int key) {
		Application.getStateController().setState(State.PLAY);
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		Application.getStateController().setState(State.PLAY);
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.getField().drawField();

		if ((System.currentTimeMillis() - this.timestamp) < 50) {
			return;
		}

		this.timestamp = System.currentTimeMillis();

		if (this.path.hasNext()) {
			super.getField().getPlayer().setPosition(this.path.next());
		}
		else {
			Application.getStateController().setState(State.PLAY);
		}
	}

	/**
	 * Calculates the path
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		this.path = new Path(
			super.getField(),
			super.getField().getCache().getNodeAtCoordinate(
				super.getField().getPlayer().getCoordinate()
			),
			super.getField().getCache().getNodeAtCoordinate(
				coordinate
			)
		);
	}
}