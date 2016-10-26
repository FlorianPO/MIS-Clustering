package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.CLRData;
import projects.kcluster.models.compositions.MISTData;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class CLRComposition implements IComposition {
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
	}

	@Override
	public void handleMessage(Message aMessage) {
	}

	private int macroAlpha() {
		return 0;
	}

	private int macroHead() {
		return 0;
	}

	private boolean macroIsClusterHead() {
		// TODO
		return false;
	}

	private boolean macroIsShort() {
		return pCLRData.alpha < k;
	}

	private boolean macroIsTall() {
		return pCLRData.alpha >= k;
	}

	private int macroMaxAShort() {
		// TODO
		return 0;
	}

	private int macroMinATall() {
		return 0;
	}

	private int macroMinIdMinATall() {
		return 0;
	}

	private int macroParent() {
		return 0;
	}

	private int macroShortChildren() {
		// TODO
		return 0;
	}

	private int macroTallChildren() {
		// TODO
		return 0;
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	private boolean ruleSetAlpha() {
		// TODO
		return false;
	}

	private boolean ruleSetHead() {
		// TODO
		return false;
	}

	private boolean ruleSetParentCLR() {
		// TODO
		return false;
	}

	@Override
	public void send() {
	}

	@Override
	public void start() {
		// TODO
	}

}
