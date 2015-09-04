package orage.model.superviser;

public class SuperviserActionRemoveKPeer extends SuperviserAction {

	public SuperviserActionRemoveKPeer(Superviser s) {
		super(s);

		kind = super.REMKPEER_KIND;
	}

	public boolean execute() {
		
		setTarget();
		setKPeerTarget();
		
		if (peerTarget != null) {
			peerTarget.removeAKnownPeer(knownPeerTarget);
			return true;
		} else {
			superviser.getLogger().addMessage("ERROR : No peer to remove.");
			return false;
		}
		
		
	}


	public String toString() {
		String s = "Superviser " + superviser.getName() + " has REMOVED A KNOWN PEER : " +
					peerTarget.getName() + " doesn't know " + knownPeerTarget.getName() + " anymore.";
			
		return s;
	}
	public String toXML() {
		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<superviser>" + superviser.getId() + "</superviser>\r\n" +
						"\t\t<peerTarget>" + peerTarget.getId() + "</peerTarget>\r\n" +
						"\t\t<knownPeerTarget>" + knownPeerTarget.getId() + "</knownPeerTarget>\r\n" +
				   "\t</action>\r\n";


		return s;
	}

}
