package ch.bfh.sokobomb.model;

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
		this.positionX = x;
		this.positionY = y;
	}

	/**
	 * Draws the item
	 */
	public void draw() {
		// TODO add code to draw
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