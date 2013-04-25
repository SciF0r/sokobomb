package ch.bfh.sokobomb.solver;

import java.util.Iterator;
import java.util.LinkedList;

import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.DijkstraNode;

/**
 * Calculates a path from a start to a target node
 *
 * @author Denis Simonet
 */
public class Path {

	private LinkedList<TileCoordinate> path;
	private Iterator<TileCoordinate> pathIterator;
	private PlayField field;

	public Path(PlayField field, DijkstraNode startNode, DijkstraNode targetNode) {
		this.field = field;

		this.calculatePath(startNode, targetNode);
	}

	/**
	 * Calculates the path (Dijkstra)
	 */
	private void calculatePath(DijkstraNode startNode, DijkstraNode targetNode) {
		Dijkstra dijkstra = new Dijkstra(this.field, startNode, targetNode);

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
}