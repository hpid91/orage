
package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;
import orage.ui.simulation.JPreferencesPanel;


public class CdOpenPreferencesPanel  extends Command {
	
	public CdOpenPreferencesPanel(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JPreferencesPanel preferencesPanel = 
				new JPreferencesPanel(view.getListener(), 
									model.getPeersSuperviserPonderation(),
									model.getPeerPonderationTableModel(),
									model.getSuperviserPonderationTableModel(),
									model.getPeerActionPonderationTableModel(),
									model.getSuperviserActionPonderationTableModel(),
									model.getActionsLogged());
		
		view.setPreferencesPanel(preferencesPanel);
	}
	
	
}