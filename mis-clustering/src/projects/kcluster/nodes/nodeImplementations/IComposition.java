package projects.kcluster.nodes.nodeImplementations;

import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public interface IComposition {

	public void handleMessage(Message aInbox);

	public void neighborhoodChange();

	public void send();

	public void start(Node aNode);

}
