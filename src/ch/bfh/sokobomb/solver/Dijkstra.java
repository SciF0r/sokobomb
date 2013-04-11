package ch.bfh.sokobomb.solver;

import java.util.LinkedList;
import java.util.List;

import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.field.FieldCache;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * This class calculates the shortest path using Dijkstra algorithm
 *
 * @author Denis Simonet
 */
public class Dijkstra {

	private PlayField field;
	private DijkstraNode startNode, targetNode;

	public Dijkstra(PlayField field, DijkstraNode startNode, DijkstraNode targetNode) {
		this.field      = field;
		this.startNode  = startNode;
		this.targetNode = targetNode;
		this.field.getCache().reset();
	}

	/**
	 * Returns the best path (Dijkstra)
	 */
	public LinkedList<TileCoordinate> getPath() {
		this.startNode.setCost(0);

		// First calculate the node costs and cheapest parents
		DijkstraNode currentNode = this.startNode;
		do {
			this.dijkstraIteration(currentNode);
			currentNode = this.field.getCache().getTemporaryNodeWithLowestCost();
		}
		while (currentNode != null);

		// Read the shortest path, beginning at the end node
		LinkedList<TileCoordinate> path = new LinkedList<TileCoordinate>();
		currentNode = this.targetNode;
		path.addFirst(currentNode.getCoordinate());
		while (currentNode.getParent() != null && currentNode.getParent() != this.startNode) {
			currentNode = currentNode.getParent();
			path.addFirst(currentNode.getCoordinate());
		}

		// If the current node is not the start node there was no path
		if (currentNode.getParent() != this.startNode) {
			return null;
		}
		
		return path;
	}

	/**
	 * Does a Dijkstra iteration
	 *
	 * @param node
	 * @return The new lowest cost node
	 */
	private void dijkstraIteration(DijkstraNode node) {
		node.setPermanent(true);

		// Updates the cost and parent of each adjacent node
		for (DijkstraNode currentNode: this.getAdjacentNodes(node)) {
			if ((node.getCost() + 1) < currentNode.getCost()) {
				currentNode.setCost(node.getCost() + 1);
				currentNode.setParent(node);
			}
		}
	}

	/**
	 * Returns adjacent nodes to a node if player may enter
	 *
	 * @param node
	 * @param cache
	 * @return
	 */
	private List<DijkstraNode> getAdjacentNodes(DijkstraNode node) {
		LinkedList<DijkstraNode> nodeList = new LinkedList<DijkstraNode>();
		FieldCache cache                  = this.field.getCache();

		try {
			this.addNodeToList(
				cache.getNodeAtCoordinate(
					node.getCoordinate().getX() + 1,
					node.getCoordinate().getY()
				),
				nodeList
			);
		}
		catch (InvalidCoordinateException e) {
			// Ignore
		}

		try {
			this.addNodeToList(
				cache.getNodeAtCoordinate(
					node.getCoordinate().getX() - 1,
					node.getCoordinate().getY()
				),
				nodeList
			);
		}
		catch (InvalidCoordinateException e) {
			// Ignore
		}

		try {
			this.addNodeToList(
				cache.getNodeAtCoordinate(
					node.getCoordinate().getX(),
					node.getCoordinate().getY() + 1
				),
				nodeList
			);
		}
		catch (InvalidCoordinateException e) {
			// Ignore
		}

		try {
			this.addNodeToList(
				cache.getNodeAtCoordinate(
					node.getCoordinate().getX(),
					node.getCoordinate().getY() - 1
				),
				nodeList
			);
		}
		catch (InvalidCoordinateException e) {
			// Ignore
		}
	
		return nodeList;
	}

	/**
	 * Adds a given node to the node list if player may enter
	 *
	 * @param node
	 * @param nodeList
	 */
	private void addNodeToList(DijkstraNode node, LinkedList<DijkstraNode> nodeList) {
		boolean mayEnter = this.field.mayEnter(
			node.getCoordinate()
		);

		if (mayEnter && !node.isPermanent()) {
			nodeList.add(node);
		}
	}
}
