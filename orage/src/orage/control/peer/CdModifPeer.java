package orage.control.peer;

import java.awt.Color;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.model.PeerCollection;
import orage.model.peer.Peer;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;

public class CdModifPeer  extends Command {

	public CdModifPeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JPeerDialog dialog = view.getCreatePeerDialog();
		String id = dialog.getId();
		
		if (id != null && !id.equals("")) { //$NON-NLS-1$
			
			// UPDATE BOT DATA
			String name = dialog.getName();
			boolean lyer = dialog.isLyer();
			int x = dialog.getInitX();
			int y = dialog.getInitY();
			Color c = dialog.getColor();
			PeerCollection knownPeers = dialog.getPeerSelectedList();
			
			Peer peer = model.getPeerFromId(id);
			peer.setName(name);
			peer.setLiar(lyer);
			boolean ok = true;
			if ((x != peer.getX()) || (y != peer.getY ())) ok = peer.move(x,y);
			peer.setColor(c);
						
			peer.removeAllKnownPeers();
			Peer temp;
			for(int i = 0; i < knownPeers.size(); i++) {
				temp = (Peer)knownPeers.get(i);
				peer.addAKnownPeer(temp);
			}
			
			model.updatePeerModel();
						
			view.getBoardPanel().setPeers(model.getPeers());
			view.getSimulPanel().setPeerPanelEnabled(ok);
			
		} else {
			model.getDocument().addMessage(Messages.getString("CdModifPeer.1")); //$NON-NLS-1$
		}
		
				
		view.closeCreatePeerDialog();

		
		
	}
}