package projects.kcluster.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import projects.kcluster.models.compositions.BFSTData;
import projects.kcluster.models.compositions.CLRData;
import projects.kcluster.models.compositions.MISTData;
import projects.kcluster.nodes.compositions.BFSTComposition;
import projects.kcluster.nodes.compositions.CLRComposition;
import projects.kcluster.nodes.compositions.IComposition;
import projects.kcluster.nodes.compositions.MISTComposition;
import projects.kcluster.nodes.random.Random;
import projects.kcluster.nodes.timers.SendTimer;
import projects.kcluster.nodes.timers.StartTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class KclusterNode extends Node {
	private static final int _rand_mean = 10;
	private static final int _rand_variation = 5;

	/** List of compositions to use */
	private LinkedList<IComposition> pCompositions;

	/** BFST Data used in compositions */
	private BFSTData pBFSData;
	/** MIST Data used in compositions */
	private MISTData pMISTData;
	/** CLR Data used in compositions */
	private CLRData pCLRData;

	public KclusterNode() {
		pCompositions = new LinkedList<>();

		/* Create shared Data objects */
		pBFSData = new BFSTData();
		pMISTData = new MISTData();
		pCLRData = new CLRData();
	}

	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		if (pMISTData.dominator == true) {
			this.setColor(Color.BLUE);
		} else {
			this.setColor(Color.YELLOW);
		}
		if (this.ID == 1) {
			this.setColor(Color.RED);
		}

		String desc = String.format("%d", ID);

		super.drawNodeAsDiskWithText(g, pt, highlight, desc, 20, Color.black);
	}

	/**
	 * Return the BFST Data used in the Node compositions
	 *
	 * @return BFST Data
	 */
	public BFSTData getBFSData() {
		return pBFSData;
	}

	/**
	 * Return the CLR Data used in the Node compositions
	 *
	 * @return CLR Data
	 */
	public CLRData getCLRData() {
		return pCLRData;
	}

	/**
	 * Return the MIST Data used in the Node compositions
	 *
	 * @return MIST Data
	 */
	public MISTData getMISTData() {
		return pMISTData;
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
	 * Send messages regularly to inform neighbors of the current state of the
	 * Node
	 */
	public void send() {
		for (IComposition wComposition : pCompositions) {
			wComposition.send();
		}

		/* regular call to send */
		(new SendTimer()).startRelative(Random.randInterval(_rand_mean, _rand_variation), this);
	}

	/**
	 * Start the node
	 */
	public void start() {
		/* Create Compositions */
		pCompositions.add(new BFSTComposition(this, pBFSData));
		pCompositions.add(new MISTComposition(this, pBFSData, pMISTData));
		pCompositions.add(new CLRComposition(this, pMISTData, pCLRData));

		/* Start all compositions */
		for (IComposition wComposition : pCompositions) {
			wComposition.start();
		}

		/* first call to send */
		(new SendTimer()).startRelative(Random.randInterval(_rand_mean, _rand_variation), this);
	}

}
