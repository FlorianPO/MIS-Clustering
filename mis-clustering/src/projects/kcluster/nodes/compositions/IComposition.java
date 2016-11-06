package projects.kcluster.nodes.compositions;

import sinalgo.nodes.messages.Message;

/**
 * Composition of an auto stabilizing algorithm.
 */
public interface IComposition {

	/**
	 * Update the state of the current Node using the content of the Message
	 * aMessage and the Node current state
	 *
	 * @param aMessage
	 *            received message
	 */
	public void handleMessage(Message aMessage);

	/**
	 * Actions to make when the neighborhood changes
	 */
	public void neighborhoodChange();

	/**
	 * Send the current state of the Node on the network.
	 */
	public void send();

	/**
	 * Initializes the Composition. This function must be called after the
	 * connections between nodes are evaluated and before the first call to
	 * handleMessage().
	 */
	public void start();

}
