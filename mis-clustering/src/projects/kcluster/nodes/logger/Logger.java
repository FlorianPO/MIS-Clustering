package projects.kcluster.nodes.logger;

import java.util.ArrayList;
import java.util.Iterator;

import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

/**
 * This generic logger can handle a specific node type and message type, which
 * fits to log a specific composition. It provides transmission-oriented logs
 * represented as : "Sender" -> "Message" -> "Receiver"
 *
 * These logs are automatically rendered while the messages are sent or received
 * (use logReceive(...) and logSend(...) methods on your side to "fill" the
 * logger)
 *
 * @param <N>
 *            type of node logged
 * @param <M>
 *            type of message logged
 */
public class Logger<N extends Node, M extends Message> {
	/**
	 * Handles a message sent or received
	 *
	 * @param <_N>
	 * @param <_M>
	 */
	private class MSG<_N extends Node, _M extends Message> {
		protected _N node;
		protected _M message;
		protected int time;

		public MSG(_N node, _M message) {
			this.node = node;
			this.message = message;
			this.time = (int) sinalgo.runtime.Global.currentTime;
		}

		public _M getMessage() {
			return message;
		}

		public _N getNode() {
			return node;
		}

		public int getTime() {
			return time;
		}
	}

	/**
	 * Handles a message received
	 *
	 * @param <_N>
	 * @param <_M>
	 */
	private class MSGReceived<_N extends Node, _M extends Message> extends MSG<_N, _M> {
		public MSGReceived(_N node, _M message) {
			super(node, message);
		}

		@Override
		public String toString() {
			return "[Time : " + time + "] " + message + " -> " + node;
		}
	}

	/**
	 * Handles a message sent
	 *
	 * @param <_N>
	 * @param <_M>
	 */
	private class MSGSent<_N extends Node, _M extends Message> extends MSG<_N, _M> {
		private static final int TTL = 50;
		private int ttl;

		public MSGSent(_N node, _M message) {
			super(node, message);
			ttl = 0;
		}

		public boolean incrementTTL() {
			ttl++;
			return ttl > TTL;
		}

		@Override
		public String toString() {
			return "[Time : " + time + "] " + node + " -> " + message;
		}
	}

	/**
	 * Handles a communication of type Sender -> Message -> Receiver
	 *
	 * @param <_N>
	 * @param <_M>
	 */
	private class Transmission {
		private MSGSent<N, M> sender;
		private MSGReceived<N, M> receiver;

		public Transmission(MSGSent<N, M> sender, MSGReceived<N, M> receiver) {
			this.sender = sender;
			this.receiver = receiver;
			if (echo)
				System.out.println(this);
		}

		@Override
		public String toString() {
			return "[Time : " + sender.getTime() + " & " + receiver.getTime() + "] " + sender.getNode() + " -> "
					+ sender.getMessage() + " -> " + receiver.getNode();
		}
	}

	private int current_time;

	private ArrayList<Transmission> log_list;
	private ArrayList<MSGSent<N, M>> send_list;
	private ArrayList<MSGReceived<N, M>> receive_list;
	private String name;
	private boolean echo;

	public Logger(String name, boolean echo) {
		this.name = name;
		this.current_time = 0;
		this.echo = echo;

		log_list = new ArrayList<>();
		send_list = new ArrayList<>();
		receive_list = new ArrayList<>();
	}

	private void checkTTL() {
		if ((int) sinalgo.runtime.Global.currentTime != current_time) {
			current_time = (int) sinalgo.runtime.Global.currentTime;

			Iterator<MSGSent<N, M>> j_iter = send_list.iterator();
			while (j_iter.hasNext()) {
				MSGSent<N, M> j = j_iter.next();
				if (j.incrementTTL()) {
					resolveSend(j);
					j_iter.remove();
				}

			}
		}
	}

	public String getName() {
		return name;
	}

	public Iterator<Transmission> iterator() {
		return log_list.iterator();
	}

	public void logReceive(N receiver, M message) {
		MSGReceived<N, M> msg = new MSGReceived<>(receiver, message);
		receive_list.add(msg);
		resolveReceive(msg);
		checkTTL();
	}

	public void logSend(N sender, M message) {
		MSGSent<N, M> msg = new MSGSent<>(sender, message);
		send_list.add(msg);
		resolveSend(msg);
		checkTTL();
	}

	public void resolveReceive(MSGReceived<N, M> i) {
		Iterator<MSGSent<N, M>> j_iter = send_list.iterator();
		while (j_iter.hasNext()) {
			MSGSent<N, M> j = j_iter.next();

			if (i.getMessage() == j.getMessage()) {
				// Match found ! Create Transmission object
				Transmission transmission = new Transmission(j, i);
				log_list.add(transmission);

				receive_list.remove(i);
				break;
			}
		}
	}

	public void resolveSend(MSGSent<N, M> i) {
		Iterator<MSGReceived<N, M>> j_iter = receive_list.iterator();
		while (j_iter.hasNext()) {
			MSGReceived<N, M> j = j_iter.next();

			if (i.getMessage() == j.getMessage()) {
				// Match found ! Create Transmission object
				Transmission transmission = new Transmission(i, j);
				log_list.add(transmission);

				j_iter.remove();
			}
		}
	}
}
