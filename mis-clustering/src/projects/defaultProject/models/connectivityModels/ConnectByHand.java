
/** no connectivity by default at all, each connection has to be built by hand, with
 * node.addConnectionTo(node)
 */

package projects.defaultProject.models.connectivityModels;

import sinalgo.models.ConnectivityModelHelper;
import sinalgo.nodes.Node;

public class ConnectByHand extends ConnectivityModelHelper {
	
	protected boolean isConnected(Node from, Node to){
		return from.outgoingConnections.contains(from, to);
	}
}
