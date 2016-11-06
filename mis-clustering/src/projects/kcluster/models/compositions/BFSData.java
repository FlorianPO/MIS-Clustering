package projects.kcluster.models.compositions;

/**
 * Data manipulated by a node during the creation of a BFS tree
 */
public class BFSData {

	/** Id of Parent node in tree. 1 if root */
	public int parent;

	/** Distance of node */
	public int distance;

	/**
	 * Distances of all neighbors nodes. Index of this array is matched to the
	 * index of the outgoingConnections of the associate Node
	 */
	public int[] distanceNeighbors;
}
