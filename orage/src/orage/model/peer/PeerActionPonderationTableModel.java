
package orage.model.peer;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import orage.model.PeerCollection;



public class PeerActionPonderationTableModel extends AbstractTableModel {

	private PeerCollection peerList;

	private Object[][] data;

	private String[] columnNames = { "Peer", "Kill","Avg", "Talk", "Query", "Move" };

	public PeerActionPonderationTableModel(PeerCollection list) {
		peerList = list;
		buildData();
	}
	
	public void buildData() {
		Peer peer;
		int size = peerList.size();
		data = null;
		data = new Object[size][6];
		for (int i = 0; i < size; i++) {
			peer = peerList.get(i);
			data[i][0] = peer.getName();
			data[i][1] = String.valueOf(peer.getPonderations().getPonderation(PeerActionKill.class));
			data[i][2] = String.valueOf(peer.getPonderations().getPonderation(PeerActionAvg.class));
			data[i][3] = String.valueOf(peer.getPonderations().getPonderation(PeerActionTalk.class));
			data[i][4] = String.valueOf(peer.getPonderations().getPonderation(PeerActionQuery.class));
			data[i][5] = String.valueOf(peer.getPonderations().getPonderation(PeerActionMove.class));
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
		
		Class c;
		if (columnIndex == 1) c = PeerActionKill.class;
		else if (columnIndex == 2) c = PeerActionAvg.class;
		else if (columnIndex == 3) c = PeerActionTalk.class;
		else if (columnIndex == 4) c = PeerActionQuery.class;
		else if (columnIndex == 5) c = PeerActionMove.class;
		else c = PeerActionQuery.class;
		
		p.getPonderations().setPonderation(c, Integer.parseInt(aValue.toString()));
		
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