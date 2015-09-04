
package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;
import orage.ui.simulation.JSuperviserDialog;

public class CdOpenCreateSuperviser extends Command {
	
	public CdOpenCreateSuperviser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		JSuperviserDialog dialog = new JSuperviserDialog(view.getListener(),
											  model.getPeers(),
											  JPeerDialog.MODE_CREATE);
		view.setCreateSuperviserDialog(dialog);
		dialog.setLocationRelativeTo(view);
		dialog.show();
	}
}
