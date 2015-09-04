package orage.ui.simulation;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import orage.control.Controller;
import orage.model.PeerPonderationTableModel;
import orage.model.SuperviserPonderationTableModel;
import orage.model.peer.PeerActionPonderationTableModel;
import orage.model.superviser.SuperviserActionPonderationTableModel;
import orage.ui.common.JPanelButton;

public class JPreferencesPanel extends JPanel {
	
	// Controller
	private ActionListener listener;
	
	// Model elements
	private float peersupponderation;
	private PeerPonderationTableModel peerPonderationTableModel;
	private SuperviserPonderationTableModel superviserPonderationTableModel;
	private PeerActionPonderationTableModel peersActionPonderationModel;
	private SuperviserActionPonderationTableModel superviserActionPonderationTableModel;
	private ArrayList actionsLogged;
	
	// Graphical elements
	private JPonderationPanel ponderationPanel;
	private JConfigLogPanel configLogPanel;
		
	public JPreferencesPanel(ActionListener l,
							float psponderation,
							PeerPonderationTableModel peerPonderationTableModel,
							SuperviserPonderationTableModel superviserPonderationTableModel,
							PeerActionPonderationTableModel peersActionPonderationModel,
							SuperviserActionPonderationTableModel superviserActionPonderationTableModel,
							ArrayList actionsLogged) {
		super();
		listener = l;
		this.peersupponderation = psponderation;
		this.peerPonderationTableModel = peerPonderationTableModel;
		this.superviserPonderationTableModel = superviserPonderationTableModel;
		this.peersActionPonderationModel = peersActionPonderationModel;
		this.superviserActionPonderationTableModel = superviserActionPonderationTableModel;
		this.actionsLogged = actionsLogged;
		
		init();
	}
	
	private void init() {
		ponderationPanel = new JPonderationPanel(listener,
												peersupponderation,
												peerPonderationTableModel,
												superviserPonderationTableModel,
												peersActionPonderationModel,
												superviserActionPonderationTableModel);
		
		configLogPanel = new JConfigLogPanel(actionsLogged);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("Ponderations", ponderationPanel);
		tabPane.add("Log", configLogPanel);
		
		JPanelButton panelButton = new JPanelButton();
		
		JButton btSet = new JButton("Set");
		btSet.addActionListener(listener);
		btSet.setActionCommand(Controller.AC_SET_PREFERENCES);
		
		JButton btClose = new JButton("Close");
		btClose.addActionListener(listener);
		btClose.setActionCommand(Controller.AC_BACK_TO_SIMUL);
		
		panelButton.add(btSet);
		panelButton.add(btClose);

		setLayout(new BorderLayout());
		add(tabPane, BorderLayout.CENTER);
		add(panelButton, BorderLayout.SOUTH);
	}

	public JPonderationPanel getPonderationPanel() {
		return ponderationPanel;
	}
	
	public float getPeersupponderation() {
		return ponderationPanel.getPeerSupPonderation();
	}
	
	public ArrayList getActionsLogged() {
		return configLogPanel.getActionsLogged();
	}
}