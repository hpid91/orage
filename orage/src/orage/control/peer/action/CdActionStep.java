
package orage.control.peer.action;

import orage.control.Command;
import orage.model.Model;
import orage.model.peer.Peer;
import orage.model.superviser.Superviser;
import orage.ui.board.JBoardDialog;
import orage.ui.common.JMessagePane;
import orage.ui.main.OrageWindow;

public class CdActionStep extends Command {
	Peer peer = null;
	Superviser sup = null;
	JBoardDialog boardDialog = null;
	boolean window = true;
	int step = 0;
	
	public CdActionStep(Model m, OrageWindow v) {
		super(m,v);
	}
		
	public void execute() {
		if (view.getSimulPanel() != null) {
			

			// Check if a peer is selected
			int index = view.getSimulPanel().getSelectedPeer();
			
			// Check if a window has to be launched
			window = view.getSimulPanel().isWindow();
			if ( index != -1) {
				peer = model.getPeers().get(index);
				
			// Check if a superviser is selected
			} else if ( (index = view.getSimulPanel().getSelectedSup()) != -1) {
				sup =  model.getSupervisers().get(index);
			} 
			
			if (window) {
				boardDialog = new JBoardDialog(view,
												peer,
												model.getBoard(),
												model.getPeers());
				boardDialog.setVisible(true);
			}
			
		
			
			
			Thread t = new Thread() {
		        public void run() {
		        	// Execute Steps
		        	step = view.getSimulPanel().getNbStep();
		        	
					for (int i = 0; i < step; i++) {
						//boardDialog.dispose();
						// if a peer is selected, make [step] actions
						if (peer != null ) {
							if (!peer.isDead()) {
								peer.makeAleaAction();
							} else {
								peer.getLoggers().addMessage("ERROR : No random action on " + peer.getName() + ", it's dead");
							}
						// if a superviser is selected, make [step] actions
						} else if (sup != null) {
							sup.makeAleaAction();
						// Otherwise select peers and supervisers randomly
						} else {				
							model.makeRandomAction();
						}
						
						if (window) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							
							model.updatePeerModel();
							model.updateSuperviserModel();
							boardDialog.setPeers(model.getPeers());
							boardDialog.repaint();
						}
						
					}
					
					if (window) {
						JMessagePane mw = new JMessagePane(view,
							 "Fin de la simulation, Voulez vous fermer la fenêtre ?",
							 JMessagePane.WINDOW_TYPE_INFO,
							 JMessagePane.OK_OPTION);
	
						if (mw.getSelectValue() == JMessagePane.YES_ANSWER) {
							boardDialog.dispose();
						}
					}
					model.updatePeerModel();
					model.updateSuperviserModel();
					view.getSimulPanel().setBoardDefined();
					view.getBoardPanel().setPeers(model.getPeers());
		        }
		      };
		      
		    t.start();
		    		
				
		}
	}
	
	
}