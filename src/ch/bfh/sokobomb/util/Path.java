package ch.bfh.sokobomb.util;

import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;

public class Path {

	private LinkedList<Coordinate> path;
	private Iterator<Coordinate> pathIterator;
	private Field field;
	private Coordinate oldCoordinate, newCoordinate;

	public Path(Coordinate oldCoordinate, Coordinate newCoordinate, Field field) {
		this.oldCoordinate = oldCoordinate;
		this.newCoordinate = newCoordinate;
		this.field         = field;
		this.calculatePath();
	}

	/**
	 * Calculates the path (A*)
	 */
	private void calculatePath() {
		this.path = new LinkedList<Coordinate>();
		// TODO Implement A*
		this.path.add(this.newCoordinate);
		this.pathIterator = this.path.iterator();
	}

	/**
	 * @return True if there is another coordinate
	 */
	public boolean hasNext() {
		return this.pathIterator.hasNext();
	}

	/**
	 * @return Iterator for the path
	 */
	public Coordinate next() {
		return this.pathIterator.next();
	}
}