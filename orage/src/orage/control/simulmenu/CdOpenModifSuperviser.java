
package orage.control.simulmenu;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.model.superviser.Superviser;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;
import orage.ui.simulation.JSuperviserDialog;

public class CdOpenModifSuperviser extends Command {

	public CdOpenModifSuperviser(Model m, OrageWindow v) {
		super(m, v);
	}

	public void execute() {
		JSuperviserDialog dialog = new JSuperviserDialog(
				view.getListener(), model.getPeers(), JPeerDialog.MODE_MODIF);

		// Initialize the dialog
		int index = view.getSuperviserPanel().getSelectedSup();
		if (index != -1) {
			
			Superviser sup = model.getSupervisers().get(index);
			
			dialog.setId(sup.getId());
			dialog.setName(sup.getName());
			dialog.setSelectedList(sup.getPeers());
			view.setCreateSuperviserDialog(dialog);
			dialog.setLocationRelativeTo(view);
			dialog.show();
		} else {
			model.getDocument().addMessage(
					Messages.getString("CdOpenModifSuperviser.0")); //$NON-NLS-1$
		}
	}
}