
package orage.model.peer;

import java.util.ArrayList;

import orage.model.superviser.system.Labels;

public class PeerActionGetQueryList extends PeerAction {

	private ArrayList queryList;
		
	public PeerActionGetQueryList(Peer p) {
		super(p);
		
		kind = super.GETQUERYLIST_KIND;
		queryList = new ArrayList();
	}
	
	public boolean execute() {
		if (peer.dead) return false;
		
		int size = peer.knownPeers.size();
		for (int i = 0; i < size; i++) {
            peer.loggers.notify(Labels.TRANSITION_ACTIONGETQL,
                                peer.id + peer.knownPeers.get(i).id);
			// DEAD PROPAGATION
			if (peer.knownPeers.get(i).isDead()) {
                peer.loggers.notify(Labels.TRANSITION_QLNOTOK,
                        peer.id + peer.knownPeers.get(i).id);
				peer.dead = true;
				return false;
			} else {
                peer.loggers.notify(Labels.TRANSITION_QLOK,
                        peer.id + peer.knownPeers.get(i).id);
            }
			
			// SERVICE ACTIVATED : knownCellsZone
			queryList.addAll(peer.knownPeers.get(i).knownCellsZone);
			
		}
		// Add its own cell zone list
		queryList.addAll(peer.knownCellsZone);
		
		return true;
	}

	public String toString() {
		String s = "GET QUERY LIST : " + peer.name + " asking its known peers for their query list.";

		return s;
	}

	public String toXML() {
		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<peer>" + peer.getId() + "</peer>\r\n" +
						"\t\t<kpeers>\r\n";
		
		int size = peer.knownPeers.size();
		
		for (int i = 0; i < size; i++) {
			s = s + "\t\t\t<kpeer>" + peer.knownPeers.get(i).getId() + "</kpeer>\r\n" ;
		}
		s = s + "\t\t</kpeers>\r\n" + "\t</action>\r\n";


		return s;
	}
	
	/**
	 * Service simulation : Get the "query list" of all known peers.
	 * @return
	 */
	public ArrayList getQueryList() {
		return queryList;
	}
}
