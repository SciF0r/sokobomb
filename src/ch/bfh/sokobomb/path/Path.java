package ch.bfh.sokobomb.path;

import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;

public class Path {

	private LinkedList<Coordinate> path;
	private Iterator<Coordinate> pathIterator;
	private Field field;

	public Path(Field field, Node startNode, Node targetNode) {
		this.field = field;

		this.calculatePath(startNode, targetNode);
	}

	/**
	 * Calculates the path (Dijkstra)
	 */
	private void calculatePath(Node startNode, Node targetNode) {
		Dijkstra dijkstra = new Dijkstra(this.field, startNode, targetNode);
		this.path         = dijkstra.getPath();
		if (this.path != null) {
			this.pathIterator = this.path.iterator();
		}
	}

	/**
	 * @return True if there is another coordinate
	 */
	public boolean hasNext() {
		return this.pathIterator != null && this.pathIterator.hasNext();
	}

	/**
	 * @return Iterator for the path
	 */
	public Coordinate next() {
		return this.pathIterator.next();
	}
}