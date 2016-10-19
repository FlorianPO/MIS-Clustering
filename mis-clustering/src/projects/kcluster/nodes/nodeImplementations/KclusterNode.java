package projects.kcluster.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import projects.kcluster.models.compositions.BFSData;
import projects.kcluster.nodes.compositions.BFSComposition;
import projects.kcluster.nodes.compositions.IComposition;
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

	private BFSData pBFSData;

	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		this.setColor(Color.YELLOW);

		String desc;
		if (pCompositions != null) {
			int wDistance = 0;
			int wParent = 0;
			for (IComposition wComposition : pCompositions) {
				if (wComposition instanceof BFSComposition) {
					BFSComposition wBFSComposition = (BFSComposition) wComposition;
					wDistance = pBFSData.distance;
					wParent = pBFSData.parent;
				}
			}
			desc = String.format("%d : (%d, %d)", ID, wDistance, wParent);
		} else {
			desc = String.format("%d", ID);
		}

		super.drawNodeAsDiskWithText(g, pt, highlight, desc, 20, Color.black);
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
		/* Create shared Data objects */
		pBFSData = new BFSData();

		/* Create Compositions */
		pCompositions = new LinkedList<>();
		pCompositions.add(new BFSComposition(this, pBFSData));

		/* Start all compositions */
		for (IComposition wComposition : pCompositions) {
			wComposition.start();
		}

		/* first call to send at 2 */
		(new SendTimer()).startRelative(1, this);
	}

}
