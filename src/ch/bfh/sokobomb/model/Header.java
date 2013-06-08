package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

public class Header implements Drawable {
	private AngelCodeFont font;
	private String level;
	private int time;
	public Header(String level, int time){
		this.level = level;
		this.time = time;
		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void draw() throws IOException {
			int x = 10;//(Display.getWidth() - this.font.getWidth(this.level)) / 2;
			int y = 0;
			
			this.font.drawString(x, y, this.level);
		
			 x = Display.getWidth() - this.font.getWidth(String.valueOf(this.time));
			 y = 0;
			
			this.font.drawString(x, y, String.valueOf(this.time));
		
	}

}
