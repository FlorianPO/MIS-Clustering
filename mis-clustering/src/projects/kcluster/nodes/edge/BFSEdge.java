package projects.kcluster.nodes.edge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import projects.kcluster.nodes.nodeImplementations.KclusterNode;
import sinalgo.gui.helper.Arrow;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Position;
import sinalgo.nodes.edges.BidirectionalEdge;

public class BFSEdge extends BidirectionalEdge {
	@Override
	public void draw(Graphics g, PositionTransformation pt) {
		Position p1 = startNode.getPosition();
		pt.translateToGUIPosition(p1);
		int fromX = pt.guiX, fromY = pt.guiY; // temporarily store
		Position p2 = endNode.getPosition();
		pt.translateToGUIPosition(p2);

		boolean resolved = false;
		if (startNode instanceof KclusterNode && endNode instanceof KclusterNode) {
			KclusterNode deb = (KclusterNode) this.startNode;
			KclusterNode fin = (KclusterNode) this.endNode;

			if (deb.getBFSData().parent == fin.ID || fin.getBFSData().parent == deb.ID) {
				resolved = true;
				Graphics2D g2 = (Graphics2D) g;
				Stroke stroke = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
				g2.setStroke(stroke);
				Arrow.drawArrow(fromX, fromY, pt.guiX, pt.guiY, g2, pt, Color.BLUE);
			}
		}
		if (!resolved) {
			Graphics2D g2 = (Graphics2D) g;
			Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
			g2.setStroke(stroke);
			Arrow.drawArrow(fromX, fromY, pt.guiX, pt.guiY, g2, pt, Color.GRAY);
		}
	}

}
