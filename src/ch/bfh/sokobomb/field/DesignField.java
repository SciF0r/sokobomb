package ch.bfh.sokobomb.field;

import java.util.LinkedList;

import ch.bfh.sokobomb.model.FieldItem;


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

		LinkedList<FieldItem> items = new LinkedList<FieldItem>();
		for (FieldItem item: this.items) {
			items.add((FieldItem)item.clone());
		}
		field.items = items;

		return field;
	}

	@Override
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			DesignField field = (DesignField)this.fieldHistory.pop();
			this.items  = field.getItems();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
		}
	}
}