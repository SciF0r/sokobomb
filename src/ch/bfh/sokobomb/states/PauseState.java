package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.model.MenuButton;

public class PauseState extends State {

	private MenuButton mb;
	
	public PauseState(Field field) {
		super(field);
		mb = new MenuButton(50, 70, 300, 80, "Return to the Game");
		
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
	public void handleLeftClick(Coordinate coordinate) {
		int x=coordinate.getX();
		int y=coordinate.getY();
		if (mb.getX() < x && mb.getX()+mb.getWidth() > x && mb.getY() < y && mb.getY() + mb.getHeight() > y ){
			super.field.setState(new PlayState(field));
		}
	}
	
	@Override
	public void pollInput() {
		Keyboard.enableRepeatEvents(true);

		// Left mouse button
		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = (this.field.getHeight() - Mouse.getY());
			System.out.println(x +" "+ y);
			this.handleLeftClick(new Coordinate(x, y));
		}

		// Right mouse button
		if (Mouse.isButtonDown(1)) {
			int x = Mouse.getX();
			int y = (this.field.getHeight() - Mouse.getY());
			this.handleRightClick(new Coordinate(x, y));
		}

		while (Keyboard.next()) {
			// Key was pressed
			if (Keyboard.getEventKeyState()) {
				this.handleKeyPress(Keyboard.getEventKey());
			}
			// Key was released
			else {
				this.handleKeyRelease(Keyboard.getEventKey());
			}
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
		GL11.glColor4f(0.5f, 0.70f, 0.5f, 0.6f);		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0,0);
		GL11.glVertex2i(0,field.getHeight());
		GL11.glVertex2i(field.getWidth(), field.getHeight());
		GL11.glVertex2i(field.getWidth(),0);
		GL11.glEnd();
		 
		mb.draw();
		Display.update();

		this.pollInput();
	}
	
	

}
