
package orage.model.superviser;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;


public class SuperviserTableModel extends AbstractTableModel {

	private SuperviserCollection supervisers;

	private Object[][] data;

	private String[] columnNames = { "Superviser", "+ peer", "+ known peer", "- peer", "- known peer", "diagnose" };

	public SuperviserTableModel(SuperviserCollection list) {
		supervisers = list;
		buildData();
	}
	
	public void buildData() {
		Superviser sup;
		int size = supervisers.size();
		data = null;
		data = new Object[size][6];
		for (int i = 0; i < size; i++) {
			sup = supervisers.get(i);
			data[i][0] = sup.getName();
			data[i][1] = "Add a Peer";
			data[i][2] = "Add a known peer";
			data[i][3] = "Remove a peer";
			data[i][4] = "Remove a known peer";
			data[i][5] = "Diagnose";
		}
		
		fireTableChanged(new TableModelEvent(this));
	}
	
	public boolean isCellEditable(int row, int col) {
        
		if (col == 0) return false;
		else return true;
        
    }
	
	public void add(Superviser sup) {
		supervisers.add(sup);
		buildData();
	}
	
	public void remove(Superviser sup) {
		supervisers.remove(sup);
		buildData();
	}

	public void remove(int index) {
		supervisers.remove(index);
		buildData();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public int getRowCount() {
		return supervisers.size();
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setSupervisers(SuperviserCollection list) {
		this.supervisers = list;
		buildData();
	}
}