package projects.kcluster.nodes.compositions;

import java.util.ArrayList;
import java.util.List;

import projects.kcluster.models.compositions.CLRData;
import projects.kcluster.models.compositions.MISTData;
import projects.kcluster.nodes.logger.Logger;
import projects.kcluster.nodes.messages.CLRMessage;
import projects.kcluster.nodes.nodeImplementations.BasicNode;
import projects.kcluster.nodes.random.Random;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class CLRComposition implements IComposition {
	private static Logger<Node, CLRMessage> LOGGER = new Logger<>("CLRLogger", false);

	private static final int k = 3;

	private Node pNode;

	private MISTData pMISTData;
	private CLRData pCLRData;

	public CLRComposition(Node aNode, MISTData aMISTData, CLRData aCLRData) {
		pNode = aNode;
		pMISTData = aMISTData;
		pCLRData = aCLRData;
	}

	private void actions() {
		if (ruleSetAlpha()) {
			pCLRData.alpha = macroAlpha();
		}
		if (ruleSetParentCLR()) {
			pCLRData.parent = macroParent();

			if (pCLRData.parent == pNode.ID) {
				pCLRData.parent_head = pCLRData.head;
			} else {
				pCLRData.parent_head = pCLRData.parentHeadNeighbors[BasicNode.getIndex(pNode, pCLRData.parent)];
			}
		}
		if (ruleSetHead()) {
			pCLRData.head = macroHead();
		}
	}

	@Override
	public void handleMessage(Message aMessage) {
		if (aMessage instanceof CLRMessage) {
			CLRMessage wMessageCLR = (CLRMessage) aMessage;
			LOGGER.logReceive(pNode, wMessageCLR);

			// Update known data with received one
			int index = BasicNode.getIndex(pNode, wMessageCLR.ID);
			pCLRData.alphaNeighbors[index] = wMessageCLR.alpha;
			pCLRData.headNeighbors[index] = wMessageCLR.head;
			pCLRData.parentPrevNeighbors[index] = wMessageCLR.parent;
			pCLRData.parentHeadNeighbors[index] = wMessageCLR.parent_head;

			actions();
		}
	}

	private int macroAlpha() {
		if (macroMaxAShort() + macroMinATall() <= 2 * k - 2)
			return macroMinATall() + 1;
		return macroMaxAShort() + 1;
	}

	private int macroHead() {
		if (macroIsClusterHead())
			return pNode.ID;

		return pCLRData.parent_head;
	}

	private boolean macroIsClusterHead() {
		return pCLRData.alpha == k || (macroIsShort(pNode.ID) && pNode.ID == 1);
	}

	private boolean macroIsShort(int ID) {
		int alpha;
		if (this.pNode.ID == ID)
			alpha = pCLRData.alpha;
		else
			alpha = pCLRData.alphaNeighbors[BasicNode.getIndex(pNode, ID)];

		return alpha < k;
	}

	private boolean macroIsTall(int ID) {
		int alpha;
		if (this.pNode.ID == ID)
			alpha = pCLRData.alpha;
		else
			alpha = pCLRData.alphaNeighbors[BasicNode.getIndex(pNode, ID)];
		return alpha >= k;
	}

	private int macroMaxAShort() {
		List<Integer> short_list = macroShortChildren();

		if (short_list.size() == 0)
			return -1;

		int max_alpha = Integer.MIN_VALUE;
		for (int i : short_list) {
			int current_alpha = pCLRData.alphaNeighbors[BasicNode.getIndex(pNode, i)];
			if (current_alpha > max_alpha)
				max_alpha = current_alpha;
		}

		return max_alpha;
	}

	private int macroMinATall() {
		List<Integer> tall_list = macroTallChildren();

		if (tall_list.size() == 0)
			return 2 * k + 1;

		int min_alpha = Integer.MAX_VALUE;
		for (int i : tall_list) {
			int current_alpha = pCLRData.alphaNeighbors[BasicNode.getIndex(pNode, i)];
			if (current_alpha < min_alpha)
				min_alpha = current_alpha;
		}

		return min_alpha;
	}

	private int macroMinIdMinATall() {
		List<Integer> tall_list = macroTallChildren();

		if (tall_list.size() == 0)
			return pNode.ID;

		int min_atall = macroMinATall();
		int min_ID = Integer.MAX_VALUE;

		for (int i : tall_list) {
			int current_alpha = pCLRData.alphaNeighbors[BasicNode.getIndex(pNode, i)];
			if (current_alpha == min_atall && i < min_ID)
				min_ID = i;
		}

		return min_ID;
	}

	private int macroParent() {
		if (macroIsClusterHead())
			return pNode.ID;

		if (pCLRData.alpha < k)
			return pMISTData.parent;

		return macroMinIdMinATall();
	}

	private List<Integer> macroShortChildren() {
		List<Integer> result_list = new ArrayList<>();

		for (int wI = 0; wI < pCLRData.alphaNeighbors.length; wI++) {
			int voisinID = BasicNode.getNeighbor(pNode, wI);
			if (pCLRData.parentPrevNeighbors[wI] == this.pNode.ID && macroIsShort(voisinID)) {
				result_list.add(voisinID);
			}
		}

		return result_list;
	}

	private List<Integer> macroTallChildren() {
		List<Integer> result_list = new ArrayList<>();

		for (int wI = 0; wI < pCLRData.alphaNeighbors.length; wI++) {
			int voisinID = BasicNode.getNeighbor(pNode, wI);
			if (pCLRData.parentPrevNeighbors[wI] == this.pNode.ID && macroIsTall(voisinID)) {
				result_list.add(voisinID);
			}
		}

		return result_list;
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	private boolean ruleSetAlpha() {
		return pCLRData.alpha != macroAlpha();
	}

	private boolean ruleSetHead() {
		return !ruleSetAlpha() && pCLRData.parent == macroParent() && pCLRData.head != macroHead();
	}

	private boolean ruleSetParentCLR() {
		return !ruleSetAlpha() && pCLRData.parent != macroParent();
	}

	@Override
	public void send() {
		CLRMessage wMessageCLR = new CLRMessage(pNode.ID, pCLRData.alpha, pCLRData.head, pMISTData.parent,
				pCLRData.parent_head);
		LOGGER.logSend(pNode, wMessageCLR);
		pNode.broadcast(wMessageCLR);
	}

	@Override
	public void start() {
		int wNumberNeighbors = BasicNode.nbNeighbors(pNode);

		pCLRData.alphaNeighbors = new int[wNumberNeighbors];
		pCLRData.parentPrevNeighbors = new int[wNumberNeighbors];
		pCLRData.headNeighbors = new int[wNumberNeighbors];
		pCLRData.parentHeadNeighbors = new int[wNumberNeighbors];

		for (int wI = 0; wI < pMISTData.neighborsKey.length; wI++) {
			pCLRData.alphaNeighbors[wI] = Random.rand(2 * k);
			pCLRData.parentPrevNeighbors[wI] = Random.rand(0);
			pCLRData.headNeighbors[wI] = Random.rand(0);
			pCLRData.parentHeadNeighbors[wI] = Random.rand(0);
		}
		pCLRData.alpha = Random.rand(2 * k);
		pCLRData.head = Random.rand(0);
		pCLRData.parent = Random.rand(0);
		pCLRData.parent_head = Random.rand(0);
	}

}
