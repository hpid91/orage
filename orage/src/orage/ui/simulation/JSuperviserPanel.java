package orage.ui.simulation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import orage.control.Controller;
import orage.model.superviser.SuperviserAction;
import orage.model.superviser.SuperviserTableModel;
import orage.ui.common.ButtonRenderer;


public class JSuperviserPanel extends JPanel implements TableModelListener,
		 ActionListener, MouseListener {

	// Controller
	private ActionListener listener;

	// Graphical element
	private JTable tableSup;
	private JTextArea txtaLogs;

	// Model
	private SuperviserTableModel tableModel;


	private final static String ERASE_LOG = "ERASE_LOG";
	
	public JSuperviserPanel(ActionListener l, SuperviserTableModel model) {
		super();
		listener = l;
		tableModel = model;

		init();

	}

	private void init() {

		// SUPERVISERS TABLE
		tableModel.addTableModelListener(this);
		tableSup = new JTable(tableModel);
		tableSup.addMouseListener(this);
		tableSup.setRowHeight(20);
		tableSup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn tc = tableSup.getColumn("+ peer");
		tc.setCellRenderer(new ButtonRenderer(SuperviserAction.ADDPEER_KIND));
		tc.setCellEditor(new ButtonSuperviserEditor(SuperviserAction.ADDPEER_KIND, listener));
		
		tc = tableSup.getColumn("+ known peer");
		tc.setCellRenderer(new ButtonRenderer(SuperviserAction.ADDKPEER_KIND));
		tc.setCellEditor(new ButtonSuperviserEditor(SuperviserAction.ADDKPEER_KIND, listener));
		
		tc = tableSup.getColumn("- peer");
		tc.setCellRenderer(new ButtonRenderer(SuperviserAction.REMPEER_KIND));
		tc.setCellEditor(new ButtonSuperviserEditor(SuperviserAction.REMPEER_KIND, listener));
		
		tc = tableSup.getColumn("- known peer");
		tc.setCellRenderer(new ButtonRenderer(SuperviserAction.REMKPEER_KIND));
		tc.setCellEditor(new ButtonSuperviserEditor(SuperviserAction.REMKPEER_KIND, listener));
		
		tc = tableSup.getColumn("diagnose");
		tc.setCellRenderer(new ButtonRenderer(SuperviserAction.DIAGNOSE_KIND));
		tc.setCellEditor(new ButtonSuperviserEditor(SuperviserAction.DIAGNOSE_KIND, listener));
		
		JScrollPane scrollPane = new JScrollPane(tableSup);

		// GENERAL ACTIONS
		JButton btCreate = new JButton("Add");
		btCreate.setActionCommand(Controller.AC_OPEN_CREATE_SUP);
		btCreate.addActionListener(listener);

		JButton btModif = new JButton("Modify");
		btModif.setActionCommand(Controller.AC_OPEN_MODIF_SUP);
		btModif.addActionListener(listener);

		JButton btRemove = new JButton("Remove");
		btRemove.setActionCommand(Controller.AC_REMOVE_SUP);
		btRemove.addActionListener(listener);
		
		// PANEL BUTTON
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		panelButton.add(Box.createHorizontalGlue());
		panelButton.add(btCreate);
		panelButton.add(Box.createRigidArea(new Dimension(5, 0)));
		panelButton.add(btModif);
		panelButton.add(Box.createRigidArea(new Dimension(5, 0)));
		panelButton.add(btRemove);
	
		// PANEL DATA
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(panelButton);
		setBorder(BorderFactory.createTitledBorder("Supervisers"));
		setPreferredSize(new Dimension(400,400));	

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ERASE_LOG)) txtaLogs.setText("");

	}
	
	public void tableChanged(TableModelEvent e) {
		tableSup.setModel(tableModel);
		tableSup.validate();
	}
	
	public int getSelectedSup() {
		return tableSup.getSelectedRow();
	}
	
	public void setSelectedSup(int index) {
		tableSup.setRowSelectionInterval(index, index);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1) {
			tableSup.clearSelection();
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