
package orage.model;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserCollection;


public class SuperviserPonderationTableModel extends AbstractTableModel {

	private SuperviserCollection supList;

	private Object[][] data;

	private String[] columnNames = { "Superviser", "Ponderation" };

	public SuperviserPonderationTableModel(SuperviserCollection list) {
		supList = list;
		buildData();
	}
	
	public void buildData() {
		Superviser sup;
		int size = supList.size();
		data = null;
		data = new Object[size][2];
		for (int i = 0; i < size; i++) {
			sup = supList.get(i);
			data[i][0] = sup.getName();
			data[i][1] = String.valueOf(sup.getPonderation());
		}
		
		fireTableChanged(new TableModelEvent(this));
	}
	
	public boolean isCellEditable(int row, int col) {
        if (col != 0) return true;
		else return false;
        
    }
	
	public void add(Superviser sup) {
		supList.add(sup);
		buildData();
	}
	
	public void remove(Superviser sup) {
		supList.remove(sup);
		buildData();
	}

	public void remove(int index) {
		supList.remove(index);
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
		return supList.size();
	}
	
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
		
		Superviser s = supList.get(rowIndex);
				
		s.setPonderation( Integer.parseInt(aValue.toString()));
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setSupList(SuperviserCollection list) {
		this.supList = list;
		buildData();
	}
}