package ch.bfh.sokobomb.util;

import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.exception.NoNextLevelException;

public class Levels {

	final public static String LEVEL1 = "res/levels/level1.txt";
	final public static String LEVEL2 = "res/levels/level2.txt";
	final public static String LEVEL3 = "res/levels/level3.txt";
	final public static String LEVEL4 = "res/levels/level4.txt";

	final private LinkedList<String> levelList;
	final private Iterator<String> levelListIterator;

	public Levels() {
		this.levelList = new LinkedList<String>();
		this.levelList.add(LEVEL1);
		this.levelList.add(LEVEL2);
		this.levelList.add(LEVEL3);
		this.levelList.add(LEVEL4);

		this.levelListIterator = this.levelList.iterator();
	}

	/**
	 * @return The next level, null if there is none
	 * @throws NoNextLevelException 
	 */
	public String getNextLevel() throws NoNextLevelException {
		if (this.levelListIterator.hasNext()) {
			return this.levelListIterator.next();
		}

		throw new NoNextLevelException("There is no new level");
	}
}