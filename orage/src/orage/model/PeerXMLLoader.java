
package orage.model;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import orage.model.peer.Peer;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserCollection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PeerXMLLoader {

	/**
	 * The root element of the document simulation
	 */
	private static Element root = new Element("orage");

	/**
	 * The document containing the simulation
	 */
	private static Document doc = new Document(root);

	public static void load(String savePath) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document simu = null;

		simu = builder.build(savePath);

		root = simu.getRootElement();
	}

	public static Board loadBoard() {
		Board board = new Board();

		Element boardElt = root.getChild("board");

		// Get general data of board
		int xsize = Integer.parseInt(boardElt.getChild("xsize").getText());
		int ysize = Integer.parseInt(boardElt.getChild("ysize").getText());
		int maxval = Integer.parseInt(boardElt.getChild("maxval").getText());
		int minval = Integer.parseInt(boardElt.getChild("minval").getText());

		board.setXsize(xsize);
		board.setYsize(ysize);
		board.setMatMaxVal(maxval);
		board.setMatMinVal(minval);

		// Get cell list
		Element cells = boardElt.getChild("cells");

		board.setCellList(getCells(cells));

		return board;
	}

	private static ArrayList getCells(Element celllist) {
		ArrayList list = new ArrayList();

		List listelt = celllist.getChildren("cell");
		Iterator i = listelt.iterator();
		Element cellelt;
		int x = 0, y = 0, val = 0;
		while (i.hasNext()) {
			cellelt = (Element) i.next();
			x = Integer.parseInt(cellelt.getChild("x").getText());
			y = Integer.parseInt(cellelt.getChild("y").getText());
			val = Integer.parseInt(cellelt.getChild("value").getText());
			list.add(new Cell(x, y, val));
		}

		return list;
	}

	public static float getPeerSupPonderation() {
		Element supselt = root.getChild("supervisers");

		float psponderation = Float.parseFloat(supselt.getChild(
				"peersSuperviserPonderation").getText());
		
		return psponderation;
	}
	
	public static PeerCollection loadPeers(Board board) {
		PeerCollection peers = new PeerCollection();
		
		Element peerselt = root.getChild("peers");

		List listelt = peerselt.getChildren("peer");
		Iterator i = listelt.iterator();
		Element peer, colorelt, kczoneelt, kcboardelt;
		String id = "", name = "";
		boolean liar, dead;
		int x, y, maxX, maxY, r, g, b, ponderation;
		ArrayList kczone, kcboard;
		Peer temp;
		while (i.hasNext()) {
			peer = (Element) i.next();

			id = peer.getAttributeValue("id");
			name = peer.getChild("name").getText();
			x = Integer.parseInt(peer.getChild("x").getText());
			y = Integer.parseInt(peer.getChild("y").getText());
			maxX = Integer.parseInt(peer.getChild("maxX").getText());
			maxY = Integer.parseInt(peer.getChild("maxY").getText());
			liar = Boolean.valueOf(peer.getChild("liar").getText())
					.booleanValue();
			dead = Boolean.valueOf(peer.getChild("dead").getText())
					.booleanValue();
			colorelt = peer.getChild("color");
			r = Integer.parseInt(colorelt.getChild("r").getText());
			g = Integer.parseInt(colorelt.getChild("g").getText());
			b = Integer.parseInt(colorelt.getChild("b").getText());
			ponderation = Integer.parseInt(peer.getChild("ponderation").getText());
			
			kczoneelt = peer.getChild("knowncellszone");
			kcboardelt = peer.getChild("knowncellsboard");
			
			ActionPonderationCollection apc = loadActionPonderations(peer);
			
			temp = new Peer(id, name, x, y, board, liar, dead, new Color(r,
					g, b), getCells(kczoneelt), getCells(kcboardelt), null);
			
			temp.setPonderation(ponderation);
			temp.setPonderations(apc);
				
			peers.add(temp);
		}
		
		setKnownPeers(peers);
		
		return peers;
	}
	
	public static SuperviserCollection loadSupervisers(Board board, PeerCollection allpeers) {
		SuperviserCollection supervisers = new SuperviserCollection();
		Element supselt = root.getChild("supervisers");
		List listelt = supselt.getChildren("superviser");
		Iterator i = listelt.iterator();
		Element supelt;
		String id, name;
		int ponderation;
		
		Superviser s;
		

		while (i.hasNext()) {
			supelt = (Element) i.next();
			id = supelt.getAttributeValue("id");
			name = supelt.getChild("name").getText();
			ponderation = Integer.parseInt(supelt.getChild("ponderation").getText());
			
			s = new Superviser(id, name, board);
			s.setPonderation(ponderation);
			
			
			PeerCollection sPeers = getSupPeers(supelt, board, allpeers);
			s.setPeers(sPeers);
			
			// Set loggers for supervised peers
			int size = sPeers.size();
			for (int j = 0; j < size; j++) {
				sPeers.get(j).addLogger(s);
			}
			
			ActionPonderationCollection apc = loadActionPonderations(supelt);
			s.setPonderations(apc);
			
			supervisers.add(s);
			
		}

		return supervisers;
	}

	private static ActionPonderationCollection loadActionPonderations(Element elt) {
		ActionPonderationCollection ponderations = new ActionPonderationCollection();
		
		Element actionponderationsElt = elt.getChild("actionponderatioons");
		List listelt = actionponderationsElt.getChildren("actionponderation");
		Iterator i = listelt.iterator();
		
		Element apElt;
		int coef;
		Class c = null;
		
		while(i.hasNext()) {
			apElt = (Element) i.next();
			
			coef = Integer.parseInt(apElt.getChild("coef").getText());
			try {
				c = Class.forName(apElt.getChild("class").getText());
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
			ponderations.addActionPonderation(coef, c);
		}
		
		return ponderations;
	}
	
	private static void setKnownPeers(PeerCollection peers) {

		Element peerselt = root.getChild("peers");
		List listpeers = peerselt.getChildren("peer");
		Iterator i = listpeers.iterator();

		String id;
		Element peerelt;
		PeerCollection allpeers = new PeerCollection();

		while (i.hasNext()) {
			peerelt = (Element) i.next();

			Element kpeerselt, kpeer, peer;
			Peer currentpeer;
			
			PeerCollection kpeers = new PeerCollection();
				
			currentpeer = peers.getFromId(peerelt.getAttributeValue("id"));

			kpeerselt = peerelt.getChild("knownpeers");

			List kpeerslist = kpeerselt.getChildren("knownpeer");
			Iterator i3 = kpeerslist.iterator();

			while (i3.hasNext()) {
				kpeer = (Element) i3.next();
				id = kpeer.getAttributeValue("id");
				kpeers.add(peers.getFromId(id));
			}
			
			currentpeer.setKnownPeers(kpeers);
			
		}
	}

	private static PeerCollection getSupPeers(Element elt, Board board,
			PeerCollection allpeers) {
		PeerCollection peers = new PeerCollection();

		Element peerselt = elt.getChild("supervisedpeers");

		List listelt = peerselt.getChildren("supervisedpeer");
		Iterator i = listelt.iterator();
		Element peer;
		String id = "";

	
		Peer temp;
		while (i.hasNext()) {
			peer = (Element) i.next();

			id = peer.getAttributeValue("id");
			
			temp = allpeers.getFromId(id);
			
			
			peers.add(temp);
		}
			
		return peers;
	}
}