package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdSetPreferences  extends Command {
	
	public CdSetPreferences(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		if (view.getPreferencesPanel() != null) {
			float psp = view.getPreferencesPanel().getPeersupponderation();
			model.setPeersSuperviserPonderation(psp);
			model.updateAllPonderation();
			
			model.setActionsLogged(view.getPreferencesPanel().getActionsLogged());
			
		}
	}
	
	
}