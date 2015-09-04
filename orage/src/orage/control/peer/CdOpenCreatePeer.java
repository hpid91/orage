
package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;


public class CdOpenCreatePeer extends Command {
	
	public CdOpenCreatePeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		JPeerDialog dialog = new JPeerDialog(view.getListener(),
											  model.getPeers(),
											  model.getSupervisers(),
											  JPeerDialog.MODE_CREATE,
											  model.getBoard().getXsize() - 1,
											  model.getBoard().getYsize() - 1);
		view.setCreatePeerDialog(dialog);
		dialog.setLocationRelativeTo(view);
		dialog.show();
	}
}
