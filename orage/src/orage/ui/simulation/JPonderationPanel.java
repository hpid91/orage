
package orage.ui.simulation;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import orage.model.PeerPonderationTableModel;
import orage.model.SuperviserPonderationTableModel;
import orage.model.peer.PeerActionPonderationTableModel;
import orage.model.superviser.SuperviserActionPonderationTableModel;

public class JPonderationPanel extends JPanel implements TableModelListener {

	// Controller
	private ActionListener listener;

	// Graphical element
	private JTextField txtPeerSupPonderation;
	private JTable tablePeer;
	private JTable tableSup;
	private JTable tablePeerAction;
	private JTable tableSupAction;

	// Model
	private float peersupponderation;
	private PeerActionPonderationTableModel tablePAModel;
	private SuperviserActionPonderationTableModel tableSAModel;
	private PeerPonderationTableModel tablePModel;
	private SuperviserPonderationTableModel tableSModel;
	
	
	public JPonderationPanel(ActionListener l,
			float psponderation,
			PeerPonderationTableModel pmodel,
			SuperviserPonderationTableModel smodel,
			PeerActionPonderationTableModel pamodel,
			SuperviserActionPonderationTableModel samodel) {
		super();
		listener = l;
		peersupponderation = psponderation;
		tablePAModel = pamodel;
		tableSAModel = samodel;
		tablePModel = pmodel;
		tableSModel = smodel;
		init();

	}

	private void init() {
		// PEER/SUPERVISER REPARTITION PONDERATION
		JLabel lblPeerSupPonderation = new JLabel("Peer/Superviser action repartition");
		txtPeerSupPonderation = new JTextField(String.valueOf(peersupponderation));
		JPanel panelPeerSup = new JPanel();
		panelPeerSup.setLayout(new BoxLayout(panelPeerSup, BoxLayout.X_AXIS));
		panelPeerSup.add(Box.createRigidArea(new Dimension(10, 0)));
		panelPeerSup.add(lblPeerSupPonderation);
		panelPeerSup.add(Box.createRigidArea(new Dimension(10, 0)));
		panelPeerSup.add(txtPeerSupPonderation);
		panelPeerSup.add(Box.createRigidArea(new Dimension(10, 0)));
		
		// SUPERVISER TABLE
		tableSModel.addTableModelListener(this);
		tableSup = new JTable(tableSModel);

		tableSup.setRowHeight(20);
		tableSup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		JScrollPane scrollPaneSup = new JScrollPane(tableSup,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneSup.setPreferredSize(new Dimension(150, 150));

		
		
		// SUPERVISER ACTION TABLE
		tableSAModel.addTableModelListener(this);
		tableSupAction = new JTable(tableSAModel);

		tableSupAction.setRowHeight(20);
		tableSupAction.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPaneSupAction = new JScrollPane(tableSupAction,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneSupAction.setPreferredSize(new Dimension(350, 150));

		JPanel panelSup = new JPanel();
		panelSup.setBorder(BorderFactory
				.createTitledBorder("Superviser ponderations"));
		panelSup.add(scrollPaneSup);
		panelSup.add(scrollPaneSupAction);

		// PEERS TABLE
		tablePModel.addTableModelListener(this);
		tablePeer = new JTable(tablePModel);

		tablePeer.setRowHeight(20);
		tablePeer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPanePeer = new JScrollPane(tablePeer,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanePeer.setPreferredSize(new Dimension(150, 150));
				
		// PEERS ACTION TABLE
		tablePAModel.addTableModelListener(this);
		tablePeerAction = new JTable(tablePAModel);

		tablePeerAction.setRowHeight(20);
		tablePeerAction.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPanePeerAction = new JScrollPane(tablePeerAction,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanePeerAction.setPreferredSize(new Dimension(350, 150));
		
		JPanel panelPeer = new JPanel();
		panelPeer.setBorder(BorderFactory.createTitledBorder("Peers ponderations"));
		panelPeer.add(scrollPanePeer);
		panelPeer.add(scrollPanePeerAction);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(panelPeerSup);
		add(panelSup);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(panelPeer);

	}

	public JTable getTablePeerAction() {
		return tablePeerAction;
	}

	public JTable getTableSupAction() {
		return tableSupAction;
	}

	public void tableChanged(TableModelEvent e) {

		//tablePeerAction.setModel(tablePAModel);
		//tablePeerAction.validate();
	}

	public float getPeerSupPonderation() {
		return Float.parseFloat(txtPeerSupPonderation.getText());
	}
}