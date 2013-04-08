package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.path.Path;

/**
 * This state moves the player along a given path
 *
 * The path is calculated by Dijkstra
 *
 * @author Denis Simonet
 */
public class PlayerMovingState extends State {

	private Path path;
	private long timestamp;

	public PlayerMovingState() {
		this.stateId = State.PLAYER_MOVING;
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
		Application.getFieldController().drawField();

		if ((System.currentTimeMillis() - this.timestamp) < 50) {
			return;
		}

		this.timestamp = System.currentTimeMillis();

		if (this.path.hasNext()) {
			Application.getFieldController().getField().getPlayer().setPosition(this.path.next());
		}
		else {
			Application.getStateController().setState(State.PLAY);
		}
	}

	/**
	 * Calculates the path
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		this.path = new Path(
			field,
			field.getCache().getNodeAtCoordinate(
					Application.getFieldController().getField().getPlayer().getCoordinate()
			),
			field.getCache().getNodeAtCoordinate(
				coordinate
			)
		);
	}
}