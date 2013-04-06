package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;
/**
 * UNDER CONSTRUCTION!!!
 * 
 * Draws a Title and a list of menu Items 
 * 
 * @author Christoph Bruderer
 *
 */
public class Menu implements Drawable{
	private int width,height;
	private LinkedList<MenuItem> items;
	private AngelCodeFont font;
	final private String title;
	private String empty = "EMPTY";
	
	public Menu(String title, LinkedList<MenuItem> items) throws SlickException{
		this.title = title;
		this.items = items;
		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		this.width = Display.getWidth();
		this.height = Display.getHeight();
	}

	
	@Override
	public void draw() throws IOException {
		int titleWidth = font.getWidth(this.title);
		int titleHeight = font.getHeight(this.title);
		
		int x = (this.width - titleWidth) / 2;
		int y = 0;

		this.font.drawString(x, y, this.title);
	}
}
