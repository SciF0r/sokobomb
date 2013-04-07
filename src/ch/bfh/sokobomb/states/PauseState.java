package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;

/**
 * Shows the pause screen
 *
 * @author Christoph Bruderer
 */
public class PauseState extends State {



private Menu pauseMenu;

	public PauseState() {
		try {
			this.pauseMenu = new Menu("....Game paused....");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.pauseMenu.addMenuItem(new MenuItem("resume Game", MenuItem.RESUME_GAME));
		this.pauseMenu.addMenuItem(new MenuItem("reset this Level", MenuItem.RESET_LEVEL));
		this.pauseMenu.addMenuItem(new MenuItem("exit Game", MenuItem.END_GAME));
		
		
		this.stateId = State.PAUSE;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PLAY);
				break;
			case Keyboard.KEY_DOWN:
				pauseMenu.nextItem(Menu.DOWN);
				break;
			case Keyboard.KEY_UP:
				pauseMenu.nextItem(Menu.UP);
				break;
			case Keyboard.KEY_RETURN:
				pauseMenu.performAction();
				break;
			
		}
	}
	
	
	
	

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		int x = coordinate.getX();
		int y = coordinate.getY();

//		if (mb.getX() < x && (mb.getX() + mb.getWidth()) > x && mb.getY() < y && (mb.getY() + mb.getHeight()) > y) {
//			Application.getStateController().setState(State.PLAY);
//		}
	}
	
	

	
	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		 
		this.pauseMenu.draw();
	}
}