package ch.bfh.sokobomb.util;

import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.path.Node;

final public class FieldCache {

	private Node[][] cache;
	private Field field;

	public FieldCache(Field field, int width, int height) {
		this.field = field;
		this.cache = new Node[width][height];

		this.buildCache();
	}

	/**
	 * Builds the cache
	 */
	private void buildCache() {
		for (FieldItem item: this.field.getItems()) {
			this.setNodeAtCoordinate(
				item.getCoordinate(),
				new Node(item.getType(), item.getCoordinate())
			);
		}
	}

	/**
	 * Resets to cache such that a new Dijkstra can be run
	 */
	public void reset() {
		for (int x = 0; x < this.cache.length; x++) {
			for (int y = 0; y < this.cache[x].length; y++) {
				try {
					this.getNodeAtCoordinate(x, y).reset();
				}
				catch (InvalidCoordinateException e) {
					// Ignore
				}
			}
		}
	}

	/**
	 * Sets a node to a given coordinate
	 *
	 * @param coordinate
	 * @param node
	 */
	private void setNodeAtCoordinate(Coordinate coordinate, Node node) {
		this.cache[coordinate.getX()][coordinate.getY()] = node;
	}

	/**
	 * Returns the tile type at given coordinate
	 *
	 * @param coordinate
	 * @return The tile type
	 */
	public int getTypeAtCoordinate(Coordinate coordinate) {
		if (this.cache.length <= coordinate.getX() || this.cache[coordinate.getX()].length <= coordinate.getY() || coordinate.getX() < 1 || coordinate.getY() < 1) {
			throw new InvalidCoordinateException("Coordinate is outside of play field");
		}

		try {
			int type = this.cache[coordinate.getX()][coordinate.getY()].getType();

			if (this.field.findBomb(coordinate) != null) {
				throw new InvalidCoordinateException("Cannot go onto a bomb");
			}

			return type;

		}
		catch (NullPointerException e) {
			throw new InvalidCoordinateException("Coordinate is not reachable");
		}
	}

	/**
	 * Returns a reference to the node at a certain coordinate
	 *
	 * @param coordinate
	 * @throws InvalidCoordinateException
	 * @return The node reference, null if invalid coordinate
	 */
	public Node getNodeAtCoordinate(Coordinate coordinate) {
		if (!this.field.mayEnter(coordinate)) {
			throw new InvalidCoordinateException("Player may not enter this coordinate");
		}

		return this.cache[coordinate.getX()][coordinate.getY()];
	}

	/**
	 * Returns a reference to the node at a certain coordinate
	 *
	 * @param coordinate
	 * @throws InvalidCoordinateException
	 * @return The node reference, null if invalid coordinate
	 */
	public Node getNodeAtCoordinate(int x, int y) {
		Coordinate coordinate = new Coordinate(x, y);

		if (!this.field.mayEnter(coordinate)) {
			throw new InvalidCoordinateException("Player may not enter this coordinate");
		}

		return this.cache[coordinate.getX()][coordinate.getY()];
	}

	/**
	 * @return The current node with lowest cost
	 */
	public Node getTemporaryNodeWithLowestCost() {
		Node node = null;
		for (int x = 0; x < this.cache.length; x++) {
			for (int y = 0; y < this.cache[x].length; y++) {
				try {
					Node currentNode = this.getNodeAtCoordinate(x, y);
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
				catch (InvalidCoordinateException e) {
					// Ignore
				}
			}
		}

		return node;
	}
}