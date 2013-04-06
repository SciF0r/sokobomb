package ch.bfh.sokobomb.controller;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ch.bfh.sokobomb.field.Field;
import ch.bfh.sokobomb.util.OpenGLLoader;

/**
 * Controller which handles all the OpenGL stuff and draws the field
 *
 * @author Christoph Bruderer
 * @author Denis Simonet
 */
public class OpenGLController {

	/**
	 * Load the lwjgl library
	 */
	public OpenGLController() {
		this.loadLibs();
	}

	/**
	 * Initialize lwjgl
	 */
	private void loadLibs() {
		try {
			OpenGLLoader.loadNativeLibraries();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Couldn't load the lwjgl nativ libraries");
			System.exit(0);
		}
	}

	/**
	 * Draw and refresh the field
	 *
	 * @param field The field to be drawn
	 */
	public void start(Field field, int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setVSyncEnabled(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		Display.setTitle("SokoBomb");

		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);  
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, width, height);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);

		Keyboard.enableRepeatEvents(true);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			field.draw();
		}

		Display.destroy();
		System.exit(0);
	}
}