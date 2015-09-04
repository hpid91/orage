
package orage.control.simulmenu.action;

import orage.control.Command;
import orage.model.Model;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserAction;
import orage.ui.main.OrageWindow;

public class CdRemovePeerAction   extends Command {
	
	public CdRemovePeerAction(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		if (view.getSimulPanel() != null) {
			int index = view.getSuperviserPanel().getSelectedSup();
			if (index != -1) {
				Superviser sup = model.getSupervisers().get(index);
			
				sup.makeAnAction(SuperviserAction.REMPEER_KIND);
				model.updateSuperviserModel();
							
				view.getSimulPanel().setBoardDefined();
				view.getBoardPanel().setPeers(model.getPeers());
				view.getSuperviserPanel().setSelectedSup(index);
			}
		}
	}
	
	
}