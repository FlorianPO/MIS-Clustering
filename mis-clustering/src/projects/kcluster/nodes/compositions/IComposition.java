package projects.kcluster.nodes.compositions;

import sinalgo.nodes.messages.Message;

public interface IComposition {

	public void handleMessage(Message aInbox);

	public void neighborhoodChange();

	public void send();

	public void start();

}
