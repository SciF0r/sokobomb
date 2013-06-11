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
			this.fieldHistory.push(
				new FieldHistoryItem(
					null,
					null,
					(FieldCache)this.getCache().clone()
				)
			);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		DesignField field = (DesignField)super.clone();

		field.cache = (FieldCache)this.cache.clone();
		return field;
	}

	@Override
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			FieldHistoryItem historyItem = this.fieldHistory.pop();
			this.cache = historyItem.getCache();
		}
	}
}