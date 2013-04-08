package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.model.coordinate.Coordinate;

/**
 * Draws a Title and a list of menu Items 
 * 
 * @author Christoph Bruderer
 *
 */
public class Menu implements Drawable{

	final public static int DOWN = 0;
	final public static int UP   = 1;

	private LinkedList<MenuItem> items;
	private AngelCodeFont font;
	final private String title;

	public Menu(String title) throws SlickException {
		this.title = title;
		this.items = new LinkedList<MenuItem>();
		this.font  = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	}

	/**
	 * @param item Item to be added to the menu
	 */
	public void addMenuItem(MenuItem item) {
		items.add(item);
		items.getFirst().setChecked(true);
	}

	/**
	 * @return All menu items
	 */
	public LinkedList<MenuItem> getItems() {
		return this.items;
	}

	/**
	 * Select the next item
	 *
	 * @param direction
	 */
	public void nextItem(int direction) {
		Iterator<MenuItem> menuIterator;

		switch (direction) {
			case DOWN:
				menuIterator = this.items.iterator();
				break;
			case UP:
				menuIterator = this.items.descendingIterator();
				break;
			default:
				throw new IllegalArgumentException("Invalid direction given");
		}

		MenuItem item;
		while (menuIterator.hasNext()) {
			item = menuIterator.next();

			if (item.isChecked() && menuIterator.hasNext()) {
				item.setChecked(false);
				menuIterator.next().setChecked(true);
				return;
			}
		}
	}

	/**
	 * @return The currently selected item action
	 */
	public int getSelectedItemAction() {
		Iterator<MenuItem> menuIterator = items.iterator();

		MenuItem item;
		while (menuIterator.hasNext()) {
			item = menuIterator.next();
			if (item.isChecked()) {
				return item.getAction();
			}
		}

		return MenuItem.NO_ACTION;
	}

	/**
	 * @param coord Handle action according to mouse click
	 */
	public int mouseAction(Coordinate coord) {
		for (MenuItem item : items){
			if (item.containsCoordinate(coord)) {	
				return item.getAction();
			}
		}
		
		return MenuItem.NO_ACTION;
	}

	@Override
	public void draw() throws IOException {
		int titleOffset = 10;
		int titleWidth  = font.getWidth(this.title);

		int x = (Display.getWidth() - titleWidth) / 2;
		int y = titleOffset;

		this.font.drawString(x, y, this.title);

		for (MenuItem item : items) {
			String text = item.getText();

			x = (Display.getWidth() - font.getWidth(text)) / 2;
			y += 1.5 * font.getHeight(text);

			item.setMinCoord(new Coordinate(x, y));
			item.setMaxCoord(new Coordinate(x + font.getWidth(text), y + font.getHeight(text)));

			if (item.isChecked()){
				this.font.drawString(x, y, text, Color.darkGray);
			}
			else {
				this.font.drawString(x, y, text);
			}
		}
	}

	/**
	 * Check whether the mouse pointer is in a menu item
	 *
	 * @param coord
	 */
	public void checkMousePosition(Coordinate coord) {
		MenuItem current = null;

		for (MenuItem item : items){
			if (item.isChecked()){
				current = item;
				break;
			}
		}

		for (MenuItem item : items){
			if (item.containsCoordinate(coord)) {
				if (!current.equals(null)) {
					current.setChecked(false);
				}
				item.setChecked(true);
			}
		}
	}
}