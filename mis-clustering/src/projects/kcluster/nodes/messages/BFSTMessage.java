package projects.kcluster.nodes.messages;

import sinalgo.nodes.messages.Message;

/**
 * Messages used to by the BFST Composition to exchange Nodes' states
 */
public class BFSTMessage extends Message {
	/** Static counter used to id messages */
	private static int BFS_cpt = 0;

	/** ID of sender Node */
	public int ID;
	/** distance to the root node in BFS tree of sender Node */
	public int distance;
	/** Value of the counter at the creation of the message **/
	public int messageID;

	/**
	 *
	 * @param ID
	 *            ID of send
	 * @param distance
	 *            distance of sender to the root node of the BFS tree
	 */
	public BFSTMessage(int ID, int distance) {
		this.ID = ID;
		this.distance = distance;
		messageID = ++BFS_cpt;
	}

	@Override
	public Message clone() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("[%s: mid=%d, ID=%d, distance=%d]", getClass().getSimpleName(), messageID, ID, distance);
	}

}
