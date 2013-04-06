package ch.bfh.sokobomb.states;

import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.field.PlayField;

abstract public class PlayFieldState extends State {
	private static Field field;

	public PlayField getField() {
		if (PlayFieldState.field == null) {
			PlayFieldState.field = new PlayField();
		}

		return (PlayField)PlayFieldState.field;
	}
}