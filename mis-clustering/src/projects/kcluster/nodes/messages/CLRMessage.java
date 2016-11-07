package projects.kcluster.nodes.messages;

import sinalgo.nodes.messages.Message;

public class CLRMessage extends Message {
	private static int CLR_cpt = 0;

	public int ID;
	public int messageID;
	public int alpha;
	public int head;
	public int parent;
	public int parent_head;

	public CLRMessage(int ID, int alpha, int head, int prev_parent, int parent_head) {
		this.ID = ID;
		this.alpha = alpha;
		this.head = head;
		this.parent = prev_parent;
		this.parent_head = parent_head;

		messageID = ++CLR_cpt;
	}

	@Override
	public Message clone() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("[%s: mid=%d, ID=%d, dominator=%d, alpha=%s, head=%d, prev_parent=%d, parent_head=%d]",
				getClass().getSimpleName(), messageID, ID, alpha, head, parent, parent_head);
	}
}
