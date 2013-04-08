package ch.bfh.sokobomb.field;

import java.util.LinkedList;

import ch.bfh.sokobomb.model.tiles.Tile;


/**
 * Contains all required information to draw a field and handle input
 *
 * @author Denis Simonet
 */
public class DesignField extends Field implements Cloneable {

	@Override
	public void addFieldToHistory() {
		try {
			this.fieldHistory.push((DesignField)this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clone bombs and player
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		DesignField field = (DesignField)super.clone();

		field.cache = (FieldCache)this.cache.clone();
		return field;
	}

	@Override
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			DesignField field = (DesignField)this.fieldHistory.pop();
			this.cache  = field.getCache();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
		}
	}
}