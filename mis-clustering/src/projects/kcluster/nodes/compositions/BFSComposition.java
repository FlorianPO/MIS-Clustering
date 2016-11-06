package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.nodes.logger.Logger;
import projects.kcluster.nodes.messages.BFSMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import projects.kcluster.nodes.random.Random;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

/**
 * Composition to create a BFS Tree
 *
 */
public class BFSComposition implements IComposition {
	/* Logger */
	private static Logger<Node, BFSMessage> LOGGER = new Logger<>("BFSTLogger", false);

	/** Node on which this composition is attached */
	private Node pNode;

	/** BFS Data manipulated */
	private BFSData pBFSData;

	/**
	 *
	 * @param aNode
	 *            Node on which the composition is attached
	 * @param aBFSData
	 *            Data to manipulate. This object will be modified by this class
	 *
	 */
	public BFSComposition(Node aNode, BFSData aBFSData) {
		pNode = aNode;
		pBFSData = aBFSData;
	}

	/**
	 * Actions of the composition. Function must be called every time a message
	 * is received
	 */
	private void actions() {
		// No action for Node 1
		if (pNode.ID == 1) {
			return;
		}

		if (rule1()) {
			pBFSData.distance = minimumDistanceNeighborD() + 1;
		} else if (rule2()) {
			for (int wI = 0; wI < pBFSData.distanceNeighbors.length; wI++) {
				if (pBFSData.distanceNeighbors[wI] == pBFSData.distance - 1) {
					pBFSData.parent = BasicNode.getNeighbor(pNode, wI);
					break;
				}
			}

		}
	}

	@Override
	public void handleMessage(Message aMessage) {
		// Check the type of the message
		if (aMessage instanceof BFSMessage) {
			BFSMessage wMessageBFS = (BFSMessage) aMessage;
			LOGGER.logReceive(pNode, wMessageBFS);

			// Update known data with received one
			pBFSData.distanceNeighbors[BasicNode.getIndex(pNode, wMessageBFS.ID)] = wMessageBFS.distance;

			// update Node state through call to actions()
			actions();
		}
	}

	/**
	 * Return the minimum of the neighbors' distance to the root node in the BFS
	 * tree
	 *
	 * @return minimum value of neighbors' distance
	 */
	private int minimumDistanceNeighbor() {
		if (pBFSData.distanceNeighbors.length > 0) {
			int wMin = pBFSData.distanceNeighbors[0];
			for (int wI = 1; wI < pBFSData.distanceNeighbors.length; wI++) {
				wMin = Math.min(wMin, pBFSData.distanceNeighbors[wI]);
			}
			return wMin;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * Return the minimum of the neighbors' distance to the root node in the BFS
	 * tree. The minimum will never be greater than D (number of nodes in graph)
	 *
	 * @return minimum value of neighbors' distance. The value is < to D
	 */
	private int minimumDistanceNeighborD() {
		return Math.min(minimumDistanceNeighbor(), Tools.getNodeList().size() - 1);
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	/**
	 * Guard of rule rule1 of the BFS composition
	 *
	 * @return true if rule is enabled. false otherwise
	 */
	private boolean rule1() {
		return pBFSData.distance != minimumDistanceNeighborD() + 1;
	}

	/**
	 * Guard of rule1 of the BFS composition
	 *
	 * @return true if rule is enabled. false otherwise
	 */
	private boolean rule2() {
		return (pBFSData.distance == minimumDistanceNeighbor() + 1)
				&& (pBFSData.distance != pBFSData.distanceNeighbors[BasicNode.getIndex(pNode, pBFSData.parent)] + 1);
	}

	@Override
	public void send() {
		BFSMessage wMessageBFS = new BFSMessage(pNode.ID, pBFSData.distance);
		LOGGER.logSend(pNode, wMessageBFS);
		pNode.broadcast(wMessageBFS);
	}

	@Override
	public void start() {
		// Except for the root Node, Nodes are initialized with random data
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		int wDistMax = Tools.getNodeList().size() - 1;
		if (pNode.ID == 1) {
			// if node is root in BFS Tree
			pBFSData.parent = 1;
			pBFSData.distance = 0;
		} else {
			// if node is not root
			int arc = Random.rand(wNumberNeighbors);
			pBFSData.parent = BasicNode.getNeighbor(pNode, arc);
			pBFSData.distance = Random.rand(wDistMax) + 1;
		}
		/* Distances of neighbors are set randomly for every nodes */
		pBFSData.distanceNeighbors = new int[wNumberNeighbors];
		for (int wI = 0; wI < wNumberNeighbors; wI++) {
			pBFSData.distanceNeighbors[wI] = Random.rand(wDistMax) + 1;
		}
	}

}
