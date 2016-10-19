package projects.kcluster.nodes.timers;

import projects.kcluster.nodes.nodeImplementations.KclusterNode;
import sinalgo.nodes.timers.Timer;

public class StartTimer extends Timer {

	/** the function "start" is called when the timer is over */
	@Override
	public void fire() {
		// The node here must be a DfsNode
		try {
			KclusterNode n = (KclusterNode) getTargetNode();
			n.start();
		} catch (ClassCastException aException) {
			aException.printStackTrace();
		}

	}
}
