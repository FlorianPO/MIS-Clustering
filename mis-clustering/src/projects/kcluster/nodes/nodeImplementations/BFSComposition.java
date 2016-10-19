package projects.kcluster.nodes.nodeImplementations;

import projects.kcluster.nodes.messages.BFSMessage;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

public class BFSComposition implements IComposition {

	private Node pNode;
	private int[] pDistanceNeighbors;

	private int pDistance;
	/** Id of Parent node. 1 if root */
	private int pParent;

	private void actions() {
		if (rule1()) {
			this.pDistance = minimumDistanceNeighborD() + 1;
		} else if (rule2()) {
			for (int wI = 0; wI < pDistanceNeighbors.length; wI++) {
				if (pDistanceNeighbors[wI] == this.pDistance - 1) {
					this.pParent = BasicNode.getVoisin(pNode, wI);
					break;
				}
			}
		}
	}

	@Override
	public void handleMessage(Message aMessage) {
		// on parcourt la liste de tous les messages reçus
		if (aMessage instanceof BFSMessage) {
			BFSMessage wMessageBFS = (BFSMessage) aMessage;
			pDistanceNeighbors[BasicNode.getIndex(pNode, wMessageBFS.ID)] = wMessageBFS.distance;
			actions();
		}
	}

	private int minimumDistanceNeighbor() {
		if (pDistanceNeighbors.length > 0) {
			int wMin = pDistanceNeighbors[0];
			for (int wI = 1; wI < pDistanceNeighbors.length; wI++) {
				wMin = Math.min(wMin, pDistanceNeighbors[wI]);
			}
			return wMin;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private int minimumDistanceNeighborD() {
		return Math.min(minimumDistanceNeighbor(), Tools.getNodeList().size() - 1);
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	private boolean rule1() {
		return pDistance != minimumDistanceNeighborD() + 1;
	}

	private boolean rule2() {
		return (pDistance == minimumDistanceNeighbor() + 1)
				&& (pDistance != pDistanceNeighbors[BasicNode.getIndex(pNode, pParent)] + 1);
	}

	@Override
	public void send() {
		pNode.broadcast(new BFSMessage(pNode.ID, pDistance));
	}

	@Override
	public void start(Node aNode) {
		pNode = aNode;

		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		if (pNode.ID == 1) {
			pParent = 1;
			pDistance = 0;
		} else {
			int arc = (int) ((Math.random() * 10000) % wNumberNeighbors);
			pParent = BasicNode.getVoisin(pNode, arc);
			pDistance = Tools.getNodeList().size();
		}
		pDistanceNeighbors = new int[wNumberNeighbors];
	}

}
