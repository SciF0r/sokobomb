package ch.bfh.sokobomb.states;

import java.io.IOException;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.path.Path;

public class PlayerMovingState extends State {

	private Path path;

	public PlayerMovingState(Field field) {
		super(field);
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.field.drawField();

		if (this.path.hasNext()) {
			super.field.getPlayer().setPosition(this.path.next());
			// TODO Threads? Or timer?
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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