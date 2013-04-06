package ch.bfh.sokobomb.field;


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
		return (DesignField)super.clone();
	}

	/**
	 * Undo the last move
	 */
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			DesignField field = (DesignField)this.fieldHistory.pop();
			this.items  = field.getItems();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
		}
	}
}