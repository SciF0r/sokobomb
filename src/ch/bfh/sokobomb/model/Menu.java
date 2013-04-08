package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.controller.StateController;
import ch.bfh.sokobomb.field.DesignField;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.states.State;
/**
 * UNDER CONSTRUCTION!!!
 * 
 * Draws a Title and a list of menu Items 
 * 
 * @author Christoph Bruderer
 *
 */
public class Menu implements Drawable{
	final public static int DOWN = 0;
	final public static int UP = 1;
	private int width,height;
	private LinkedList<MenuItem> items;
	private Iterator<MenuItem> menuIterator;
	private AngelCodeFont font;
	final private String title;
	private String empty = "EMPTY";

	public Menu(String title) throws SlickException{
		this.title = title;
		this.items = new LinkedList<MenuItem>();
		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		this.width = Display.getWidth();
		this.height = Display.getHeight();
	}

	public void addMenuItem(MenuItem item){
		items.add(item);
		items.getFirst().setChecked(true);
	}


	public LinkedList<MenuItem> getItems(){
		return items;
	}

	public void nextItem(int order){	
		switch ( order){
		case DOWN:
			menuIterator = items.iterator();
			break;
		case UP:
			menuIterator = items.descendingIterator();
			break;
		}

		while(menuIterator.hasNext()){
			MenuItem item = menuIterator.next();
			if (item.isChecked() && menuIterator.hasNext()){
				item.setChecked(false);
				menuIterator.next().setChecked(true);
			}	
		}
	}

	public int keyboardAction(){
		this.menuIterator = items.iterator();
		int action=MenuItem.NO_ACTION;
		while(menuIterator.hasNext()){
			MenuItem item = menuIterator.next();
			if (item.isChecked()){
				action = item.getAction();
				break;
			}
		}
		return action;
	}

	public void performAction(int action){
		PlayField field = (PlayField)Application.getFieldController().getField();
		switch (action){
		case MenuItem.START_GAME:
			Application.getStateController().setState(State.PLAY);
			break;
		case MenuItem.EXIT_GAME:
			Application.getStateController().setState(State.HOME);
			break;
		case MenuItem.RESET_LEVEL:
			field.restartLevel();
			Application.getStateController().setState(State.PLAY);
			break;
		case MenuItem.RESUME_GAME:
			Application.getStateController().setState(State.PLAY);
			break;
		case MenuItem.DESIGN_MODE:
			Application.getStateController().setState(State.DESIGN);
			Application.getFieldController().setField(new DesignField());
			break;
		case MenuItem.END_GAME:
			System.exit(0);
		case MenuItem.NO_ACTION:
			//Do nthing
			break;

		}

	}

	public void mouseAction(Coordinate coord){
		int action = MenuItem.NO_ACTION;
		for (MenuItem item : items){
			if (item.containsCoordinate(coord)){	
				action = item.getAction();
				break;
			}
		}
		this.performAction(action);
	}


	@Override
	public void draw() throws IOException {
		int titleOffset = 10;
		int titleWidth = font.getWidth(this.title);

		int x = (this.width - titleWidth) / 2;
		int y = titleOffset;

		this.font.drawString(x, y, this.title);

		for (MenuItem item : items ){
			String text = item.getText();
			x = (this.width - font.getWidth(text)) / 2;
			y += 1.5 * font.getHeight(text);

			item.setMinCoord(new Coordinate(x,y));
			item.setMaxCoord(new Coordinate(x+font.getWidth(text),y+font.getHeight(text)));

			if (item.isChecked()){
				this.font.drawString(x, y, text, Color.darkGray);
			}
			else {
				this.font.drawString(x, y, text);
			}
		}
	}

	public void checkMousePosition(Coordinate coord){
		MenuItem current = null;
		for (MenuItem item : items){
			if (item.isChecked()){
				current = item;
				break;
			}
		}
		for (MenuItem item : items){
			if (item.containsCoordinate(coord)){
				if (!current.equals(null)){
					current.setChecked(false);
				}
				item.setChecked(true);
			}
		}
	}

}
