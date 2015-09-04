package orage.ui.simulation;


import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import orage.control.Controller;




public class ButtonSuperviserEditor extends JButton implements TableCellEditor {

	protected EventListenerList listenerList = new EventListenerList();

	protected ChangeEvent changeEvent = new ChangeEvent(this);

	private int row;
	private String action;
	private int column;
	
	private ActionListener listener;
	
	public ButtonSuperviserEditor(String action, ActionListener l) {
		super();
		listener = l;
		this.action = action;
		setText("Do");
		addActionListener(listener);
	}

	public void addCellEditorListener(CellEditorListener listener) {
		listenerList.add(CellEditorListener.class, listener);
	}

	public void removeCellEditorListener(CellEditorListener listener) {
		listenerList.remove(CellEditorListener.class, listener);
	}

	public void cancelCellEditing() {
		//fireEditingCanceled();
	}

	public boolean stopCellEditing() {
		//fireEditingStopped();
		return true;
	}

	public boolean isCellEditable(EventObject event) {
		return true;
	}

	public boolean shouldSelectCell(EventObject event) {
		return true;
	}

	//fonction appelée au clic
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		setActionCommand(Controller.AC_TABLE_SUP + action + "#" + row );
		setText(getText());
		return this;
	}

	public Object getCellEditorValue() {
		return this;
	}
}