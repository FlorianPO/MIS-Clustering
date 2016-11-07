package projects.kcluster.nodes.messages;

import projects.kcluster.models.compositions.MISTData.MISTKey;
import sinalgo.nodes.messages.Message;

/**
 * Messages used to by the MIST Composition to exchange Nodes' states
 */
public class MISTMessage extends Message {
	/** Static counter used to id messages */
	private static int MIST_cpt = 0;

	/** ID of sender Node */
	public int ID;
	/** value of dominator variable of sender Node */
	public boolean dominator;
	/** Key of sender Node */
	public MISTKey key;
	/** Value of the counter at the creation of the message **/
	public int messageID;

	/**
	 *
	 * @param ID
	 *            ID of sender
	 * @param dominator
	 *            is sender dominator in MIS tree
	 * @param key
	 *            Key of send
	 */
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
		return String.format("[%s: mid=%d, ID=%d, dom=%b, key=%s]", getClass().getSimpleName(), messageID, ID,
				dominator, key);
	}
}