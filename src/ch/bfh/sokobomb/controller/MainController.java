package ch.bfh.sokobomb.controller;

import ch.bfh.sokobomb.model.Field;

/**
 * Main controller
 *
 * @author Denis Simonet
 */
public class MainController {

	private Field field;
	private OpenGLController openGL;

	/**
	 * Set field and initialize openGL
	 *
	 * @param field
	 */
	public MainController(Field field) {
		this.field = field;
		this.openGL = new OpenGLController();
		this.openGL.start(this.field);
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		Field field = new Field(544, 512);

		new MainController(field);
	}
}