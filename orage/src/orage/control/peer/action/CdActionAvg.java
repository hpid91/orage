
package orage.control.peer.action;

import orage.control.Command;
import orage.model.Model;
import orage.model.peer.Peer;
import orage.model.peer.PeerAction;
import orage.ui.main.OrageWindow;

public class CdActionAvg extends Command {
	
	public CdActionAvg(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		int index = view.getSimulPanel().getSelectedPeer();
		if ( index != -1) {
			Peer peer = model.getPeers().get(index);
			peer.makeAnAction(PeerAction.AVG_KIND);
			view.getBoardPanel().getBoardPanel().repaint();
			
		}
	}
}