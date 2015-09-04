
package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdCloseCreatePeer  extends Command {

	public CdCloseCreatePeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		view.closeCreatePeerDialog();
		
	}
}
