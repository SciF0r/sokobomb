package ch.bfh.sokobomb.solver;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Tile;


/**
 * A node in the field cache
 *
 * @author Denis Simonet
 *
 */
public class DijkstraNode extends Tile {

	private int cost;
	private DijkstraNode parent;
	private boolean permanent;

	public DijkstraNode(int type, TileCoordinate coordinate) {
		this.setType(type);
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
	public DijkstraNode getParent() {
		return parent;
	}

	/**
	 * @param parent The path parent to set
	 */
	public void setParent(DijkstraNode parent) {
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
	 * @return the coordinate
	 */
	public TileCoordinate getCoordinate() {
		return coordinate;
	}
}