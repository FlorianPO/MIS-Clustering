package projects.kcluster.nodes.nodeImplementations;

import java.util.Iterator;

import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;

public class BasicNode {

	/**
	 * Return the index in the outgoingConnections collection of the canal used
	 * by the Node aNode to communicate with the Node identified by aId. Return
	 * -1 if no direct canal exist
	 *
	 * @param aNode
	 *            Node
	 * @param aID
	 *            id
	 * @return the index of the canal to use in outgoingConnections or -1
	 */
	public static int getIndex(Node aNode, int aID) {
		int j = 0;
		for (Edge e : aNode.outgoingConnections) {
			if (e.endNode.ID == aID) {
				return j;
			}
			j++;
		}
		return -1;
	}

	/**
	 * Return the id of the Node directly reachable through the canal at the
	 * index aIndex in the outgoingConnections collection of the Node aNode. If
	 * the canal does not exist, return )1
	 *
	 * @param aNode
	 *            Node
	 * @param aIndex
	 *            index
	 * @return id of the found node or -1
	 */
	public static int getNeighbor(Node aNode, int aIndex) {
		if (aIndex >= aNode.outgoingConnections.size() || aIndex < 0) {
			return -1;
		}
		Iterator<Edge> iter = aNode.outgoingConnections.iterator();
		for (int j = 0; j < aIndex; j++) {
			iter.next();
		}
		return iter.next().endNode.ID;
	}

	/**
	 * Return the number of neighbors
	 *
	 * @param aNode
	 * @return Number of neighbors
	 */
	public static int nbNeighbors(Node aNode) {
		return aNode.outgoingConnections.size();
	}

}
