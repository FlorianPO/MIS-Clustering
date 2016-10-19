package projects.kcluster.nodes.messages;

import sinalgo.nodes.messages.Message;

public class BFSMessage extends Message {

	public int distance;
	public int ID;

	public BFSMessage(int ID, int distance) {
		this.ID = ID;
		this.distance = distance;
	}

	@Override
	public Message clone() {
		return this;
	}

}
