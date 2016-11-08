package projects.kcluster;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import sinalgo.runtime.AbstractCustomGlobal;
import sinalgo.runtime.Main;
import sinalgo.runtime.NotInGUIModeException;

/**
 * This class holds customized global state and methods for the framework. The
 * only mandatory method to overwrite is <code>hasTerminated</code> <br>
 * Optional methods to override are
 * <ul>
 * <li><code>customPaint</code></li>
 * <li><code>handleEmptyEventQueue</code></li>
 * <li><code>onExit</code></li>
 * <li><code>preRun</code></li>
 * <li><code>preRound</code></li>
 * <li><code>postRound</code></li>
 * <li><code>checkProjectRequirements</code></li>
 * </ul>
 *
 * @see sinalgo.runtime.AbstractCustomGlobal for more details. <br>
 *      In addition, this class also provides the possibility to extend the
 *      framework with custom methods that can be called either through the menu
 *      or via a button that is added to the GUI.
 */
public class CustomGlobal extends AbstractCustomGlobal {
	public static class DrawHandler implements KeyListener {
		public enum state {
			BFST, MIST, CLR
		}

		public final static DrawHandler DRAW_HANDLER = new DrawHandler();

		public state current_state;

		private DrawHandler() {
			current_state = state.CLR;
			try {
				Main.getGuiRuntime().getGUI().getGraphPanel().addKeyListener(this);
			} catch (NotInGUIModeException e) {
			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_A) {
				current_state = state.BFST;
			} else if (arg0.getKeyCode() == KeyEvent.VK_Z) {
				current_state = state.MIST;
			} else if (arg0.getKeyCode() == KeyEvent.VK_E) {
				current_state = state.CLR;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see runtime.AbstractCustomGlobal#hasTerminated()
	 */
	@Override
	public boolean hasTerminated() {
		return false;
	}
}