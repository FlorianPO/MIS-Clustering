package projects.kcluster.nodes.messages;

import sinalgo.nodes.messages.Message;

public class BFSTMessage extends Message {
	private static int BFS_cpt = 0;

	public int cpt;
	public int distance;
	public int ID;

	public BFSTMessage(int ID, int distance) {
		this.ID = ID;
		this.distance = distance;
		cpt = BFS_cpt;
		BFS_cpt++;
	}

	@Override
	public Message clone() {
		return this;
	}

	@Override
	public String toString() {
		return "ID_" + ID + ", d_" + distance + " (BFS " + cpt + ")";
	}

}
