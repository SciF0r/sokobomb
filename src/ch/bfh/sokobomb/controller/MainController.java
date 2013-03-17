package ch.bfh.sokobomb.controller;

import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.Player;

/**
 * Main controller
 *
 * @author Denis Simonet
 */
public class MainController {

	private Field field;

	/**
	 * Set field and initialize openGL
	 *
	 * @param field
	 */
	public MainController(Field field) {
		this.field = field;
		OpenGLController openGL = new OpenGLController();
		openGL.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Player player = new Player();
		Field field = new Field(player);
		MainController mainController = new MainController(field);
	}
}