package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.exception.OutOfBoundsException;
import ch.bfh.sokobomb.model.coordinate.Coordinate;

/**
 * Draws a Title and a list of menu Items 
 * 
 * @author Christoph Bruderer
 *
 */
public class Menu implements Drawable {

	final public static int DOWN = 0;
	final public static int UP   = 1;

	private LinkedList<MenuItem> items;
	private MenuItem selectedItem;
	private AngelCodeFont font;
	private boolean selectable = true;
	final private String title;

	private int titleOffset = 10;

	public Menu(String title) {
		this.title        = title;
		this.selectedItem = null;
		this.items        = new LinkedList<MenuItem>();
		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * @param item Item to be added to the menu
	 */
	public void addMenuItem(MenuItem item) {
		items.add(item);

		if (this.selectedItem == null) {
			this.selectedItem = this.items.getFirst();
		}
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

			if (item == this.selectedItem && menuIterator.hasNext()) {
				this.selectedItem = menuIterator.next();
				return;
			}
		}
	}

	/**
	 * @return The currently selected item action
	 */
	public MenuItem getSelectedItem() {
		return this.selectedItem;
	}

	/**
	 * @param coord Returns the item at the coordinate
	 * @throws OutOfBoundsException 
	 */
	public MenuItem getItemAtPosition(Coordinate coord) throws OutOfBoundsException {
		for (MenuItem item : this.items){
			if (item.containsCoordinate(coord)) {	
				return item;
			}
		}

		throw new OutOfBoundsException("No item at mouse position");
	}

	@Override
	public void draw() throws IOException {
		int titleWidth  = font.getWidth(this.title);

		int x = (Display.getWidth() - titleWidth) / 2;
		int y = this.titleOffset;

		if (!this.title.equals("")) {
			this.font.drawString(x, y, this.title);
		}

		for (MenuItem item : items) {
			String text = item.getText();

			x = (Display.getWidth() - font.getWidth(text)) / 2;
			y += 1.5 * font.getHeight(text);

			item.setMinCoord(new Coordinate(x, y));
			item.setMaxCoord(new Coordinate(x + font.getWidth(text), y + font.getHeight(text)));

			if (item == this.selectedItem && this.selectable) {
				this.font.drawString(x, y, text, Color.yellow);
			}
			else {
				this.font.drawString(x, y, text);
			}
		}
	}

	/**
	 * Selects the item under the mouse cursor
	 *
	 * @param coord The coordinate of the mouse pointer 
	 */
	public void selectAtPosition(Coordinate coord) {
		for (MenuItem item : items){
			if (item.containsCoordinate(coord)) {
				this.selectedItem = item;
			}
		}
	}

	/**
	 * @param selectable Whether the menu items shall be selectable
	 */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * @return The title offset
	 */
	public int getTitleOffset() {
		return titleOffset;
	}

	/**
	 * @param titleOffset The title offset
	 */
	public void setTitleOffset(int titleOffset) {
		this.titleOffset = titleOffset;
	}
}