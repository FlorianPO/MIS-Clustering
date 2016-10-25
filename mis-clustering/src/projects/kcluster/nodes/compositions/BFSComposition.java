package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.nodes.logger.Logger;
import projects.kcluster.nodes.messages.BFSMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import projects.kcluster.nodes.random.Random;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

public class BFSComposition implements IComposition {
	private static Logger<Node, BFSMessage> logger = new Logger<>("BFSLogger", true);

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
		// Check the message of the type we are waiting
		if (aMessage instanceof BFSMessage) {
			BFSMessage wMessageBFS = (BFSMessage) aMessage;
			logger.logReceive(pNode, wMessageBFS);

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
		BFSMessage wMessageBFS = new BFSMessage(pNode.ID, pBFSData.distance);
		logger.logSend(pNode, wMessageBFS);
		pNode.broadcast(wMessageBFS);
	}

	@Override
	public void start() {
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);
		if (pNode.ID == 1) {
			pBFSData.parent = 1;
			pBFSData.distance = 0;
		} else {
			int arc = Random.rand(wNumberNeighbors);
			pBFSData.parent = BasicNode.getVoisin(pNode, arc);
			pBFSData.distance = Tools.getNodeList().size();
		}
		pBFSData.distanceNeighbors = new int[wNumberNeighbors];
	}

}
