package ch.bfh.sokobomb.model;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class FieldItem {

	protected String image;
	protected int positionX;
	protected int positionY;

	/**
	 * The default implementation "beams" the field
	 *
	 * Must be overwritten for animation
	 *
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.positionX = x * 32;
		this.positionY = y * 32;
	}

	/**
	 * Draws the item
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.image));
		texture.bind();

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(this.positionX, this.positionY);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(this.positionX + texture.getTextureWidth(), this.positionY);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(this.positionX + texture.getTextureWidth(), this.positionY + texture.getTextureHeight());
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(this.positionX, this.positionY + texture.getTextureHeight());
		GL11.glEnd();
	}

	/**
	 * Sets a new graphics to be displayed
	 *
	 * @param path
	 */
	public void setImage(String path) {
		this.image = path;
	}
}