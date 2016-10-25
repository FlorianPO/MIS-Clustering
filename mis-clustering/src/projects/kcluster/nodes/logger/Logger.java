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
 * These logs are automatically rendered (if you specify auto_resolve == true in
 * the constructor) while the messages are sent or received (use logReceive(...)
 * and logSend(...) methods on your side to "fill" the logger)
 *
 * Messages which are still inside communication links are stored in send_list
 * and receive_list (use iterators to access the logs)
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
		private _N node;
		private _M message;

		public MSG(_N node, _M message) {
			this.node = node;
			this.message = message;
		}

		public _M getMessage() {
			return message;
		}

		public _N getNode() {
			return node;
		}
	}

	/**
	 * Handles a communication of type Sender -> Message -> Receiver These
	 * objects
	 *
	 * @param <_N>
	 * @param <_M>
	 */
	private class Transmission<_N extends Node, _M extends Message> {
		private _N sender;
		private _M message;
		private _N receiver;

		public Transmission(_N sender, _M message, _N receiver) {
			this.sender = sender;
			this.message = message;
			this.receiver = receiver;
		}

		public _M getMessage() {
			return message;
		}

		public _N getReceiver() {
			return receiver;
		}

		public _N getSender() {
			return sender;
		}
	}

	private ArrayList<Transmission<N, M>> log_list;

	private ArrayList<MSG<N, M>> send_list;
	private ArrayList<MSG<N, M>> receive_list;
	private String name;
	private boolean auto_resolve;

	public Logger(String name, boolean auto_resolve) {
		this.name = name;
		this.auto_resolve = auto_resolve;
		send_list = new ArrayList<MSG<N, M>>();
		receive_list = new ArrayList<MSG<N, M>>();
	}

	public String getName() {
		return name;
	}

	public Iterator<Transmission<N, M>> iterator() {
		return log_list.iterator();
	}

	public Iterator<MSG<N, M>> iteratorReceiver() {
		return receive_list.iterator();
	}

	public Iterator<MSG<N, M>> iteratorSender() {
		return send_list.iterator();
	}

	public void logReceive(N receiver, M message) {
		receive_list.add(new MSG<N, M>(receiver, message));
		if (auto_resolve)
			resolve();
	}

	public void logSend(N sender, M message) {
		send_list.add(new MSG<N, M>(sender, message));
		if (auto_resolve)
			resolve();
	}

	public void resolve() {
		if (send_list.size() > 0 && receive_list.size() > 0) { // Find matches
			for (int i = 0; i < send_list.size(); i++) {
				for (int j = 0; i < receive_list.size(); j++) {
					if (send_list.get(i).getMessage() == receive_list.get(i).getMessage()) {
						// Match found ! Create Transmission object
						log_list.add(new Transmission<N, M>(send_list.get(i).getNode(), send_list.get(i).getMessage(),
								receive_list.get(i).getNode()));
						send_list.remove(i); // Remove in sender list
						i--;
						send_list.remove(j); // Remove in receiver list
						j--;
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		// TODO
		return null;
	}
}
