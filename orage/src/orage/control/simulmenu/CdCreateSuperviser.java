
package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.model.PeerCollection;
import orage.model.superviser.Superviser;
import orage.ui.main.OrageWindow;
import orage.ui.simulation.JSuperviserDialog;


public class CdCreateSuperviser  extends Command {

	public CdCreateSuperviser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JSuperviserDialog dialog = view.getCreateSuperviserDialog();
				
		String name = dialog.getName();
		PeerCollection supPeers = dialog.getPeerSelectedList();
		PeerCollection notsupPeers = dialog.getPeerNotSelectedList();
		
		Superviser newsup = new Superviser(name, model.getBoard());
		model.setSuperviserPeers(newsup, supPeers, notsupPeers);
		
		model.addSuperviser(newsup);
		view.closeCreateSuperviserDialog();
		
	}
}