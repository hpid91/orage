
package orage.model.superviser;

import orage.model.Action;
import orage.model.peer.Peer;


public abstract class SuperviserAction extends Action {
	
	protected Superviser superviser;
	
	
	protected Peer peerTarget = null;	// target : bot who receive the request
	protected Peer knownPeerTarget = null;	// target : bot who receive the request
		
	public final static String ADDPEER_KIND = "AddPeer";
	public final static String ADDKPEER_KIND = "AddKPeer";
	public final static String REMKPEER_KIND = "RemoveKPeer";
	public final static String REMPEER_KIND = "RemovePeer";
	public final static String DIAGNOSE_KIND = "Diagnose";
	
	public SuperviserAction(Superviser s) {
		superviser = s;
	}
			
	protected void setTarget() {
		int nbpeers = superviser.peers.size();
		if (nbpeers > 0 ) {
			int pos = superviser.rd.nextInt(nbpeers);
		
			peerTarget = superviser.peers.get(pos);
		} else peerTarget = null;
		
	}
	
	protected void setKPeerTarget() {
		int nbpeers = superviser.peers.size();
		if (nbpeers > 1 ) {
			int pos = superviser.rd.nextInt(nbpeers);
			knownPeerTarget = superviser.peers.get(pos);
			if (knownPeerTarget.equals(peerTarget)) setKPeerTarget();
		} else knownPeerTarget = null;
		
	}
	
	public Peer getPeerTarget() {
		return peerTarget;
	}
}
