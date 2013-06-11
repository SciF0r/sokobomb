package ch.bfh.sokobomb.state;

import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import ch.bfh.sokobomb.command.Command;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.DeltaCoordinate;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * Class for a state
 *
 * @author Denis Simonet
 */
public abstract class State {

	protected LinkedList<Command> commands = new LinkedList<Command>();

	/**
	 * The available states
	 */
	final public static int DESIGN        = 0;
	final public static int END_GAME      = 1;
	final public static int HIGHSCORE     = 2;
	final public static int PAUSE         = 3;
	final public static int PLAYER_MOVING = 4;
	final public static int PLAY          = 5;
	final public static int WON           = 6;
	final public static int HOME          = 7;
	final public static int SOLVING       = 8;
	final public static int GAME_OVER     = 9;

	/**
	 * The state id of this state
	 */
	protected int stateId;

	/**
	 * @return The state id
	 */
	public int getStateId() {
		return this.stateId;
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
	 * Handle a mouse move
	 */
	public void handleMouseMoved(Coordinate coordinate, DeltaCoordinate delta) {
		// Nothing
	}

	/**
	 * Handle a left mouse click
	 */
	public void handleLeftClick(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Handle a left mouse click release
	 */
	public void handleLeftClickRelease(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Handle a right mouse click
	 */
	public void handleRightClick(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Handle a right mouse click release
	 */
	public void handleRightClickRelease(Coordinate coordinate) {
		// Nothing
	}

	/**
	 * Sets a new player position
	 *
	 * @param coordinate
	 * @return Whether the player actually moved to the position
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		// Nothing
	}

	/**
	 * Mouse and keyboard event handling
	 */
	final public void pollInput() {		
		while (Mouse.next()) {
			int button = Mouse.getEventButton();
			int x      = Mouse.getEventX();
			int y      = Display.getHeight() - Mouse.getEventY();

			if (button == -1) {
				this.handleMouseMoved(
					new Coordinate(x, y),
					new DeltaCoordinate(
						Mouse.getEventDX(),
						Mouse.getEventDY()
					)
				);
			}

			if (button == 0) {
				if (Mouse.getEventButtonState()) {
					this.handleLeftClick(new Coordinate(x, y));
				}
				else {
					this.handleLeftClickRelease(new Coordinate(x, y));
				}
			}

			if (button == 1) {
				if (Mouse.getEventButtonState()) {
					this.handleRightClick(new Coordinate(x, y));
				}
				else {
					this.handleRightClickRelease(new Coordinate(x, y));
				}
			}
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
		GL11.glColor4f(0.5f, 0.70f, 0.5f, 0.8f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0,                  0                  );
		GL11.glVertex2i(0,                  Display.getHeight());
		GL11.glVertex2i(Display.getWidth(), Display.getHeight());
		GL11.glVertex2i(Display.getWidth(), 0                  );
		GL11.glEnd();
	}

	/**
	 * Process all commands
	 */
	public void processCommads() {
		if (!this.commands.isEmpty()) {
			for (Command command: this.commands) {
				command.execute();
			}

			this.commands.clear();
		}
	}
}