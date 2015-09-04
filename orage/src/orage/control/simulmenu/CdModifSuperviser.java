
package orage.control.simulmenu;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.model.PeerCollection;
import orage.model.superviser.Superviser;
import orage.ui.main.OrageWindow;
import orage.ui.simulation.JSuperviserDialog;

public class CdModifSuperviser  extends Command {

	public CdModifSuperviser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JSuperviserDialog dialog = view.getCreateSuperviserDialog();
		String id = dialog.getId();
		
		if (id != null && !id.equals("")) { //$NON-NLS-1$
			
			// UPDATE BOT DATA
			String name = dialog.getName();
			PeerCollection supPeers = dialog.getPeerSelectedList();
			PeerCollection notsupPeers = dialog.getPeerNotSelectedList();
			
			Superviser sup = model.getSupervisers().getFromId(id);
			sup.setName(name);
			
			model.setSuperviserPeers(sup, supPeers, notsupPeers);
			
			model.updateSuperviserModel();
			
		} else {
			model.getDocument().addMessage(Messages.getString("CdModifSuperviser.1")); //$NON-NLS-1$
		}
		
				
		view.closeCreateSuperviserDialog();

		
		
	}
}