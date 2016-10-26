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

	private boolean isShort() {
		return pCLRData.alpha < k;
	}

	private boolean isTall() {
		return pCLRData.alpha >= k;
	}

	@Override
	public void neighborhoodChange() {
		// TODO
	}

	@Override
	public void send() {
	}

	@Override
	public void start() {
		// TODO
	}

}
