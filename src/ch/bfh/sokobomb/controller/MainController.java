package ch.bfh.sokobomb.controller;

import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.field.PlayField;

/**
 * Main controller
 *
 * @author Denis Simonet
 */
public class MainController {

	private OpenGLController openGL;

	/**
	 * Set field and initialize openGL
	 *
	 * @param field
	 */
	public MainController(Field field) {
		this.openGL = new OpenGLController();
		this.openGL.start(field, 544, 512);
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		PlayField field = new PlayField();

		new MainController(field);
	}
}