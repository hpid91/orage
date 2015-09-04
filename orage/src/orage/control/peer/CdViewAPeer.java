package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.model.peer.Peer;
import orage.ui.board.JBoardDialog;
import orage.ui.main.OrageWindow;

public class CdViewAPeer extends Command {
	
	
	public CdViewAPeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		int index = view.getSimulPanel().getSelectedPeer();
		if ( index != -1) {
			Peer peer = model.getPeers().get(index);
			
			JBoardDialog boardDialog = new JBoardDialog(view,
														peer,
														model.getBoard(),
														model.getPeers());
			
			boardDialog.setVisible(true);
			
		}
		
	}
}
