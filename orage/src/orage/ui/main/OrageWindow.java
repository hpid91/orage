
package orage.ui.main;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.Document;

import orage.ui.board.JBoardPanel;
import orage.ui.peer.JColorChooserDialog;
import orage.ui.peer.JPeerDialog;
import orage.ui.peer.JPeerPanel;
import orage.ui.simulation.JPreferencesPanel;
import orage.ui.simulation.JSimulPanel;
import orage.ui.simulation.JSuperviserDialog;
import orage.ui.simulation.JSuperviserPanel;


public class OrageWindow extends JFrame {

	// Controller
	private ActionListener listener;
	
	// graphical elements
	private JLabel lblWelcome;
	private JColorChooserDialog colorChooserDialog;
	private JPeerDialog createPeerDialog;
	private JSuperviserDialog createSuperviserDialog;
	private OrageMenuBar menubar;
	private JPeerPanel peerPanel;
	private JBoardPanel boardPanel;
	private JSimulPanel simulPanel;
	private JPreferencesPanel preferencesPanel;
	private JSuperviserPanel superviserPanel;
	
	public OrageWindow (ActionListener l) {
		super();
		listener = l;
		init();
	}
		
	private void init() {
				
		setTitle("Orage");
		setIconImage(Toolkit.getDefaultToolkit().getImage("icons/logoSmall.png"));
		
		menubar = new OrageMenuBar(listener);
		
		setJMenuBar(menubar);

		lblWelcome = new JLabel(new ImageIcon("icons/logo.png"));
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
		
		this.getContentPane().add(lblWelcome);
		setLocation(200,200);
		setResizable(false);
		this.setVisible(true);
		this.pack();
		
	}
	
	public void reinit() {

		this.getContentPane().removeAll();
		this.getContentPane().add(lblWelcome);
		this.pack();
	}
	
	public ActionListener getListener() {
		return listener;
	}
	
	public JColorChooserDialog getColorChooserDialog() {
		return colorChooserDialog;
	}
	
	public void setColorChooserDialog(JColorChooserDialog colorChooserDialog) {
		this.colorChooserDialog = colorChooserDialog;
	}
	
	public void closeColorChooserDialog() {
		if (this.colorChooserDialog  != null) {
			this.colorChooserDialog.hide();
			this.colorChooserDialog = null;
		}
	}
	
	public JSimulPanel getSimulPanel() {
		return simulPanel;
	}
	
	public void setSimulPanel() {
		this.getContentPane().removeAll();
		this.getContentPane().add(this.simulPanel);
		this.pack();
	}
	
	public void buildSimulPanel(JPeerPanel peerPanel,
								JSuperviserPanel supPanel,
								JBoardPanel boardPanel,
								Document doc) {
		this.simulPanel = null;
		this.peerPanel = peerPanel;
		this.boardPanel = boardPanel;
		this.superviserPanel = supPanel;
		
		this.simulPanel = new JSimulPanel(listener, peerPanel,superviserPanel, boardPanel, doc);
		
		this.getContentPane().removeAll();
		this.getContentPane().add(this.simulPanel);
		this.pack();
		
	}
	
	public JPeerDialog getCreatePeerDialog() {
		return createPeerDialog;
	}
	
	public void setCreatePeerDialog(JPeerDialog createPeerDialog) {
		this.createPeerDialog = createPeerDialog;
	}
	
	public void closeCreatePeerDialog() {
		if (this.createPeerDialog  != null) {
			this.createPeerDialog.dispose();
			this.createPeerDialog = null;
		}
	}
	
	public JPeerPanel getPeerPanel() {
		return peerPanel;
	}
	
	public void setPeerPanel(JPeerPanel peerPanel) {
		this.peerPanel = null;
		this.peerPanel = peerPanel;
	}
	
	public JBoardPanel getBoardPanel() {
		return boardPanel;
	}
	
	public void setBoardPanel(JBoardPanel boardPanel) {
		this.boardPanel = null;
		this.boardPanel = boardPanel;
	}
	
	public JPreferencesPanel getPreferencesPanel() {
		return preferencesPanel;
	}
	
	public void setPreferencesPanel(JPreferencesPanel preferencesPanel) {
		this.preferencesPanel = null;
		this.preferencesPanel = preferencesPanel;
		
		this.getContentPane().removeAll();
		this.getContentPane().add(this.preferencesPanel);
		this.pack();
	}
	
	public OrageMenuBar getMenubar() {
		return menubar;
	}
	
	public JSuperviserDialog getCreateSuperviserDialog() {
		return createSuperviserDialog;
	}
	
	public void setCreateSuperviserDialog(JSuperviserDialog createSuperviserDialog) {
		this.createSuperviserDialog = createSuperviserDialog;
	}
	
	public void closeCreateSuperviserDialog() {
		if (this.createSuperviserDialog  != null) {
			this.createSuperviserDialog.dispose();
			this.createSuperviserDialog = null;
		}
	}
	
	public JSuperviserPanel getSuperviserPanel() {
		return superviserPanel;
	}
	
	public void setSuperviserPanel(JSuperviserPanel superviserPanel) {
		this.superviserPanel = null;
		this.superviserPanel = superviserPanel;
		
		this.getContentPane().removeAll();
		this.getContentPane().add(this.superviserPanel);
		this.pack();
	}
}
