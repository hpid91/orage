package orage.control.simulmenu;

import orage.control.Command;
import orage.model.Model;
import orage.ui.board.JBoardPanel;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerPanel;
import orage.ui.simulation.JSuperviserPanel;

public class CdBackToSimulPanel  extends Command {
	
	public CdBackToSimulPanel(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		if (view.getSimulPanel() == null) {
			JPeerPanel peerPanel = new JPeerPanel(view.getListener(), model.getPeerTableModel());
			JBoardPanel boardPanel = new JBoardPanel(view.getListener(), model.getBoard(), model.getPeers());
			JSuperviserPanel supPanel = new JSuperviserPanel(view.getListener(),
											model.getSupervisersTableModel());
			
			view.buildSimulPanel(peerPanel,supPanel,  boardPanel, model.getDocument().getDocument());
			view.getMenubar().setSimuMenuEnabled(true);
		} else {
			
			view.setSimulPanel();
		}
		
	}
	
	
}