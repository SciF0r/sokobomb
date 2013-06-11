package ch.bfh.sokobomb.state;

import java.io.IOException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.solver.Path;

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
		Application.getFieldController().getField().getHeader().draw();
		

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
		try {
			this.path = new Path(
				field,
				Application.getFieldController().getField().getPlayer().getCoordinate(),
				coordinate
			);
		} catch (InvalidCoordinateException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}