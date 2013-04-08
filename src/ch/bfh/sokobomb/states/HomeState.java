package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;

public class HomeState extends State {
	private Menu homeMenu;

	public HomeState() {
		try {
			this.homeMenu = new Menu("!!WELCOME TO SOKOBOMB!!");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.homeMenu.addMenuItem(new MenuItem("start Game", MenuItem.START_GAME));
		this.homeMenu.addMenuItem(new MenuItem("Level Designer", MenuItem.DESIGN_MODE));
		this.homeMenu.addMenuItem(new MenuItem("exit Game", MenuItem.END_GAME));


		this.stateId = State.HOME;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
		case Keyboard.KEY_ESCAPE:
			System.exit(0);
			break;
		case Keyboard.KEY_DOWN:
			homeMenu.nextItem(Menu.DOWN);
			break;
		case Keyboard.KEY_UP:
			homeMenu.nextItem(Menu.UP);
			break;
		case Keyboard.KEY_RETURN:
			homeMenu.performAction(homeMenu.keyboardAction());
			break;

		}
	}

	@Override
	public void handleMouseOver(Coordinate coordinate){
		homeMenu.checkMousePosition(coordinate);
	}




	@Override
	public void handleLeftClick(Coordinate coordinate) {
		homeMenu.mouseAction(coordinate);

	}




	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		GL11.glColor4f(0.4f, 0.6f, 0.7f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0,                0                );
		GL11.glVertex2i(0,                Display.getHeight());
		GL11.glVertex2i(Display.getWidth(), Display.getHeight());
		GL11.glVertex2i(Display.getWidth(), 0                );
		GL11.glEnd();

		this.homeMenu.draw();
	}
}

