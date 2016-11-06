package projects.kcluster.models.compositions;

/**
 * Data manipulated by a node during the creation of a MIS tree
 */
public class MISTData {

	/**
	 * MIS tree Key of a Node
	 */
	public static class MISTKey implements Comparable<MISTKey> {
		/** Id of node */
		public int ID;

		/** distance of root in DFS tree */
		public int levelDFS;

		/**
		 * @param aID
		 *            Id of node
		 * @param aLevelDFS
		 *            distance of root in DFS tree
		 */
		public MISTKey(int aID, int aLevelDFS) {
			this.ID = aID;
			this.levelDFS = aLevelDFS;
		}

		@Override
		public int compareTo(MISTKey a) {
			/* Method to compare two Keys. */
			// Return -1 if this < a
			// Return 0 if this = a
			// Return 1 if this > a

			if (this.levelDFS < a.levelDFS) {
				return -1;
			} else if (this.levelDFS > a.levelDFS) {
				return 1;
			}

			if (this.ID < a.ID) {
				return -1;
			} else if (this.ID > a.ID) {
				return 1;
			}

			return 0;
		}

	}

	/** Id of Parent node in tree. 1 if root */
	public int parent;

	/** Boolean set to true if Node is dominator */
	public boolean dominator;

	/**
	 * Keys of all neighbors nodes. Index of this array is matched to the index
	 * of the outgoingConnections of the associate Node
	 */
	public MISTKey[] neighborsKey;

	/**
	 * Dominator value of all neighbors nodes. Index of this array is matched to
	 * the index of the outgoingConnections of the associate Node
	 */
	public boolean[] neighborsDominator;
}
