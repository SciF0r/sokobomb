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

	protected String imagePath  = null;
	protected Texture texture   = null;
	protected Integer tokenType = null;
	protected boolean isMoving  = false;
	protected Coordinate coordinate;

	/**
	 * This "beams" the field to a new position
	 *
	 * @param coordinate
	 */
	public void setPosition(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @return Position x
	 */
	public int getPositionX() {
		return this.coordinate.getX();
	}

	/**
	 * @return Position y
	 */
	public int getPositionY() {
		return this.coordinate.getY();
	}

	/**
	 * @return The current coordinate
	 */
	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	/**
	 * Draws the item 
	 *
	 * @throws IOException When the texture could not be initialized
	 */
	public void draw() throws IOException {
		int x = this.coordinate.getX() * Tiles.WIDTH;
		int y = this.coordinate.getY() * Tiles.HEIGHT;
		
		if (this.texture == null) {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.imagePath));
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
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
		this.texture   = null;
	}
}