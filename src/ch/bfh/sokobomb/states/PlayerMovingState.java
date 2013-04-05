package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
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

	public PlayerMovingState(Field field) {
		super(field);
	}

	@Override
	public void doEntry() {
		this.timestamp = 0;
	}

	@Override
	public void handleKeyPress(int key) {
		super.field.setState(new PlayState(super.field));
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		super.field.setState(new PlayState(super.field));
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.field.drawField();

		if ((System.currentTimeMillis() - this.timestamp) < 300) {
			return;
		}

		this.timestamp = System.currentTimeMillis();

		if (this.path.hasNext()) {
			super.field.getPlayer().setPosition(this.path.next());
		}
		else {
			super.field.setState(new PlayState(super.field));
		}
	}

	/**
	 * Calculates the path
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		this.path = new Path(
			super.field,
			super.field.getCache().getNodeAtCoordinate(
				super.field.getPlayer().getCoordinate()
			),
			super.field.getCache().getNodeAtCoordinate(
				coordinate
			)
		);
	}
}