package ch.bfh.sokobomb.controller;

import ch.bfh.sokobomb.field.PlayField;

/**
 * Main controller
 *
 * @author Denis Simonet
 */
public class MainController {

	private PlayField field;
	private OpenGLController openGL;

	/**
	 * Set field and initialize openGL
	 *
	 * @param field
	 */
	public MainController(PlayField field) {
		this.field = field;
		this.openGL = new OpenGLController();
		this.openGL.start(this.field, 544, 512);
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		PlayField field = new PlayField();

		new MainController(field);
	}
}