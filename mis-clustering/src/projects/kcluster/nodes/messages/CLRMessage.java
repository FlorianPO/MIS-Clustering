package projects.kcluster.nodes.messages;

import sinalgo.nodes.messages.Message;

public class CLRMessage extends Message {

	public int ID;
	public int alpha;
	public int head;
	public int prev_parent;

	public CLRMessage(int ID, int alpha, int head, int prev_parent) {
		this.ID = ID;
		this.alpha = alpha;
		this.head = head;
		this.prev_parent = prev_parent;
	}

	@Override
	public Message clone() {
		return this;
	}

}
