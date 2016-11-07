package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSTData;
import projects.kcluster.models.compositions.MISTData;
import projects.kcluster.models.compositions.MISTData.MISTKey;
import projects.kcluster.nodes.logger.Logger;
import projects.kcluster.nodes.messages.MISTMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import projects.kcluster.nodes.random.Random;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

/**
 * Composition to create a MIS Tree
 *
 */
public class MISTComposition implements IComposition {
	/* Logger */
	private static Logger<Node, MISTMessage> LOGGER = new Logger<>("MISTLogger", false);

	/** Node on which this composition is attached */
	private Node pNode;

	/** BFS Data handed to composition (must be read only) */
	private BFSTData pBFSData;
	/** MIST Data manipulated */
	private MISTData pMISTData;

	/**
	 *
	 * @param aNode
	 *            Node on which the composition is attached
	 * @param aBFSData
	 *            Data handed to compositon. This composition does not modify
	 *            the data contained in that object
	 * @param aMISTData
	 *            Data to manipulate. This object will be modified by this class
	 *
	 */
	public MISTComposition(Node aNode, BFSTData aBFSData, MISTData aMISTData) {
		pNode = aNode;
		pBFSData = aBFSData;
		pMISTData = aMISTData;
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

		if (ruleSetDominator()) {
			pMISTData.dominator = macroDominator();
		} else if (ruleSetParentMIST()) {
			pMISTData.parent = macroParent();
		}

	}

	@Override
	public void handleMessage(Message aMessage) {
		// Check the type of the message
		if (aMessage instanceof MISTMessage) {
			MISTMessage wMessageMIST = (MISTMessage) aMessage;
			LOGGER.logReceive(pNode, wMessageMIST);

			// Update known data with received one
			int index = BasicNode.getIndex(pNode, wMessageMIST.ID);
			pMISTData.neighborsDominator[index] = wMessageMIST.dominator;
			pMISTData.neighborsKey[index] = wMessageMIST.key;

			// update Node state through call to actions()
			actions();
		}
	}

	/**
	 * Using neighbors Data, will return true if the node is Dominator
	 *
	 * @return true if Node is dominator according to known neighbors data.
	 *         false otherwise
	 */
	private boolean macroDominator() {
		/* Looking for a dominator of Key inferior to pNode */
		MISTKey wKey = macroKey();
		for (int wI = 0; wI < pMISTData.neighborsDominator.length; wI++) {
			if (pMISTData.neighborsDominator[wI] == true && pMISTData.neighborsKey[wI].compareTo(wKey) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Create a MIS tree Key of Node
	 *
	 * @return MIS tree Key
	 */
	private MISTKey macroKey() {
		return new MISTKey(pNode.ID, pBFSData.distance);
	}

	/**
	 * Return this ID of the parent Node in the MIS tree. The Node is found by
	 * using the informations known on Neighbors.
	 *
	 * @return ID of Parent Node in MIS Tree
	 */
	private int macroParent() {
		if (pBFSData.distance == 0) {
			return pNode.ID;
		}
		int wMinIndex = 0;
		MISTKey wMinKey = pMISTData.neighborsKey[0];
		for (int wI = 1; wI < pMISTData.neighborsKey.length; wI++) {
			if (this.pMISTData.dominator != pMISTData.neighborsDominator[wI]) {
				if (wMinKey.compareTo(pMISTData.neighborsKey[wI]) == 1) {
					wMinIndex = wI;
					wMinKey = pMISTData.neighborsKey[wI];
				}
			}
		}
		return BasicNode.getNeighbor(pNode, wMinIndex);
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	/**
	 * Guard of rule setDominator of the MIS tree Composition
	 *
	 * @return true if rule is enabled. false otherwise
	 */
	private boolean ruleSetDominator() {
		if (pMISTData.dominator != macroDominator()) {
			return true;
		}
		return false;
	}

	/**
	 * Guard of rule setParent of the MIS tree Composition
	 *
	 * @return true if rule is enabled. false otherwise
	 */
	private boolean ruleSetParentMIST() {
		if (pMISTData.dominator == macroDominator() && pMISTData.parent != macroParent()) {
			return true;
		}
		return false;
	}

	@Override
	public void send() {
		MISTMessage wMessageMIST = new MISTMessage(pNode.ID, pMISTData.dominator, macroKey());
		LOGGER.logSend(pNode, wMessageMIST);
		pNode.broadcast(wMessageMIST);
	}

	@Override
	public void start() {
		// Except for the root Node, Nodes are initialized with random data
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		int wNbNodes = Tools.getNodeList().size();
		if (pNode.ID == 1) {
			pBFSData.parent = 1;
			pMISTData.dominator = true;
		} else {
			int arc = Random.rand(wNumberNeighbors);
			pMISTData.parent = BasicNode.getNeighbor(pNode, arc);
			pMISTData.dominator = (Random.rand(2) == 1) ? false : true;
		}

		pMISTData.neighborsDominator = new boolean[wNumberNeighbors];
		pMISTData.neighborsKey = new MISTKey[wNumberNeighbors];
		for (int wI = 0; wI < pMISTData.neighborsKey.length; wI++) {
			pMISTData.neighborsDominator[wI] = (Random.rand(2) == 1) ? false : true;
			pMISTData.neighborsKey[wI] = new MISTKey(Random.rand(wNbNodes) + 1, Random.rand(wNbNodes - 1) + 1);
		}
	}

}
