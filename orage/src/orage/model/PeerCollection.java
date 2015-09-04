
package orage.model;

import java.util.ArrayList;

import orage.model.peer.Peer;


public class PeerCollection {
	
	private ArrayList list;
	
	public PeerCollection() {
		list = new ArrayList();
	}
	
	public PeerCollection (PeerCollection toCopy) {
		list = new ArrayList();
		int size = toCopy.size();
		
		for (int i = 0; i < size; i ++) {
			list.add(toCopy.get(i));
		}
		
	}
	
	public PeerCollection(ArrayList list) {
		this.list = list;
	}
	
	public void add (Peer peer) {
		list.add(peer);
		
	}
	
	public void remove (int index) {
		list.remove (index);
	}
	
	public void remove (Peer peer) {
		list.remove (peer);
	}
	
	public int indexOf(Peer peer) {
		return list.indexOf(peer);
	}
	public Peer get(int index) {
		return (Peer)list.get(index);
	}
	
	public String[] getListNames() {
		int size = list.size();
		String[] names = new String[size];
		
		String name = "";
		
		for (int i = 0; i < size; i++) {
			name = ((Peer)list.get(i)).getName();
			names[i] = name;
		}
		
		return names;
	}
	
	public Peer getFromId(String id) {
		int size = list.size();
		Peer result = null;
		
		for (int i = 0; i < size; i++) {
			if (((Peer)list.get(i)).getId().equals(id)) {
				result = (Peer)list.get(i);
			}
		}
		return result;
	}
	
	public ArrayList getList() {
		return list;
	}
	
	public int size() {
		return list.size();
	}
	
	public boolean isIn(Peer p) {
		return list.contains(p);
	}
	
	public void addAll(PeerCollection peers) {
		list.addAll(peers.list);
	}
}
