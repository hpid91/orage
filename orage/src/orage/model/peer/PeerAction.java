
package orage.model.peer;

import orage.model.Action;


public abstract class PeerAction extends Action {
	
	protected Peer peer;
		
	protected Peer target = null;	// target : bot who receive the request
	protected Element request = null;	// request : object of the action. 
								// For a talk, a query : a cell
	protected Element answer = null;	// The answer of the request
	
	public final static String QUERY_KIND = "Query";
	public final static String TALK_KIND = "Talk";
	public final static String KILL_KIND = "Kill";
	public final static String MOVE_KIND = "Move";
	public final static String AVG_KIND = "Avg";
	
	public final static String GETQUERYLIST_KIND = "GetQueryList";
	
	public PeerAction(Peer p) {
		peer = p;
	}
	
	protected Peer getAPeerToAsk() {
		Peer b = null;

		if (peer.knownPeers.size() > 0) {

			int range = peer.knownPeers.size();
			range = peer.rd.nextInt(range);

			b = (Peer) peer.knownPeers.get(range);
		}

		return b;
	}
		
}
