package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

/**
 * Stores and displays time
 *
 * @author Christoph Bruderer
 */
public class Time implements Drawable {

	private AngelCodeFont font;
	private long          time, initialTime;
	private Long          start;
	
	public Time(int time) {
		this.time        = time;
		this.initialTime = time;
	}

	@Override
	public void draw() throws IOException {
		if (this.font == null) {
			try {
				this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
			} catch (SlickException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		if (this.start == null) {
			this.start = System.currentTimeMillis() / 1000L;
		}

		this.time = this.initialTime + this.start - System.currentTimeMillis() / 1000L;

		int x = Display.getWidth() - this.font.getWidth(String.valueOf(this.time));
		int y = 0;

		this.font.drawString(x, y, String.valueOf(this.time));
	}
}