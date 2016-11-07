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

/**
 * Edge used to show MIS Tree in sinalgo
 */
public class CLREdge extends BidirectionalEdge {
	@Override
	public void draw(Graphics g, PositionTransformation pt) {
		/* Retrieve first points X and Y positions */
		Position p1 = startNode.getPosition();
		pt.translateToGUIPosition(p1);
		int p1X = pt.guiX;
		int p1Y = pt.guiY;

		/* Retrieve second points X and Y positions */
		Position p2 = endNode.getPosition();
		pt.translateToGUIPosition(p2);
		int p2X = pt.guiX;
		int p2Y = pt.guiY;

		boolean resolved = false;
		if (startNode instanceof KclusterNode && endNode instanceof KclusterNode) {
			KclusterNode deb = (KclusterNode) this.startNode;
			KclusterNode fin = (KclusterNode) this.endNode;

			if (deb.getCLRData().parent == fin.ID) {
				resolved = true;
				// Nothing to do here
			} else if (fin.getCLRData().parent == deb.ID) {
				resolved = true;
				// swap p1 and p2
				int tmpX;
				tmpX = p1X;
				p1X = p2X;
				p2X = tmpX;

				int tmpY;
				tmpY = p1Y;
				p1Y = p2Y;
				p2Y = tmpY;
			}
		}
		if (resolved) {
			Graphics2D g2 = (Graphics2D) g;
			Stroke stroke = new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
			g2.setStroke(stroke);

			Arrow.drawArrow(p1X, p1Y, p2X, p2Y, g2, pt, Color.BLUE);
		} else {
			Graphics2D g2 = (Graphics2D) g;
			Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
			g2.setStroke(stroke);

			Arrow.drawArrow(p1X, p1Y, p2X, p2Y, g2, pt, Color.GRAY);
		}
	}

}
