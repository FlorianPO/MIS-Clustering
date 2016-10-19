package projects.kcluster.nodes.timers;

import projects.kcluster.nodes.nodeImplementations.KclusterNode;
import sinalgo.nodes.timers.Timer;

public class SendTimer extends Timer {

	/** the function "send" is called when the timer is over */
	@Override
	public void fire() {
		try {
			KclusterNode n = (KclusterNode) getTargetNode();
			n.send();
		} catch (ClassCastException aException) {
			aException.printStackTrace();
		}
	}
}