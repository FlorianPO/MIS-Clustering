package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.models.compositions.MISTData;
import projects.kcluster.models.compositions.MISTKey;
import projects.kcluster.nodes.messages.MISTMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import projects.kcluster.nodes.random.Random;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class MISTComposition implements IComposition {

	private Node pNode;

	private BFSData pBFSData;
	private MISTData pMISTData;

	public MISTComposition(Node aNode, BFSData aBFSData, MISTData aMISTData) {
		pNode = aNode;
		pBFSData = aBFSData;
		pMISTData = aMISTData;
	}

	private void actions() {
		// No action for Node 1
		if (pNode.ID != 1) {
			if (ruleSetDominator()) {
				pMISTData.dominator = macroDominator();
			} else if (ruleSetParentMIST()) {
				pMISTData.parent = macroParent();
			}
		}
	}

	@Override
	public void handleMessage(Message aMessage) {
		if (aMessage instanceof MISTMessage) {
			MISTMessage wMessageMIST = (MISTMessage) aMessage;
			// Update known data with received one
			int index = BasicNode.getIndex(pNode, wMessageMIST.ID);
			pMISTData.neighborsDominator[index] = wMessageMIST.dominator;
			pMISTData.neighborsKey[index] = wMessageMIST.key;
			actions();
		}
	}

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

	private MISTKey macroKey() {
		return new MISTKey(pNode.ID, pBFSData.distance);
	}

	private int macroParent() {
		if (pBFSData.distance == 0) {
			return pNode.ID;
		}
		int wMinIndex = -1;
		MISTKey wMinKey = new MISTKey(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (int wI = 0; wI < pMISTData.neighborsKey.length; wI++) {
			if (this.pMISTData.dominator != pMISTData.neighborsDominator[wI]) {
				if (wMinKey.compareTo(pMISTData.neighborsKey[wI]) == 1) {
					wMinIndex = wI;
					wMinKey = pMISTData.neighborsKey[wI];
				}
			}
		}

		return BasicNode.getVoisin(pNode, wMinIndex);
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	private boolean ruleSetDominator() {
		if (pMISTData.dominator != macroDominator()) {
			return true;
		}
		return false;
	}

	private boolean ruleSetParentMIST() {
		if (pMISTData.dominator == macroDominator() && pMISTData.parent != macroParent()) {
			return true;
		}
		return false;
	}

	@Override
	public void send() {
		pNode.broadcast(new MISTMessage(pNode.ID, pMISTData.dominator, macroKey()));
	}

	@Override
	public void start() {
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		if (pNode.ID == 1) {
			pBFSData.parent = 1;
			pMISTData.dominator = true;
		} else {
			int arc = Random.rand(wNumberNeighbors);
			pMISTData.parent = BasicNode.getVoisin(pNode, arc);
			pMISTData.dominator = (Random.rand(2) == 1) ? false : true;
		}
		pMISTData.neighborsDominator = new boolean[wNumberNeighbors];
		pMISTData.neighborsKey = new MISTKey[wNumberNeighbors];
		for (int wI = 0; wI < pMISTData.neighborsKey.length; wI++) {
			pMISTData.neighborsKey[wI] = new MISTKey(Random.rand(Integer.MAX_VALUE), Random.rand(Integer.MAX_VALUE));
		}
	}

}
