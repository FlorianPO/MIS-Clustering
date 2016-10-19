package projects.kcluster.nodes.nodeImplementations;

import java.util.Iterator;

import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;

public class BasicNode {

	/**
	 * Retourne le numéro du canal permettant de communiquer avec le noeud
	 * d'identifiant ID. Si le noeud n'est pas trouvé dans les voisin du noeud,
	 * la fonction retourne -1
	 *
	 * @param aNode
	 * @param aID
	 *            identifiant du noeud
	 * @return
	 */
	public static int getIndex(Node aNode, int aID) {
		int j = 0;
		for (Edge e : aNode.outgoingConnections) {
			if (e.endNode.ID == aID) {
				return j;
			}
			j++;
		}
		return -1;
	}

	/**
	 * Retourne l'identifiant du noeud connecté au canal 'indice‘ du noeud. Si
	 * l'indice n'existe pas, la fonction retourne -1
	 *
	 * @param aNode
	 * @param aIndice
	 * @return identifiant du noeud.
	 */
	public static int getVoisin(Node aNode, int aIndice) {
		if (aIndice >= aNode.outgoingConnections.size() || aIndice < 0) {
			return -1;
		}
		Iterator<Edge> iter = aNode.outgoingConnections.iterator();
		for (int j = 0; j < aIndice; j++) {
			iter.next();
		}
		return iter.next().endNode.ID;
	}

	/**
	 *
	 * @param aNode
	 * @return Number of neighbors
	 */
	public static int nbNeighbors(Node aNode) {
		return aNode.outgoingConnections.size();
	}

}
