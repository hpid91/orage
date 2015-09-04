
package orage.ui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import orage.model.Board;
import orage.model.Cell;
import orage.model.PeerCollection;
import orage.model.peer.Peer;


public class BoardPanel extends JPanel implements Scrollable, MouseListener {

	private int xsize = 0;

	private int ysize = 0;

	private ArrayList cells = null;

	private ArrayList peers = null;

	private PeerCollection peersModel = null;

	private Board board;

	public BoardPanel(Board b, PeerCollection peersModel) {
		board = b;
		this.peersModel = peersModel;

		cells = new ArrayList();
		peers = new ArrayList();

		xsize = board.getXsize();
		ysize = board.getYsize();

		addMouseListener(this);
		init();
	}

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 40;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 40;

	}

	public void mouseClicked(MouseEvent e) {
		/*if (e.getSource().getClass().equals(CellComponent.class)) {
			int size = cells.size();
				
			CellComponent selectCell = (CellComponent) e.getSource();
	
			CellComponent temp;
			for (int i = 0; i < size; i++) {
				temp = (CellComponent) cells.get(i);
				if (temp.equals(selectCell)) {
					temp.setSelect(true);
					temp.repaint();
				} else
					temp.setSelect(false);
	
			}
			
		}*/

	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	private void init() {

		GridLayout layout = new GridLayout(ysize, xsize);
		this.setLayout(layout);

		// Initialize cell components
		CellComponent cell;
		for (int i = ysize - 1; i > -1; i--) {

			for (int j = 0; j < xsize; j++) {
				cell = new CellComponent(board.getCell(j, i));
				cell.setPos(j * 20, (ysize - i - 1) * 20);
				cells.add(cell);
				this.add(cell);
			}
		}

		// Initialize peers components
		int size = peersModel.size();
		PeerComponent peer;
		Peer temp;
		for (int k = 0; k < size; k++) {
			temp = peersModel.get(k);
			peer = new PeerComponent(temp);
			peer.setPos(temp.getX() * 20, (ysize - temp.getY() - 1) * 20);
			peers.add(peer);
			CellComponent cc = getPeerComponentParent(peer);
			if (cc != null ) cc.add(peer);
			
		}

		setPreferredSize(new Dimension(xsize * 20, ysize * 20));
		setVisible(true);
	}
	
	private CellComponent getPeerComponentParent(PeerComponent bc) {
		CellComponent cc = null, temp = null;
		int x = bc.getPosX();
		int y = bc.getPosY();
		
		int size = cells.size();
		
		for (int i = 0; i < size; i++) {
			temp = (CellComponent)cells.get(i);
			if ( (temp.getPosX() == x) && (temp.getPosY() == y ) ) {
				cc = temp;
			}
		}
		return cc;
		
	}
	
	private void paintPeerKnownPeers(Graphics g, PeerComponent current) {
		PeerCollection kPeers = null;
		PeerComponent kPeer;
		//		 Known peers
		kPeers = current.getPeerModel().getKnownPeers();
		int ksize = kPeers.size();
		for (int j = 0; j < ksize; j++) {
			kPeer = getFromPeer(kPeers.get(j));
			if (kPeer != null) {
				Graphics2D g2d = (Graphics2D) g;
				//g2d.setColor(new Color(145, 139, 156));
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2.0f));
				drawArrow(g2d, current.getPosX() + 5,
						current.getPosY() + 5, kPeer.getPosX() + 2, kPeer
								.getPosY() + 2, 1f);
				kPeer.paint(g);
			}		
		}
	}
	private void paintPeerKnownCells(Graphics g, PeerComponent current) {
				
		ArrayList kCells = null;
		
		CellComponent kCell;
		int ksize = 0;
		
			
		// Paint Known cells
		kCells = current.getPeerModel().getKnownCells();
		if (kCells != null) {
			ksize = kCells.size();
			for (int j = 0; j < ksize; j++) {
				kCell = getFromPeerCell((Cell)kCells.get(j));
				if (kCell != null) {
					kCell.setColor(current.getPeerModel().getColor());
					kCell.paint(g);
				}
			}
		}
	}
	
	public void paintView (Peer bp) {
		Graphics g = this.getGraphics();
		
		// Paint original board
		CellComponent cc;
		int size = cells.size();
		for (int i = 0; i < size; i++) {
			cc = (CellComponent) cells.get(i);
			cc.setColor(CellComponent.ORIGINAL_COLOR);
			cc.paint(g);
		}
		
			
		// Paint discovered cells of the peer + its peers mates
		PeerComponent peer = getFromPeer(bp);
		paintPeerKnownCells(g, peer);
		paintPeerKnownPeers(g, peer);
		peer.paint(g);
		
		// Paint other peers (just for seeing them)
		PeerComponent current;
		size = peers.size();
		for (int i = 0; i < size; i++) {
			current = (PeerComponent) peers.get(i);
			current.paint(g);
		}
	}
	
	public void updatePeerModel(PeerCollection pm) {
		peersModel = pm;
		int size = peersModel.size();
		peers = null;
		peers = new ArrayList();
		PeerComponent peer;
		Peer temp;
		for (int k = 0; k < size; k++) {
			temp = peersModel.get(k);
			peer = new PeerComponent(temp);
			peer.setPos(temp.getX() * 20, (ysize - temp.getY() - 1) * 20);
			peers.add(peer);
			CellComponent cc = getPeerComponentParent(peer);
			if (cc != null ) cc.add(peer);
			
		}
		
	}
	public void paint(Graphics g) {
		super.paint(g);

		// Paint Cell components
		int size = cells.size();
		CellComponent cc;
		for (int i = 0; i < size; i++) {
			cc = (CellComponent) cells.get(i);
			cc.paint(g);
		}

		// Paint peer known cells
		PeerComponent current;
		size = peers.size();
		for (int i = 0; i < size; i++) {
			current = (PeerComponent) peers.get(i);
			paintPeerKnownCells(g, current);
			
		}
		
		// Paint peers
		size = peers.size();
		for (int i = 0; i < size; i++) {
			current = (PeerComponent) peers.get(i);
			current.paint(g);
		}
		
		// Paint known peers arrows (over the cells of course)
		size = peers.size();
		for (int i = 0; i < size; i++) {
			current = (PeerComponent) peers.get(i);
			paintPeerKnownPeers(g, current);
			
		}
		
		
	}

	public void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x,
			int y, float stroke) {
		double aDir = Math.atan2(xCenter - x, yCenter - y);
		g2d.drawLine(x, y, xCenter, yCenter);
		g2d.setStroke(new BasicStroke(0.5f)); // make the arrow head solid even
											  // if
											  // dash pattern has been specified
		Polygon tmpPoly = new Polygon();
		int i1 = 12 + (int) (stroke);
		int i2 = 6 + (int) stroke; // make the arrow head the same size
		// regardless of the length length
		tmpPoly.addPoint(x, y); // arrow tip
		tmpPoly.addPoint(x + xCor(i1, aDir + .5), y + yCor(i1, aDir + .5));
		tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
		tmpPoly.addPoint(x + xCor(i1, aDir - .5), y + yCor(i1, aDir - .5));
		tmpPoly.addPoint(x, y); // arrow tip
		g2d.drawPolygon(tmpPoly);
		g2d.fillPolygon(tmpPoly); // remove this line to leave arrow head
		// unpainted
	}

	private static int yCor(int len, double dir) {
		return (int) (len * Math.cos(dir));
	}

	private static int xCor(int len, double dir) {
		return (int) (len * Math.sin(dir));
	}

	public PeerComponent getFromCoord(int x, int y) {
		int size = peers.size();
		PeerComponent result = null;
		
		for (int i = 0; i < size; i++) {
			if ( (((PeerComponent)peers.get(i)).getX() == x) &&
					(((PeerComponent)peers.get(i)).getY() == y) ) {
				result = (PeerComponent)peers.get(i);
			}
		}
		return result;
	}
	private CellComponent getFromPeerCell(Cell bc) {
		CellComponent result = null;
		int size = cells.size();
		
		for (int i = 0; i < size; i++) {
			if (((CellComponent) cells.get(i)).equals(bc))
				result = (CellComponent) cells.get(i);
		}
		
		return result;
	}
	
	private PeerComponent getFromPeer(Peer bp) {
		PeerComponent result = null;
		int size = peers.size();

		for (int i = 0; i < size; i++) {
			if (((PeerComponent) peers.get(i)).getPeerModel().equals(bp))
				result = (PeerComponent) peers.get(i);
		}

		return result;
	}
}