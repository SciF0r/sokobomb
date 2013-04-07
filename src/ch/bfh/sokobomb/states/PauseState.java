package ch.bfh.sokobomb.states;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

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

private static final int DOWN = 0;
private static final int UP = 1;

private Menu pauseMenu;

	public PauseState() {
		MenuItem reset = new MenuItem("reset this Level");
		MenuItem endGame = new MenuItem("exit Game");
		MenuItem resume = new MenuItem("resume Game");
		LinkedList<MenuItem> items = new LinkedList<MenuItem>();
		items.add(reset);
		items.add(resume);
		items.add(endGame);
		try {
			this.pauseMenu = new Menu("....Game paused....", items);
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		this.stateId = State.PAUSE;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PLAY);
				break;
			case Keyboard.KEY_DOWN:
				this.nextItem(this.DOWN);
				break;
			case Keyboard.KEY_UP:
				this.nextItem(this.UP);
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
	
	private void nextItem(int order){
		
		LinkedList<MenuItem> menuitems = this.pauseMenu.getItems();
		Iterator<MenuItem> it = null;
		switch ( order){
		case DOWN:
			it = menuitems.iterator();
			break;
		case UP:
			it = menuitems.descendingIterator();
			break;
			
	}
		
		while(it.hasNext()){
			MenuItem item = it.next();
			if (item.isChecked()){
				item.setChecked(false);
				it.next().setChecked(true);
			}	
		}
	}

	
	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		 
		pauseMenu.draw();
	}
}