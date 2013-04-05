package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;

/**
 * Class for a state
 *
 * @author Denis Simonet
 */
public abstract class State {

	protected Field field;

	protected State(Field field) {
		this.field = field;
	}

	/**
	 * Triggered on state entry
	 */
	final public void entry() {
		this.doEntry();
		this.startDo();
	}

	/**
	 * Triggered on state exit
	 */
	final public void exit() {
		this.stopDo();
		this.doExit();
	}

	/**
	 * Default implementation of entry
	 */
	protected void doEntry() {
		// Nothing
	}

	/**
	 * Default implementation of do
	 */
	protected void startDo() {
		// Nothing

	}

	/**
	 * Default implementation of stop do
	 */
	protected void stopDo() {
		// Nothing
	}

	/**
	 * Default implementation of exit
	 */
	protected void doExit() {
		// Nothing
	}

	/**
	 * Draw the field
	 */
	public abstract void draw() throws IOException;

	/**
	 * Handle a key press
	 */
	public void handleKeyPress(int key) {
		// Nothing
	}

	/**
	 * Handle a key release
	 */
	public void handleKeyRelease(int key) {
		// Nothing
	}

	/**
	 * Handle a left mouse click
	 */
	public void handleLeftClick(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Handle a right mouse click
	 */
	public void handleRightClick(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Sets a new player position
	 *
	 * @param coordinate
	 * @return Whether the player actually moved to the position
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Mouse and keyboard event handling
	 */
	final public void pollInput() {
		Keyboard.enableRepeatEvents(true);

		// Left mouse button
		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = this.field.getHeight() - Mouse.getY();
			this.handleLeftClick(new Coordinate(x, y));
		}

		// Right mouse button
		if (Mouse.isButtonDown(1)) {
			int x = Mouse.getX();
			int y = this.field.getHeight() - Mouse.getY();
			this.handleRightClick(new Coordinate(x, y));
		}

		while (Keyboard.next()) {
			// Key was pressed
			if (Keyboard.getEventKeyState()) {
				this.handleKeyPress(Keyboard.getEventKey());
			}
			// Key was released
			else {
				this.handleKeyRelease(Keyboard.getEventKey());
			}
		}
	}

	/**
	 * Draws a transparent overlay for menus
	 */
	protected void drawTransparentOverlay() {
		GL11.glColor4f(0.5f, 0.70f, 0.5f, 0.6f);		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0,0);
		GL11.glVertex2i(0,field.getHeight());
		GL11.glVertex2i(field.getWidth(), field.getHeight());
		GL11.glVertex2i(field.getWidth(),0);
		GL11.glEnd();
	}
}