package orage.model.superviser;

import orage.model.peer.Peer;


public class SuperviserActionAddKPeer extends SuperviserAction {

	public SuperviserActionAddKPeer(Superviser s) {
		super(s);

		kind = super.ADDKPEER_KIND;
	}

	public boolean execute() {

		if (hasKPeerToAdd()) {
			selectPeerAndKPeer();
			if ((peerTarget != null) && (knownPeerTarget != null)) {
				peerTarget.addAKnownPeer(knownPeerTarget);
				return true;
			} else {
				superviser.getLogger().addMessage("ERROR : " + superviser.getName() + " Not enough peers.");
				return false;
			}
		} else {
			superviser.getLogger().addMessage(
					"ERROR : All peers of the superviser "
							+ superviser.getName() + " know each other.");
			return false;
		}
	}

	/**
	 * Return true if all the peers supervised by the current superviser are
	 * interconnected, false else;
	 * 
	 * @return
	 */
	private boolean hasKPeerToAdd() {
		Peer current;
		boolean result = false;
		int size = superviser.peers.size();

		for (int i = 0; i < size; i++) {
			current = superviser.peers.get(i);
			for (int j = 0; j < size; j++) {
				if ((!current.equals(superviser.peers.get(j)) && (!current
						.isAKnownPeer(superviser.peers.get(j)))))
					result = true;
			}

		}

		return result;
	}

	/**
	 * Select randomly a peer target and add a known peer to the target
	 */
	private void selectPeerAndKPeer() {
		setTarget();

		if (knowsOtherPeers())
			selectPeerAndKPeer();
		selectKPeer();

	}

	/**
	 * Return true if the curren peer target knows all the other peer supervised
	 * by the superviser.
	 * 
	 * @return
	 */
	private boolean knowsOtherPeers() {
		boolean result = false;
		int nbIn = 0;

		int psize = superviser.peers.size();
		for (int i = 0; i < psize; i++) {
			int ksize = peerTarget.getKnownPeers().size();
			for (int j = 0; j < ksize; j++) {
				if (superviser.peers.get(i).equals(
						peerTarget.getKnownPeers().get(j)))
					nbIn++;
			}
		}

		if (nbIn == psize - 1)
			result = true;

		return result;
	}

	/**
	 * Select a known peer for the peer target
	 */
	private void selectKPeer() {
		setKPeerTarget();
		if (peerTarget.isAKnownPeer(knownPeerTarget)) {
			selectKPeer();
		}
	}

	public String toString() {
		String s = "Superviser " + superviser.getName()
				+ " has ADDED A KNOWN PEER : " + peerTarget.getName()
				+ " knows now " + knownPeerTarget.getName() + " .";

		return s;
	}

	public String toXML() {
		String s = "\t<action>\r\n" + "\t\t<kind>" + kind + "</kind>\r\n"
				+ "\t\t<superviser>" + superviser.getId() + "</superviser>\r\n"
				+ "\t\t<peerTarget>" + peerTarget.getId() + "</peerTarget>\r\n"
				+ "\t\t<knownPeerTarget>" + knownPeerTarget.getId()
				+ "</knownPeerTarget>\r\n" + "\t</action>\r\n";

		return s;
	}

}