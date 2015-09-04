package orage.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import orage.model.peer.Peer;
import orage.model.peer.PeerActionPonderationTableModel;
import orage.model.peer.PeerTableModel;
import orage.model.superviser.ActionLoggerDocument;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserActionPonderationTableModel;
import orage.model.superviser.SuperviserCollection;
import orage.model.superviser.SuperviserLogSaver;
import orage.model.superviser.SuperviserTableModel;


public class Model {
	
	// Model elements
	private PeerCollection peers;
	private Board board;
	private SuperviserCollection supervisers;
	private ArrayList actionsLogged;
	
	private float peersSuperviserPonderation = 0.95f;
	private int PEER_POND_SUM = 0;
	private int SUP_POND_SUM = 0;
	
	// Table models
	private SuperviserTableModel supervisersTableModel;
	private SuperviserActionPonderationTableModel superviserActionPonderationTableModel;
	private PeerTableModel peerTableModel;
	private PeerActionPonderationTableModel peerActionPonderationTableModel;
	private SuperviserPonderationTableModel superviserPonderationTableModel;
	private PeerPonderationTableModel peerPonderationTableModel;
	
	// Regroup the action loggers of the peers
	private ActionLoggerDocument document;
		
	// Random
	private Random rd = new Random();
	
	public Model() {
		peers = new PeerCollection();
		supervisers = new SuperviserCollection();
		
		peerTableModel = new PeerTableModel(peers);
		peerPonderationTableModel = new PeerPonderationTableModel(peers);
		peerActionPonderationTableModel = new PeerActionPonderationTableModel(peers);
		
		supervisersTableModel = new SuperviserTableModel(supervisers);
		superviserPonderationTableModel = new SuperviserPonderationTableModel(supervisers);
		superviserActionPonderationTableModel =  new SuperviserActionPonderationTableModel(supervisers);
		
		buildActionLoggerDocument();
		
		initActionsLogged();
		
		rd.setSeed(System.currentTimeMillis());
	}
	
	public Model(Board board) {
		this.board = board;
		peers = new PeerCollection();
		supervisers = new SuperviserCollection();
		peerTableModel = new PeerTableModel(peers);
		peerPonderationTableModel = new PeerPonderationTableModel(peers);
		peerActionPonderationTableModel = new PeerActionPonderationTableModel(peers);
		
		supervisersTableModel = new SuperviserTableModel(supervisers);
		superviserPonderationTableModel = new SuperviserPonderationTableModel(supervisers);
		superviserActionPonderationTableModel =  new SuperviserActionPonderationTableModel(supervisers);
		
		document =  new ActionLoggerDocument();
		buildActionLoggerDocument();
		
		initActionsLogged();
		
		rd.setSeed(System.currentTimeMillis());
		
	}
	
	public Model(Board board, SuperviserCollection supervisers) {
		this.board = board;
		this.supervisers = supervisers;
		
		// Build peers list from supervisers
		buildPeerList();
		
		peerTableModel = new PeerTableModel(peers);
		peerPonderationTableModel = new PeerPonderationTableModel(peers);
		peerActionPonderationTableModel = new PeerActionPonderationTableModel(peers);
		
		supervisersTableModel = new SuperviserTableModel(supervisers);
		superviserPonderationTableModel = new SuperviserPonderationTableModel(supervisers);
		superviserActionPonderationTableModel =  new SuperviserActionPonderationTableModel(supervisers);

		computePeerPonderation();
		computeSuperviserPonderation();
		
		document =  new ActionLoggerDocument();
		buildActionLoggerDocument();
		
		initActionsLogged();
		
		rd.setSeed(System.currentTimeMillis());
	}
	
	private void buildPeerList() {
		PeerCollection temp; // the peer list of each superviser
		
		if (supervisers != null) {
			
			int size = supervisers.size();
			// Init peers model list
			peers = null;
			peers = new PeerCollection();
			
			for (int i = 0; i < size; i++) {
				temp = supervisers.get(i).getPeers(); 
							
				int sizetemp = temp.size();
				for (int j = 0; j < sizetemp; j++) {
					// Add each peer which isn't already in the peers model list
					if (! peers.isIn(temp.get(j))) peers.add(temp.get(j));
				}
				
			}

		}
	}
	
	private void buildActionLoggerDocument() {
		// Build a list with the bot loggers
		ArrayList loggers = new ArrayList();
		int size = supervisers.size();
		for (int i = 0; i < size; i++) {
			loggers.add(supervisers.get(i).getLogger());
		}
		
		document = new ActionLoggerDocument(loggers);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public boolean setBoard(Board board) {
		this.board = null;
		this.board = board;
		
		int size = peers.size();
		boolean b = true;
		boolean result = true;
		
		for (int i = 0; i < size; i++) {
			b = peers.get(i).setBoard(this.board);
			if (!b) {
				document.addMessage(Messages.getString("Model.1") + peers.get(i).getName() + //$NON-NLS-1$
										Messages.getString("Model.2")); //$NON-NLS-1$
				result = false;
			}
		}
		
		size = supervisers.size();
		for (int i = 0; i < size; i++) {
			supervisers.get(i).setBoard(this.board);	
		}
		
		return result;
	}
	
	public ArrayList getPeersName() {
		ArrayList temp = new ArrayList();
		int size = peers.size();
		for (int i = 0; i < size; i++) {
			temp.add(((Peer)peers.get(i)).getName());
		}
		return temp;
	}
	
	public PeerCollection getPeers() {
		return peers;
	}

	public Peer getPeer (int row) {
		return peers.get(row);
	}
	
	public Peer getPeerFromId (String id) {
		return peers.getFromId(id);
	}
	
	public void addPeer(Peer peer) {
		peers.add(peer);
		peer.setId(String.valueOf(peers.size() - 1));
		peerTableModel.buildData();
		updatePeerActionPonderationTableModel();
		updatePeerPonderationTableModel();
		PEER_POND_SUM = PEER_POND_SUM + peer.getPonderation();
	}
	
	/**
	 * This method is used to update the model when the user
	 * removes a peer
	 * @param index : the position of the peer in he collection
	 */
	public void removePeer(int index) {
		Peer toRemove = peers.get(index);
		
		PEER_POND_SUM = PEER_POND_SUM - toRemove.getPonderation();
		
		// Remove it from every known peers list
		int size = peers.size();
		for (int i = 0; i < size; i++) {
			if (i != index) peers.get(i).removeAKnownPeer(toRemove);
		}
		
		// Remove it from every superviser list
		size = supervisers.size();
		for (int i = 0; i < size; i++) {
			supervisers.get(i).removePeer(toRemove);
		}
		
		peers.remove(index);
				
		peerTableModel.buildData();
		peerActionPonderationTableModel.buildData();
		updatePeerPonderationTableModel();
		supervisersTableModel.buildData();
		
	}
	
	/**
	 * This method is used to update the model when the superviser
	 * removes a peer
	 * @param p : the peer to remove from the model
	 */
	public void removePeer(Peer p) {
		
		// Remove it from every known peers list
		int size = peers.size();
		for (int i = 0; i < size; i++) {
			peers.get(i).removeAKnownPeer(p);
		}
		
		PEER_POND_SUM = PEER_POND_SUM - p.getPonderation();
		
		peers.remove(p);
		peerTableModel.buildData();
		peerActionPonderationTableModel.buildData();
		updatePeerPonderationTableModel();
	}
	
	public void setPeers(PeerCollection peers) {
		this.peers = peers;
		peerTableModel.setPeerList(this.peers);
		
		computePeerPonderation();
		
		updatePeerActionPonderationTableModel();
		updatePeerPonderationTableModel();
		buildActionLoggerDocument();
	}

	public SuperviserCollection getSupervisers() {
		return supervisers;
	}
	
	public void addSuperviser (Superviser sup) {
		supervisers.add(sup);
		sup.setId(String.valueOf(supervisers.size() - 1));
		sup.getLogger().setActionsLogged(actionsLogged);
		
		supervisersTableModel.buildData();
		superviserActionPonderationTableModel.buildData();
		updateSuperviserPonderationTableModel();
		SUP_POND_SUM = SUP_POND_SUM + sup.getPonderation();
		
		document.addActionLogger(sup.getLogger());
	}
	
	public boolean removeSuperviser(int index) {
		Superviser toRemove = supervisers.get(index);
		
		// Remove it from every known peers list
		if (toRemove.getPeers() == null || toRemove.getPeers().size() <= 0) {
		
			SUP_POND_SUM = SUP_POND_SUM - toRemove.getPonderation();
			
			supervisers.remove(toRemove);
			supervisersTableModel.buildData();
			superviserActionPonderationTableModel.buildData();
			updateSuperviserPonderationTableModel();
			return true;
		}
		return false;
	}
	
	public void addSuperviserPeer (Superviser sup, Peer peer) {
		peers.add(peer);
		peer.setId(String.valueOf(peers.size() - 1));
		peerTableModel.buildData();
		peerActionPonderationTableModel.buildData();
		updatePeerPonderationTableModel();
		
		sup.addPeer(peer);
		supervisersTableModel.buildData();
		superviserActionPonderationTableModel.buildData();
		updateSuperviserPonderationTableModel();
		
		PEER_POND_SUM = PEER_POND_SUM + peer.getPonderation();
	}
	
	/*public void modifySuperviserPeer(Superviser newSup, Peer peer) {
		Superviser oldSup = peer.getLogger().getSuperviser();
		oldSup.removePeer(peer);
		newSup.addPeer(peer);
		peer.setLogger(newSup.getLogger());
		supervisersTableModel.buildData();	
		superviserActionPonderationTableModel.buildData();
				
	}*/
	
	public void setSuperviserPeers (Superviser sup, PeerCollection peerslist, PeerCollection notPeerlist) {
		
		sup.setPeers(peerslist);
			
		// Check every peer has a logger, if not set to default superviser (the first superviser)
		int size = notPeerlist.size();
		for (int i = 0; i < size; i++) {
			if (notPeerlist.get(i).getLoggers() == null || 
					notPeerlist.get(i).getLoggers().size() == 0){
				notPeerlist.get(i).addLogger(supervisers.get(0));
				supervisers.get(0).addPeer(notPeerlist.get(i));
			}
		}
				
	}
	
	public void makeRandomAction() {
		int peerorsup = rd.nextInt(100);
		int range = peers.size();
		
		if (peerorsup <= (int)(100 * peersSuperviserPonderation) ) {
			Peer p = selectRandomPeer();
			/*if (!p.isDead()) */p.makeAleaAction();
			/*else makeRandomAction();*/
			
		} else if (peerorsup > (int)(100 * peersSuperviserPonderation)) {
			selectRandomSuperviser().makeAleaAction();
		}
	}
	
	private Superviser selectRandomSuperviser() {
		int range = rd.nextInt(100);
		Superviser s = null;
		int size = supervisers.size();
		float sum = 0f;
		
		for (int i = 0 ; i < size; i ++ ) {
			sum = sum + supervisers.get(i).getPonderation();
			if (range < (int) (100 * ((float)sum /(float)SUP_POND_SUM) ) ) {
				s = supervisers.get(i);
				return s;
			}
		}
		return null;
	}
	
	private Peer selectRandomPeer() {
		int range = rd.nextInt(100);
		Peer p = null;
		int size = peers.size();
		int sum = 0;

		for (int i = 0 ; i < size; i ++ ) {
			sum = sum + peers.get(i).getPonderation();
			
			
			if (range <= (int) (100 * ((float)sum / (float)PEER_POND_SUM) ) ) {
				p = peers.get(i);
				return p;
			}
		}
		return null;
	}
	
	public void setSeed (long seed) {
		rd.setSeed(seed);
	}
	
	
	public void updatePeerModel() {
		peerTableModel.buildData();
	}
	
	public PeerTableModel getPeerTableModel() {
		return peerTableModel;
	}
	
	public void setPeerTableModel(PeerTableModel peerTableModel) {
		this.peerTableModel = peerTableModel;
	}
	
	/*public void updateSuperviserModelStep() {
		buildPeerList();
		peerTableModel.setPeerList(peers);
		peerTableModel.buildData();
		peerActionPonderationTableModel.buildData();
		
		supervisersTableModel.buildData();
		superviserActionPonderationTableModel.buildData();
	}*/
	
	public void updateSuperviserModel() {
		buildPeerList();
		peerTableModel.setPeerList(peers);
		peerActionPonderationTableModel.setPeerList(peers);
		peerPonderationTableModel.setPeerList(peers);
		
		updatePeerModel();
		updatePeerActionPonderationTableModel();
		updatePeerPonderationTableModel();
		
		supervisersTableModel.buildData();
		updateSuperviserActionPonderationTableModel();
		updateSuperviserPonderationTableModel();
		
		computePeerPonderation();
		computeSuperviserPonderation();
	}
	
	public PeerActionPonderationTableModel getPeerActionPonderationTableModel() {
		return peerActionPonderationTableModel;
	}
	
	public void setPeerActionPonderationTableModel(
			PeerActionPonderationTableModel peerActionTableModel) {
		this.peerActionPonderationTableModel = peerActionTableModel;
	}
	
	public void updatePeerActionPonderationTableModel() {
		peerActionPonderationTableModel.buildData();
	}
	
	public SuperviserTableModel getSupervisersTableModel() {
		return supervisersTableModel;
	}
	
	public SuperviserActionPonderationTableModel getSuperviserActionPonderationTableModel() {
		return superviserActionPonderationTableModel;
	}
	
	public void setSuperviserActionPonderationTableModel(
			SuperviserActionPonderationTableModel superviserActionPonderationTableModel) {
		this.superviserActionPonderationTableModel = superviserActionPonderationTableModel;
	}
	
	public void updateSuperviserActionPonderationTableModel() {
		superviserActionPonderationTableModel.buildData();
	}
	
	public ActionLoggerDocument getDocument() {
		return document;
	}
	
	public void saveLogs(String filename) {
		Document doc = document.getDocument();
		try {
			String s = doc.getText(0, doc.getLength());
			
			File file = new File (filename);
			if (!file.exists()) file.createNewFile();
			
			FileWriter writer = new FileWriter(file);
			writer.write(s);
			writer.close();
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String filename) {
		try {
			File f = new File(filename);
			if (!f.exists()) f.createNewFile();
			PrintWriter pw = new PrintWriter(new FileOutputStream(f));
			
			pw.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"); //$NON-NLS-1$
			
			pw.println("<orage>"); //$NON-NLS-1$
			pw.print(board.toXML());
			
			pw.print("\t<peers>");
			
			int size = peers.size();
			for (int i = 0; i < size; i++) {
				pw.print(peers.get(i).toXML());
			}
			pw.print("\t</peers>");
			
			pw.println("\t<supervisers>"); //$NON-NLS-1$
			pw.println("\t\t<peersSuperviserPonderation>");
			pw.println(peersSuperviserPonderation);
			pw.println("\t\t</peersSuperviserPonderation>");
			 size = supervisers.size();
			for (int i = 0; i < size; i++) {
				pw.print(supervisers.get(i).toXML());
			}
			pw.println("\t</supervisers>"); //$NON-NLS-1$
						
			pw.println("</orage>"); //$NON-NLS-1$
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		ArrayList loggers = document.getActionLoggers();
		int size = loggers.size();
		
		for (int i = 0; i < size; i++) {
			((SuperviserLogSaver)loggers.get(i)).stop();
		}
	}
	
	public void empty() {
		board = null;
		
		Peer peer;
		int size = peers.size();
		for (int i = 0; i < size; i++) {
			peer = peers.get(0);
			peer = null;
			peers.remove(0);
		}
			
		updatePeerModel();
		updateSuperviserModel();
		updatePeerActionPonderationTableModel();
		updateSuperviserActionPonderationTableModel();
		updateSuperviserPonderationTableModel();
		updatePeerPonderationTableModel();
		computePeerPonderation();
		computeSuperviserPonderation();
	}
	
	public float getPeersSuperviserPonderation() {
		return peersSuperviserPonderation;
	}
	
	public void setPeersSuperviserPonderation(float peersSuperviserPonderation) {
		this.peersSuperviserPonderation = peersSuperviserPonderation;
	}
	
	public SuperviserPonderationTableModel getSuperviserPonderationTableModel() {
		return superviserPonderationTableModel;
	}
	
	public void setSuperviserPonderationTableModel(
			SuperviserPonderationTableModel superviserPonderationTableModel) {
		this.superviserPonderationTableModel = superviserPonderationTableModel;
	}
	
	public void updateSuperviserPonderationTableModel() {
		superviserPonderationTableModel.buildData();
	}
	
	public void updateAllPonderation() {
		updatePeerActionPonderationTableModel();
		updateSuperviserActionPonderationTableModel();
		updateSuperviserPonderationTableModel();
		updatePeerPonderationTableModel();
		computePeerPonderation();
		computeSuperviserPonderation();
	}
	
	private void computeSuperviserPonderation() {
		int size = supervisers.size();
		SUP_POND_SUM = 0;
		for (int i = 0; i < size ; i++) {
			SUP_POND_SUM = SUP_POND_SUM + supervisers.get(i).getPonderation();
		}
	}
	
	private void computePeerPonderation() {
		int size = peers.size();
		PEER_POND_SUM = 0;
		for (int i = 0; i < size ; i++) {
			PEER_POND_SUM = PEER_POND_SUM + peers.get(i).getPonderation();
		}
		
	}
	
	public PeerPonderationTableModel getPeerPonderationTableModel() {
		return peerPonderationTableModel;
	}
	
	public void setPeerPonderationTableModel(
			PeerPonderationTableModel peerPonderationTableModel) {
		this.peerPonderationTableModel = peerPonderationTableModel;
	}
	
	public void updatePeerPonderationTableModel() {
		peerPonderationTableModel.buildData();
	}
	
	private void initActionsLogged() {
		actionsLogged = new ArrayList();
		actionsLogged.add(String.valueOf(false));
		actionsLogged.add(String.valueOf(false));
		actionsLogged.add(String.valueOf(false));
		actionsLogged.add(String.valueOf(false));
		actionsLogged.add(String.valueOf(true));
		actionsLogged.add(String.valueOf(true));
		// Set to supervisers loggers
		int size = supervisers.size();
		
		for (int i = 0; i < size; i++) {
			supervisers.get(i).getLogger().setActionsLogged(actionsLogged);
		}
	}
	
	public void setActionsLogged(ArrayList list) {
		actionsLogged = null;
		actionsLogged = list;
		
		// Set to supervisers loggers
		int size = supervisers.size();
		
		for (int i = 0; i < size; i++) {
			supervisers.get(i).getLogger().setActionsLogged(actionsLogged);
		}
		
	}
	
	public ArrayList getActionsLogged() {
		return actionsLogged;
	}
}
