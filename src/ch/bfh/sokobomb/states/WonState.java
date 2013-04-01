package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.model.Field;

/**
 * Shows that the player has won
 *
 * @author Denis Simonet
 *
 */
public class WonState extends State {

	final private String textWon = "You win!";
	private AngelCodeFont font;

	public WonState(Field field) throws SlickException {
		super(field);

		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RETURN:
				super.field.restart();
				super.field.setState(new PlayState(super.field));
				break;
		}
	}

	@Override
	public void draw() throws IOException {
		super.field.drawField();

		//Draw Background
		GL11.glColor4f(0.5f, 0.70f, 0.5f, 0.6f);		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(0               , 0                );
		GL11.glVertex2i(0               , field.getHeight());
		GL11.glVertex2i(field.getWidth(), field.getHeight());
		GL11.glVertex2i(field.getWidth(), 0                );
		GL11.glEnd();

		int titleWidth  = this.font.getWidth(this.textWon);
		int titleHeight = this.font.getHeight(this.textWon);

		int x = (super.field.getWidth()  - titleWidth) / 2;
		int y = (super.field.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textWon);
	}
}