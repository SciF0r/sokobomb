package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

/**
 * Draws the header
 *
 * Time left, title of the current level
 *
 * @author Christoph Bruderer
 */
public class Header implements Drawable {
	private AngelCodeFont font;
	private String level;
	private Time time;

	/**
	 * @param level The level name
	 * @param time  A Time object
	 */
	public Header(String level, Time time){
		this.level = level;
		this.time  = time;

		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Time getTime(){
		return this.time;
	}

	@Override
	public void draw() throws IOException {
			int x = 10;
			int y = 0;
			
			this.font.drawString(x, y, this.level);
		
			this.time.draw();
	}
}
