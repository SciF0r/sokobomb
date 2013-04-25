package ch.bfh.sokobomb.field;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.DijkstraNode;

/**
 * Caches the field
 *
 * @author Denis Simonet
 */
final public class FieldCache implements Cloneable {

	private HashMap<TileCoordinate, DijkstraNode> cache = new HashMap<TileCoordinate, DijkstraNode>();

	/**
	 * Resets the cache such that a new Dijkstra can be run
	 */
	public void reset() {
		for (Entry<TileCoordinate, DijkstraNode> entry: this.cache.entrySet()) {
			entry.getValue().reset();
		}
	}

	/**
	 * Clears the cache HashMap
	 */
	public void clear() {
		this.cache.clear();
	}

	/**
	 * Sets a node to a given coordinate
	 *
	 * @param coordinate
	 * @param node
	 */
	public void addNode(DijkstraNode node) {
		this.cache.put(node.getCoordinate(), node);
	}

	/**
	 * @param coordinate Coordinate to be emptied
	 */
	public void removeNode(TileCoordinate coordinate) {
		this.cache.remove(coordinate);
	}

	/**
	 * Returns a reference to the node at a certain coordinate
	 *
	 * @param coordinate
	 * @throws InvalidCoordinateException
	 * @return The node reference, null if invalid coordinate
	 */
	public DijkstraNode getNodeAtCoordinate(TileCoordinate coordinate) throws InvalidCoordinateException {
		DijkstraNode node = this.cache.get(coordinate);

		if (node == null) {
			throw new InvalidCoordinateException("This coordinate does not exist");
		}

		return node;
	}

	/**
	 * Returns a reference to the node at a certain coordinate
	 *
	 * @param coordinate
	 * @throws InvalidCoordinateException
	 * @return The node reference, null if invalid coordinate
	 */
	public DijkstraNode getNodeAtCoordinate(int x, int y) throws InvalidCoordinateException {
		TileCoordinate coordinate = new TileCoordinate(x, y);

		return this.getNodeAtCoordinate(coordinate);
	}

	/**
	 * Dijkstra needs this
	 *
	 * @return The current node with lowest cost
	 */
	public DijkstraNode getTemporaryNodeWithLowestCost() {

		DijkstraNode node = null;

		for (Entry<TileCoordinate, DijkstraNode> entry: this.cache.entrySet()) {
			DijkstraNode currentNode = entry.getValue();

			if (
				!currentNode.isPermanent()
				&& (
					node == null
					||
					node.getCost() > currentNode.getCost()
				)
			){
				node = currentNode;
			}
		}

		return node;
	}

	/**
	 * Draws the field
	 */
	public void draw() {
		for (Entry<TileCoordinate, DijkstraNode> entry: this.cache.entrySet()) {
			try {
				entry.getValue().draw();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	/**
	 * Clone the field including tiles
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		FieldCache fieldCache = (FieldCache)super.clone();
		fieldCache.cache = (HashMap<TileCoordinate, DijkstraNode>)this.cache.clone();

		return fieldCache;
	}
}