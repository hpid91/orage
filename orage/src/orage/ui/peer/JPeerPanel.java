package orage.ui.peer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import orage.control.Controller;
import orage.model.peer.PeerAction;
import orage.model.peer.PeerTableModel;
import orage.ui.common.ButtonRenderer;
import orage.ui.common.ColorRenderer;

public class JPeerPanel extends JPanel implements TableModelListener, MouseListener {
	
	// Controller
	private ActionListener listener;
	
	// Graphical element
	private JTable tablePeer;
	private JSpinner spinStep;
	private JCheckBox ckWindow;
	
	
	// Model
	private PeerTableModel tableModel;
	
	public JPeerPanel(ActionListener l, PeerTableModel model) {
		super();
		listener = l;
		tableModel = model;
		init();
		
	}
	
	private void init() {
		
		// PEERS TABLE
		tableModel.addTableModelListener(this);
		tablePeer = new JTable(tableModel);
		tablePeer.addMouseListener(this);
		tablePeer.setRowHeight(20);
		
		tablePeer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePeer.setDefaultRenderer(Color.class,
									new ColorRenderer(true));
		
		TableColumn tc = tablePeer.getColumn("Talk");
		tc.setCellRenderer(new ButtonRenderer(PeerAction.TALK_KIND));
		tc.setCellEditor(new ButtonPeerEditor(PeerAction.TALK_KIND, listener));
		
		tc = tablePeer.getColumn("Kill");
		tc.setCellRenderer(new ButtonRenderer("Kill"));
		tc.setCellEditor(new ButtonPeerEditor("Kill", listener));
		
		tc = tablePeer.getColumn("Query");
		tc.setCellRenderer(new ButtonRenderer(PeerAction.QUERY_KIND));
		tc.setCellEditor(new ButtonPeerEditor(PeerAction.QUERY_KIND, listener));
		
		tc = tablePeer.getColumn("Move");
		tc.setCellRenderer(new ButtonRenderer(PeerAction.MOVE_KIND));
		tc.setCellEditor(new ButtonPeerEditor(PeerAction.MOVE_KIND, listener));
		
		tc = tablePeer.getColumn("Avg");
		tc.setCellRenderer(new ButtonRenderer(PeerAction.AVG_KIND));
		tc.setCellEditor(new ButtonPeerEditor(PeerAction.AVG_KIND, listener));
		
		JScrollPane scrollPane = new JScrollPane(tablePeer);
		
		
		// STEP BY STEP PANEL
		JPanel panelStep = new JPanel();
		panelStep.setBorder(BorderFactory.createTitledBorder("Steps"));
		
		
		JLabel lblSteps = new JLabel("Steps :");
		SpinnerNumberModel snm = new SpinnerNumberModel(5,0,1000,1);
		spinStep = new JSpinner(snm);
		
		JButton btStep = new JButton("Launch");
		btStep.setActionCommand(Controller.AC_STEP);
		btStep.addActionListener(listener);
		btStep.setPreferredSize(new Dimension(20, 25));
		
		ckWindow = new JCheckBox("Simulation Window", true);
		
		panelStep.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.weightx = 1.5;
		c.weighty = 1.5;
		
		c.ipadx = 20;  
		c.gridx = 0;
		c.gridy = 0;
		panelStep.add(lblSteps, c);
		
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 0;
		panelStep.add(spinStep, c);
		
		c.ipady = 0;
		c.gridx = 2;
		c.gridy = 0;
		panelStep.add(ckWindow, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0,10,0,0);
		c.gridx = 3;
		c.gridy = 0;
		panelStep.add(btStep, c);
		panelStep.setPreferredSize(new Dimension(250,70));
		
		// GENERAL ACTIONS
		JButton btView = new JButton("View");
		btView.setActionCommand(Controller.AC_VIEW_PEER);
		btView.addActionListener(listener);
				
		JButton btCreate = new JButton("Add");
		btCreate.setActionCommand(Controller.AC_OPEN_CREATE_PEER);
		btCreate.addActionListener(listener);
		
		JButton btModif = new JButton("Modify");
		btModif.setActionCommand(Controller.AC_OPEN_MODIF_PEER);
		btModif.addActionListener(listener);
		
		JButton btRemove = new JButton("Remove");
		btRemove.setActionCommand(Controller.AC_REMOVE_PEER);
		btRemove.addActionListener(listener);
		
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		panelButton.add(Box.createHorizontalGlue());
		panelButton.add(btView);
		panelButton.add(Box.createRigidArea(new Dimension(15, 0)));
		panelButton.add(btCreate);
		panelButton.add(Box.createRigidArea(new Dimension(5, 0)));
		panelButton.add(btModif);
		panelButton.add(Box.createRigidArea(new Dimension(5, 0)));
		panelButton.add(btRemove);
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(panelStep);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(panelButton);
		setPreferredSize(new Dimension(300,300));
		setBorder(BorderFactory.createTitledBorder("Peers"));
		
	}
	
	public JTable getTablePeer() {
		return tablePeer;
	}
	
	
	public void tableChanged(TableModelEvent e) {
		tablePeer.setModel(tableModel);
		tablePeer.validate();
	}
	
	public int getSelectedPeer() {
		return tablePeer.getSelectedRow();
	}
	
	public boolean isWindow() {
		return ckWindow.isSelected();
		
	}
	public void setButtonEnabled(boolean b) {
		TableColumn tc = tablePeer.getColumn("Talk");
		
		ButtonRenderer renderer = (ButtonRenderer)tc.getCellRenderer();
		renderer.setEnabled(b);
		ButtonPeerEditor editor = (ButtonPeerEditor) tc.getCellEditor();
		editor.setEnabled(b);
		
		tc = tablePeer.getColumn("Query");
		renderer = (ButtonRenderer)tc.getCellRenderer();
	    renderer.setEnabled(b);
	    editor = (ButtonPeerEditor) tc.getCellEditor();
		editor.setEnabled(b);
		
		tc = tablePeer.getColumn("Kill");
		renderer = (ButtonRenderer)tc.getCellRenderer();
	    renderer.setEnabled(b);
	    editor = (ButtonPeerEditor) tc.getCellEditor();
		editor.setEnabled(b);
		
		
		tc = tablePeer.getColumn("Move");
		renderer = (ButtonRenderer)tc.getCellRenderer();
	    renderer.setEnabled(b);
	    editor = (ButtonPeerEditor) tc.getCellEditor();
		editor.setEnabled(b);
		
		
		
	    repaint();
	}
	
	public void setAButtonEnabled(boolean b, int row, String colName) {
		TableColumn tc = tablePeer.getColumn(colName);
		int col = tc.getModelIndex();
		
	
		/*ButtonRenderer renderer = (ButtonRenderer)tablePeer.getCellRenderer(row, col);
	    renderer.setEnabled(b);*/
		ButtonPeerEditor tce = (ButtonPeerEditor) tablePeer.getCellEditor(row, col);
		tce.setEnabled(b);

		
	}
	
	public int getNbStep() {
		return ((Integer)spinStep.getValue()).intValue();
	}
	
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1) {
			tablePeer.clearSelection();
		}

	}
	public void mouseEntered(MouseEvent e) {
	
	}
	public void mouseExited(MouseEvent e) {
		

	}
	public void mousePressed(MouseEvent e) {
		

	}
	public void mouseReleased(MouseEvent e) {
	

	}
}
