package projects.kcluster.nodes.messages;

import projects.kcluster.models.compositions.MISTKey;
import sinalgo.nodes.messages.Message;

public class MISTMessage extends Message {

	public int ID;
	public boolean dominator;
	public MISTKey key;

	public MISTMessage(int ID, boolean dominator, MISTKey key) {
		this.ID = ID;
		this.dominator = dominator;
		this.key = key;
	}

	@Override
	public Message clone() {
		return this;
	}

}