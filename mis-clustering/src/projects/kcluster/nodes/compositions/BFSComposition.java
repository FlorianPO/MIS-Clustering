package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.nodes.messages.BFSMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

public class BFSComposition implements IComposition {

	private Node pNode;

	private BFSData pBFSData;

	public BFSComposition(Node aNode, BFSData aBFSData) {
		pNode = aNode;
		pBFSData = aBFSData;
	}

	private void actions() {
		// No action for Node 1
		if (pNode.ID != 1) {
			if (rule1()) {
				pBFSData.distance = minimumDistanceNeighborD() + 1;
			} else if (rule2()) {
				for (int wI = 0; wI < pBFSData.distanceNeighbors.length; wI++) {
					if (pBFSData.distanceNeighbors[wI] == pBFSData.distance - 1) {
						pBFSData.parent = BasicNode.getVoisin(pNode, wI);
						break;
					}
				}
			}
		}
	}

	@Override
	public void handleMessage(Message aMessage) {
		// on parcourt la liste de tous les messages reçus
		if (aMessage instanceof BFSMessage) {
			BFSMessage wMessageBFS = (BFSMessage) aMessage;
			pBFSData.distanceNeighbors[BasicNode.getIndex(pNode, wMessageBFS.ID)] = wMessageBFS.distance;
			actions();
		}
	}

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

	private int minimumDistanceNeighborD() {
		return Math.min(minimumDistanceNeighbor(), Tools.getNodeList().size() - 1);
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	private boolean rule1() {
		return pBFSData.distance != minimumDistanceNeighborD() + 1;
	}

	private boolean rule2() {
		return (pBFSData.distance == minimumDistanceNeighbor() + 1)
				&& (pBFSData.distance != pBFSData.distanceNeighbors[BasicNode.getIndex(pNode, pBFSData.parent)] + 1);
	}

	@Override
	public void send() {
		pNode.broadcast(new BFSMessage(pNode.ID, pBFSData.distance));
	}

	@Override
	public void start() {
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		if (pNode.ID == 1) {
			pBFSData.parent = 0;
			pBFSData.distance = 0;
		} else {
			int arc = (int) ((Math.random() * 10000) % wNumberNeighbors);
			pBFSData.parent = BasicNode.getVoisin(pNode, arc);
			pBFSData.distance = Tools.getNodeList().size();
		}
		pBFSData.distanceNeighbors = new int[wNumberNeighbors];

	}

}
