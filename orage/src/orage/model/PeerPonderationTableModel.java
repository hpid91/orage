
package orage.model;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import orage.model.peer.Peer;


public class PeerPonderationTableModel extends AbstractTableModel {

	private PeerCollection peerList;

	private Object[][] data;

	private String[] columnNames = { "Peer", "Ponderation" };

	public PeerPonderationTableModel(PeerCollection list) {
		peerList = list;
		buildData();
	}
	
	public void buildData() {
		Peer peer;
		int size = peerList.size();
		data = null;
		data = new Object[size][2];
		for (int i = 0; i < size; i++) {
			peer = peerList.get(i);
			data[i][0] = peer.getName();
			data[i][1] = String.valueOf(peer.getPonderation());
		}
		
		fireTableChanged(new TableModelEvent(this));
	}
	
	public boolean isCellEditable(int row, int col) {
        if (col != 0) return true;
		else return false;
        
    }
	
	public void add(Peer peer) {
		peerList.add(peer);
		buildData();
	}
	
	public void remove(Peer peer) {
		peerList.remove(peer);
		buildData();
	}

	public void remove(int index) {
		peerList.remove(index);
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
		return peerList.size();
	}
	
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
		
		Peer p = peerList.get(rowIndex);
				
		p.setPonderation( Integer.parseInt(aValue.toString()));
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setPeerList(PeerCollection list) {
		this.peerList = list;
		buildData();
	}
}