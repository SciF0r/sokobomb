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

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.util.OpenGLLoader;
import ch.bfh.sokobomb.util.Tiles;

/**
 * Controller which handles all the OpenGL stuff and draws the field
 *
 * @author Christoph Bruderer
 * @author Denis Simonet
 */
public class OpenGLController {

	private int width;
	private int height;

	/**
	 * Load the lwjgl library
	 */
	public OpenGLController(int width, int height) {
		this.width      = width;
		this.height     = height;
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
	public void start(Field field) {
		try {
			Display.setDisplayMode(new DisplayMode(this.width, this.height));
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
		glViewport(0, 0, this.width, this.height);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();
		glOrtho(0, this.width, this.height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			//clear buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			try {
				field.draw();
			}
			catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

			Display.update();
			Display.sync(100);

			pollInput(field);
		}

		Display.destroy();
		System.exit(0);
	}

	/**
	 * Mouse and keyboard event handling
	 *
	 * @param field The field to act on
	 */
	private void pollInput(Field field) {
		Keyboard.enableRepeatEvents(true);

		if (Mouse.isButtonDown(0)) {
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			field.getPlayer().setPosition(mouseX / Tiles.WIDTH, (this.height - mouseY) / Tiles.HEIGHT);
		}

		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				field.movePlayer(1, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				field.movePlayer(-1, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				field.movePlayer(0, -1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				field.movePlayer(0, 1);
			}
		}
	}
}