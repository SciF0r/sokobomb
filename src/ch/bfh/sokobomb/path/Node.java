package ch.bfh.sokobomb.path;

import ch.bfh.sokobomb.model.Coordinate;

public class Node {

	final private int type;
	final private Coordinate coordinate;
	private int cost;
	private Node parent;
	private boolean permanent;

	public Node(int type, Coordinate coordinate) {
		this.type       = type;
		this.coordinate = coordinate;

		this.reset();
	}

	/**
	 * Resets the node
	 */
	public void reset() {
		this.cost       = Integer.MAX_VALUE;
		this.parent     = null;
		this.permanent  = false;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost The cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @return the path parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param parent The path parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @return Whether this node is permanent
	 */
	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * @param permanent The permanent state to set
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}
}