package projects.kcluster.models.compositions;

public class MISTData {

	public boolean dominator;
	/** Id of Parent node. 1 if root */
	public int parent;

	/* Neighbors informations */
	public MISTKey[] neighborsKey;
	public boolean[] neighborsDominator;
}
