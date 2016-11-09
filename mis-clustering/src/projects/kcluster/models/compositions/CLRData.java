package projects.kcluster.models.compositions;

/**
 * Data manipulated by a node during the creation of CLR clusters
 */
public class CLRData {
	/** Id of Parent node in tree. 1 if root */
	public int parent;

	/** Alpha node in tree */
	public int alpha;

	/** Head node in tree */
	public int head;

	/**
	 * Alpha of all neighbors nodes. Index of this array is matched to the index
	 * of the outgoingConnections of the associate Node
	 */
	public int alphaNeighbors[];

	/**
	 * Head of all neighbors nodes. Index of this array is matched to the index
	 * of the outgoingConnections of the associate Node
	 */
	public int headNeighbors[];

	/** Head node of Parent */
	public int parent_head;

	/**
	 * MIST Parent of all neighbors nodes. Index of this array is matched to the
	 * index of the outgoingConnections of the associate Node
	 */
	public int parentPrevNeighbors[];

	/**
	 * Parent's head all neighbors nodes. Index of this array is matched to the
	 * index of the outgoingConnections of the associate Node
	 */
	public int parentHeadNeighbors[];
}
