
package orage.model.superviser;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;


public class SuperviserActionPonderationTableModel extends AbstractTableModel {

	private SuperviserCollection supervisers;

	private Object[][] data;

	private String[] columnNames = { "Superviser", "+ peer", "+ known peer", "- peer", "- known peer" };

	public SuperviserActionPonderationTableModel(SuperviserCollection list) {
		supervisers = list;
		buildData();
	}
	
	public void buildData() {
		Superviser sup;
		int size = supervisers.size();
		data = null;
		data = new Object[size][5];
		for (int i = 0; i < size; i++) {
			sup = supervisers.get(i);
			data[i][0] = sup.getName();
			data[i][1] = String.valueOf(sup.getPonderations().getPonderation(SuperviserActionAddPeer.class));
			data[i][2] = String.valueOf(sup.getPonderations().getPonderation(SuperviserActionAddKPeer.class));
			data[i][3] = String.valueOf(sup.getPonderations().getPonderation(SuperviserActionRemovePeer.class));
			data[i][4] = String.valueOf(sup.getPonderations().getPonderation(SuperviserActionRemoveKPeer.class));
		}
		
		fireTableChanged(new TableModelEvent(this));
	}
	
	public boolean isCellEditable(int row, int col) {
        if (col != 0) return true;
		else return false;
        
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
	
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
		
		Superviser sup = supervisers.get(rowIndex);
		
		Class c;
		if (columnIndex == 1) c = SuperviserActionAddPeer.class;
		else if (columnIndex == 2) c = SuperviserActionAddKPeer.class;
		else if (columnIndex == 3) c = SuperviserActionRemovePeer.class;
		else if (columnIndex == 4) c = SuperviserActionRemoveKPeer.class;
		else c = SuperviserActionAddKPeer.class;
	
		
		
		sup.getPonderations().setPonderation(c, Integer.parseInt(aValue.toString()));
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setSupervisers(SuperviserCollection list) {
		this.supervisers = list;
		buildData();
	}
}