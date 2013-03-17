package ch.bfh.sokobomb.controller;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2i;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.util.OpenGLLoader;

/**
 * Controller which handles all the OpenGL stuff
 *
 * @author Denis Simonet
 */
public class OpenGLController {

	/*
	 * For the demo only
	 */
	private int x;
	private int y;
	private int row=6;
	private int col=8;

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
	 * Draw a field
	 *
	 * @param field
	 */
	public void draw(Field field) {
		// TODO implement
	}

	/**
	 * Demonstration, needs to be replaced
	 */
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		Display.setTitle("OpengL - TEST");

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, -1, 1);
		glMatrixMode(GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			//clear buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			//set color
			glColor3f(1.0f, 1.0f, 0.0f);

			//draw all fields
			int delta=4;
			int size=100;
			for (int i=0; i<col; i++) {
				int xpos = i*100;
				for(int j=0; j<row; j++) {
					int ypos=j*size;
					glBegin(GL_QUADS);
					glVertex2i(xpos+delta,ypos+delta);
					glVertex2i(xpos+size-delta,ypos+delta);
					glVertex2i(xpos+size-delta,ypos+size-delta);
					glVertex2i(xpos+delta,ypos+size-delta);
					glEnd();
				}
			}

			drawPlayer(x,y);
			System.out.println(x+" "+y);

			pollInput();
			Display.update();
		}

		Display.destroy();
		System.exit(0);
	}

	/**
	 * Draws the player, needs to be replaced
	 *
	 * @param x
	 * @param y
	 */
	private void drawPlayer(int x,int y) {
		//set color
		glColor3f(0.0f, 0.0f, 1.0f);

		glBegin(GL_QUADS);
		int size=100;
		int s = 50;
		int offset=25;
		glVertex2i(x*size+offset, y*size+offset);
		glVertex2i(x*size+s+offset, y*size+offset);
		glVertex2i(x*size+offset+s, y*size+offset+s);
		glVertex2i(x*size+offset, y*size+offset+s);
		glEnd();
	}

	/**
	 * Mouse and keyboard event handling
	 */
	private void pollInput() {
		//Keyboard.enableRepeatEvents(false);
		if (Mouse.isButtonDown(0)) {
			int mousex = Mouse.getX();
			int mousey = Mouse.getY();
			//this.drawPlayer(x/100, y/100);
			x=mousex/100;
			y=mousey/100;
		}

		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				if (x<col-1) {
					x=x+1;
					//Display.sync(10);
					//System.out.println(x +" "+y);
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				if (x>0) {
					x=x-1;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				if (y<row-1) {
					y=y+1;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				if (y>0) {
					y=y-1;
				}
			}
		}
	}
}