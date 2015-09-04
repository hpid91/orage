
package orage.control.peer;

import java.awt.Color;

import orage.control.Command;
import orage.model.Model;
import orage.model.PeerCollection;
import orage.model.peer.Peer;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserCollection;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;


public class CdCreatePeer  extends Command {

	public CdCreatePeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JPeerDialog dialog = view.getCreatePeerDialog();
				
		String name = dialog.getName();
		boolean lyer = dialog.isLyer();
		int x = dialog.getInitX();
		int y = dialog.getInitY();
		Color c = dialog.getColor();
		PeerCollection knownPeers = dialog.getPeerSelectedList();
		
		Superviser defaultsup = model.getSupervisers().get(0);
		
        SuperviserCollection slss = new SuperviserCollection();
		slss.add(defaultsup);
		
		Peer newPeer = new Peer(name, x ,y , model.getBoard(), lyer, false, c , slss);
		Peer temp;
		for(int i = 0; i < knownPeers.size(); i++) {
			temp = (Peer)knownPeers.get(i);
			newPeer.addAKnownPeer(temp);
		}
		
		model.addSuperviserPeer(defaultsup, newPeer);
		view.closeCreatePeerDialog();

		view.getBoardPanel().setPeers(model.getPeers());
		
	}
}