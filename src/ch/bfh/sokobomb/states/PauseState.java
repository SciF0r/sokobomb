package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.util.Tiles;

public class PauseState extends State {

	private int xmin, xmax, ymin, ymax;
	
	public PauseState(Field field) {
		super(field);
	}
	
	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RIGHT:
				super.field.movePlayer(1, 0);
				break;
		}
	}
	
	@Override
	public void handleLeftClick(int x, int y) {
		System.out.println(x +" "+y);
		if (xmin < x && xmax > x && ymin < y && ymax > y ){
			super.field.setState(new PlayState(field));
		}
	}
	
	public void draw() throws IOException{
		
		for (FieldItem item: super.field.getItems()) {
			item.draw();
		}

		for (Bomb bomb: super.field.getBombs()) {
			bomb.draw();
		}

		super.field.getPlayer().draw();
		
		//Draw Background
		GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.6f);		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0,0);
		GL11.glVertex2i(0,field.getHeight());
		GL11.glVertex2i(field.getWidth(), field.getHeight());
		GL11.glVertex2i(field.getWidth(),0);
		GL11.glEnd();
		
		//Draw Button
		xmin=50;
		ymin=70;
		xmax=150;
		ymax=170;
		GL11.glColor3f(1.0f, 0.0f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(xmin,ymin);
		GL11.glVertex2i(xmin,ymax);
		GL11.glVertex2i(xmax,ymax);
		GL11.glVertex2i(xmax,ymin);
		GL11.glEnd();
		
		Display.update();

		super.pollInput();
	}
	
	

}
