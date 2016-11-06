package projects.kcluster.models.compositions;

/**
 * Data manipulated by a node during the creation of CLR clusters
 */
public class CLRData {
	public int parent;

	public int alpha;

	public int head;

	public int parentPrevNeighbors[];

	public int alphaNeighbors[];

	public int headNeighbors[];
}
