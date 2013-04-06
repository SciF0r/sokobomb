package ch.bfh.sokobomb.states;

import ch.bfh.sokobomb.field.DesignField;
import ch.bfh.sokobomb.field.Field;

abstract public class DesignFieldState extends State {
	private static Field field;

	public DesignField getField() {
		if (DesignFieldState.field == null) {
			DesignFieldState.field = new DesignField();
		}

		return (DesignField)DesignFieldState.field;
	}
}