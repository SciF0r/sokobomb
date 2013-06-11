package ch.bfh.sokobomb.solver;

import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * Stores a path
 *
 * @author Denis Simonet
 */
public class Path {

	private LinkedList<TileCoordinate> path;
	private Iterator<TileCoordinate> pathIterator;
	private PlayField field;

	public Path(PlayField field, TileCoordinate start, TileCoordinate target) throws InvalidCoordinateException {
		this.field = field;

		this.calculatePath(start, target);
	}

	/**
	 * Calculates the path (Dijkstra)
	 * @throws InvalidCoordinateException 
	 */
	private void calculatePath(TileCoordinate start, TileCoordinate target) throws InvalidCoordinateException {
		Dijkstra dijkstra = new Dijkstra(this.field, start, target);

		this.path = dijkstra.getPath();
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
	public TileCoordinate next() {
		return this.pathIterator.next();
	}

	/**
	 * @return the path
	 */
	public LinkedList<TileCoordinate> getPath() {
		return path;
	}
}