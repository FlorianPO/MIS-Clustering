package projects.kcluster.nodes.random;

public class Random {
	private static int _mod_param = 100000;

	/**
	 * Returns a random integer between [0, max[ (Linear probability)
	 *
	 * @param max
	 * @return [0, max[
	 */
	public static int rand(int max) {
		return (int) (Math.random() * _mod_param % max);
	}

	/**
	 * Returns a random integer between [mean - variation, mean + variation]
	 * (Linear probability)
	 *
	 * @param mean
	 * @param variation
	 * @return [mean - variation, mean + variation]
	 */
	public static int randInterval(int mean, int variation) {
		return mean + (int) (Math.random() * _mod_param % (variation * 2)) - variation;
	}
}
