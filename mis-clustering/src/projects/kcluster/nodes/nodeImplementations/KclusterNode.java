package projects.kcluster.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import projects.kcluster.nodes.timers.SendTimer;
import projects.kcluster.nodes.timers.StartTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class KclusterNode extends Node {

	/** List of compositions to use */
	private LinkedList<IComposition> pCompositions;

	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		this.setColor(Color.YELLOW);
		String text = "" + this.ID;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, Color.black);
	}

	@Override
	public void handleMessages(Inbox inbox) {
		while (inbox.hasNext()) {
			Message m = inbox.next();
			for (IComposition wComposition : pCompositions) {
				wComposition.handleMessage(m);
			}
		}
	}

	@Override
	public void init() {
		(new StartTimer()).startRelative(1, this);
	}

	@Override
	public void neighborhoodChange() {
		for (IComposition wComposition : pCompositions) {
			wComposition.neighborhoodChange();
		}
	}

	@Override
	public void postStep() {
	}

	@Override
	public void preStep() {
	}

	/**
	 * Send a message
	 */
	public void send() {
		for (IComposition wComposition : pCompositions) {
			wComposition.send();
		}

		/* regular call to send */
		(new SendTimer()).startRelative(15, this);
	}

	/**
	 * Start the node
	 */
	public void start() {
		/* Create Compositions and start them */
		pCompositions = new LinkedList<>();
		pCompositions.add(new BFSComposition());

		/* Start all compositions */
		for (IComposition wComposition : pCompositions) {
			wComposition.start(this);
		}

		/* first call to send at 2 */
		(new SendTimer()).startRelative(1, this);
	}

}
