
package orage.control.peer;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdRemovePeer extends Command {
	public CdRemovePeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		int index = view.getPeerPanel().getTablePeer().getSelectedRow();
		if (index > -1 ){
			model.removePeer(index);
			view.getBoardPanel().setPeers(model.getPeers());
		} else {
			model.getDocument().addMessage(Messages.getString("CdRemovePeer.0")); //$NON-NLS-1$
		}
		
	}
}
