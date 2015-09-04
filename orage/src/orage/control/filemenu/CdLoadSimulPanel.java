package orage.control.filemenu;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import orage.control.Command;
import orage.model.Board;
import orage.model.Model;
import orage.model.PeerCollection;
import orage.model.PeerXMLLoader;
import orage.model.superviser.SuperviserCollection;
import orage.ui.board.JBoardPanel;
import orage.ui.common.XmlSelectFilter;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerPanel;
import orage.ui.simulation.JSuperviserPanel;

import org.jdom.JDOMException;

public class CdLoadSimulPanel  extends Command {
	
	private Model newModel;
	
	public CdLoadSimulPanel(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JFileChooser chooser = new JFileChooser();
		
		FileFilter filter = new XmlSelectFilter();
		
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false) ;
		
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setApproveButtonText("Select");
		
		int state = chooser.showSaveDialog(view);
		
		File choosen = chooser.getSelectedFile();
		if ( (choosen != null) && (state == JFileChooser.APPROVE_OPTION) ){
						
			String s = choosen.getAbsolutePath().replaceAll("\\\\", "/");
			
			try {
				PeerXMLLoader.load(s);
				Board newboard = PeerXMLLoader.loadBoard();
				PeerCollection allpeers = PeerXMLLoader.loadPeers(newboard);
				SuperviserCollection supervisers = PeerXMLLoader.loadSupervisers(newboard, allpeers);
				
				newModel = new Model(newboard, supervisers);
				newModel.setPeersSuperviserPonderation(PeerXMLLoader.getPeerSupPonderation());		
				
				JPeerPanel peerPanel = new JPeerPanel(view.getListener(), newModel.getPeerTableModel());
				JBoardPanel boardPanel = new JBoardPanel(view.getListener(), newModel.getBoard(), newModel.getPeers());
								
				JSuperviserPanel supPanel = new JSuperviserPanel(view.getListener(), newModel.getSupervisersTableModel());

				view.buildSimulPanel(peerPanel, supPanel, boardPanel, newModel.getDocument().getDocument());

				view.getMenubar().setSimuMenuEnabled(true);
			} catch (JDOMException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}

	}
	
	
	public Model getNewModel() {
		return newModel;
	}
}