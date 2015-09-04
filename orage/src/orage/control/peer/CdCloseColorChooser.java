
package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdCloseColorChooser  extends Command {

	public CdCloseColorChooser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		view.closeCreatePeerDialog();
		
	}
}
