package projects.kcluster.models.compositions;

public class MISTKey implements Comparable<MISTKey> {

	public int ID;
	public int levelDFS;

	public MISTKey(int aID, int aLevelDFS) {
		this.ID = aID;
		this.levelDFS = aLevelDFS;
	}

	@Override
	public int compareTo(MISTKey a) {
		if (this.levelDFS < a.levelDFS) {
			return -1;
		} else if (this.levelDFS < a.levelDFS) {
			return 1;
		}

		if (this.ID < a.ID) {
			return -1;
		} else if (this.ID < a.ID) {
			return 1;
		}

		return 0;
	}

}
