
package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdCloseCreateSuperviser  extends Command {

	public CdCloseCreateSuperviser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		view.closeCreateSuperviserDialog();
		
	}
}
