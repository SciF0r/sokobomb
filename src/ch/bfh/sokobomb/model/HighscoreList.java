package ch.bfh.sokobomb.model;

import java.util.ArrayList;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

/**
 * Draws a given highscore
 *
 * @author Denis Simonet
 */
public class HighscoreList implements Drawable {

	final private String highscoreTitle = "Highscore";
	final private String highscoreEmpty = "Empty list";

	private ArrayList<HighscoreItem> items;
	private AngelCodeFont font;
	@SuppressWarnings("unused")
	private int width, height;

	public HighscoreList(int width, int height, ArrayList<HighscoreItem> items) throws SlickException {
		this.items  = items;
		this.width  = width;
		this.height = height;

		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	}

	public void draw() {
		int titleWidth  = this.font.getWidth(this.highscoreTitle);
		int titleHeight = this.font.getHeight(this.highscoreTitle);

		int x = (this.width - titleWidth) / 2;
		int y = 0;

		this.font.drawString(x, y, this.highscoreTitle);

		if (this.items == null || this.items.isEmpty()) {
			int itemWidth  = this.font.getWidth(this.highscoreEmpty);
			int itemHeight = this.font.getHeight(this.highscoreEmpty);
			int itemX      = (this.width - itemWidth) / 2;
			int itemY      = titleHeight + itemHeight;

			this.font.drawString(itemX, itemY, this.highscoreEmpty);
		}
		else {
			int i = 0;
			for (HighscoreItem item: this.items) {
				int itemWidth  = this.font.getWidth(item.toString());
				int itemHeight = this.font.getHeight(item.toString());
				int itemX      = (this.width - itemWidth) / 2;
				int itemY      = titleHeight + itemHeight * i++;

				this.font.drawString(itemX, itemY, item.toString());
			}
		}
	}
}