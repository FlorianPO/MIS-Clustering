package projects.kcluster.nodes.messages;

import projects.kcluster.models.compositions.MISTData.MISTKey;
import sinalgo.nodes.messages.Message;

public class MISTMessage extends Message {
	private static int MIST_cpt = 0;

	public int ID;
	public int messageID;
	public boolean dominator;
	public MISTKey key;

	public MISTMessage(int ID, boolean dominator, MISTKey key) {
		this.ID = ID;
		this.dominator = dominator;
		this.key = key;

		messageID = ++MIST_cpt;
	}

	@Override
	public Message clone() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("[%s: mid=%d, ID=%d, dominator=%d, key=%s]", getClass().getSimpleName(), messageID, ID,
				dominator, key);
	}
}