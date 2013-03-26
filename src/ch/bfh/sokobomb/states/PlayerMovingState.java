package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.util.Path;

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
		for (FieldItem item: super.field.getItems()) {
			item.draw();
		}

		for (Bomb bomb: super.field.getBombs()) {
			bomb.draw();
		}

		super.field.getPlayer().draw();

		Display.update();

		if (this.path.hasNext()) {
			this.field.getPlayer().setPosition(this.path.next());
		}
		else {
			super.field.setState(new PlayState(super.field));
		}

		super.pollInput();
	}

	/**
	 * Calculates the path
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		this.path = new Path(
			super.field.getPlayer().getCoordinate(),
			coordinate,
			super.field
		);
	}
}