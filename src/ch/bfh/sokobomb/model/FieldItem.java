package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import ch.bfh.sokobomb.util.Tiles;

/**
 * A field item (Bomb, Floor, ...)
 *
 * @author Denis Simonet
 */
public abstract class FieldItem {

	protected String  path    = null;
	protected Texture texture = null;
	protected int     positionX;
	protected int     positionY;
	protected int     tokenType;

	/**
	 * This "beams" the field to a new position
	 *
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}

	/**
	 * @return Position x
	 */
	public int getPositionX() {
		return this.positionX;
	}

	/**
	 * @return Position y
	 */
	public int getPositionY() {
		return this.positionY;
	}

	/**
	 * Draws the item 
	 *
	 * @throws IOException When the texture could not be initialized
	 */
	public void draw() throws IOException {
		int x = this.positionX * Tiles.WIDTH;
		int y = this.positionY * Tiles.HEIGHT;
		
		if (this.texture == null) {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.path));
		}

		Color.white.bind();
		this.texture.bind();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + texture.getTextureWidth(), y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + texture.getTextureWidth(), y + texture.getTextureHeight());
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + texture.getTextureHeight());
		GL11.glEnd();
	}

	/**
	 * Sets a new graphics to be displayed
	 *
	 * @param path
	 */
	public void setImage(String path) {
		this.path    = path;
		this.texture = null;
	}
}