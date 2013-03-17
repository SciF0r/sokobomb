package ch.bfh.sokobomb.controller;

import ch.bfh.sokobomb.model.Field;

/**
 * Main controller
 *
 * @author Denis Simonet
 */
public class MainController {

	final static String DEFAULT_LEVEL = "res/levels/level1.txt";

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
		this.openGL.start();
	}

	public void draw() {
		this.openGL.draw(this.field);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Field field = new Field(MainController.DEFAULT_LEVEL);

		MainController mainController = new MainController(field);
		mainController.draw();
	}
}