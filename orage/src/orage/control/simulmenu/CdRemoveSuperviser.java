
package orage.control.simulmenu;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdRemoveSuperviser extends Command {
	public CdRemoveSuperviser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		int index = view.getSuperviserPanel().getSelectedSup();
		if (index > -1 ){
			boolean ok = model.removeSuperviser(index);
			//view.getSuperviserPanel().setSupervisers(model.getSupervisers());
			if (!ok) model.getDocument().addMessage(Messages.getString("CdRemoveSuperviser.1"));
		} else {
			model.getDocument().addMessage(Messages.getString("CdRemoveSuperviser.0"));
		}
		
	}
}
