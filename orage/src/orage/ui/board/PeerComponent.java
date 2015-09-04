package orage.ui.board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import orage.model.peer.Peer;


public class PeerComponent extends JComponent implements MouseListener {

	// Model element
	private Peer peermodel;

	// Graphical attributes
	private static int WIDTH = 16;

	private static int HEIGHT = 16;

	private int posX = 0;

	private int posY = 0;

	private Image aliveImage = Toolkit.getDefaultToolkit().getImage(
			"icons/peer.png");

	private Image deadImage = Toolkit.getDefaultToolkit().getImage(
			"icons/deadpeer.png");

	public PeerComponent(Peer peer) {
		super();
		this.peermodel = peer;
		posX = peer.getX() * 20;
		posY = peer.getY() * 20;

		addMouseListener(this);
	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		validate();
	}

	public void paint(Graphics g) {
	
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		// Paint image
		if (peermodel.isDead()) {
			g2d.drawImage(deadImage, posX + 2, posY + 2, null);
		} else {
			g2d.drawImage(aliveImage, posX + 2, posY + 2, null);
		}

	}

	public void mouseEntered(MouseEvent e) {
		getParent().dispatchEvent(e);
	}

	public void mouseClicked(MouseEvent e) {
		getParent().dispatchEvent(e);
	}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public boolean equals(PeerComponent obj) {

		return (this.peermodel.equals(obj.peermodel));
	}

	public Peer getPeerModel() {
		return peermodel;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}