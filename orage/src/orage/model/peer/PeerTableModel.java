
package orage.model.peer;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import orage.model.PeerCollection;


public class PeerTableModel extends AbstractTableModel {

	private PeerCollection peerList;

	private Object[][] data;

	private String[] columnNames = { "Peer", "Liar", "Color", "Kill", "Talk",
			"Query", "Move", "Avg" };

	public PeerTableModel(PeerCollection list) {
		peerList = list;
		buildData();
	}
	
	public void buildData() {
		Peer peer;
		int size = peerList.size();
		data = null;
		data = new Object[size][8];
		for (int i = 0; i < size; i++) {
			peer = peerList.get(i);
			data[i][0] = peer.getName();
			data[i][1] = new Boolean(peer.isLiar());
			data[i][2] = peer.getColor();
			data[i][3] = "Kill";
			data[i][4] = "Talk";
			data[i][5] = "Query";
			data[i][6] = "Move";
			data[i][7] = "Avg";
		}
		
		fireTableChanged(new TableModelEvent(this));
	}
	
	public boolean isCellEditable(int row, int col) {
        if (col >= 2) return true;
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

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	public void setPeerList(PeerCollection list) {
		this.peerList = list;
		buildData();
	}
}