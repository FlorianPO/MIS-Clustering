package projects.kcluster.nodes.compositions;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.models.compositions.MISTData;
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
	}

	@Override
	public void handleMessage(Message aMessage) {
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
