package ch.bfh.sokobomb.model;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;


public class MenuButton implements Drawable {

	private String text;
	
	private int x,y, width, height;
	
	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public MenuButton(int x, int y, int width, int height, String text){
		this.text =text;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public void draw(){
		GL11.glColor3f(0.5f, 0.9f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(x,y);
		GL11.glVertex2i(x,y+height);
		GL11.glVertex2i(x+width,y+height);
		GL11.glVertex2i(x+width,y);
		GL11.glEnd();
		
		AngelCodeFont fnt=null;
		try {
			fnt = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fnt.drawString(x+20, y+20, text);
		
	}
	
	
	

}
