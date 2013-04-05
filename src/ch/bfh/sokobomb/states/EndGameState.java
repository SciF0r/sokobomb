package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.model.Field;

/**
 * Shows that the game has ended
 *
 * @author Denis Simonet
 *
 */
public class EndGameState extends State {

	final private String textEndGame = "Finished";
	final private AngelCodeFont font;

	public EndGameState(Field field) throws SlickException {
		super(field);

		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RETURN:
				super.field.startGame();
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

		int titleWidth  = this.font.getWidth(this.textEndGame);
		int titleHeight = this.font.getHeight(this.textEndGame);

		int x = (super.field.getWidth()  - titleWidth) / 2;
		int y = (super.field.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textEndGame);
	}
}