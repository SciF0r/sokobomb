package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

public class Time implements Drawable{
	private AngelCodeFont font;
	private int time;
	
	public Time(int time){
	this.time=time;
	try {
		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	} catch (SlickException e) {
		e.printStackTrace();
		System.exit(0);
	}
	}

	@Override
	public void draw() throws IOException {
		 int x = Display.getWidth() - this.font.getWidth(String.valueOf(this.time));
		 int y = 0;
		
		this.font.drawString(x, y, String.valueOf(this.time));
		
	}

}
