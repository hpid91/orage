
package orage.ui.board;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import orage.model.Board;
import orage.model.PeerCollection;
import orage.model.peer.Peer;

public class JBoardDialog extends JDialog implements ActionListener,
		MouseListener {

	private BoardPanel boardPanel;
	private Board board;
	private PeerCollection peers;
	
	JScrollPane scrollpane;
	
	private static final String CLOSE = "CLOSE";

	private Peer peer;

	public JBoardDialog(JFrame mother, Peer peer, Board board,
			PeerCollection peers) {
		super(mother);
		
		this.peer = peer;
		this.board = board;
		this.peers = peers;
		
		addMouseListener(this);
		
		boardPanel = new BoardPanel(board, peers);

		scrollpane = new JScrollPane(boardPanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scrollpane);

		if (peer != null ) setTitle("View from " + peer.getName());
		else setTitle("Global View");
		
		setResizable(false);
		setModal(false);

		setLocationRelativeTo(mother);

		pack();
		validate();
	}

	public void paint(Graphics g) {
		
		if (peer!= null) boardPanel.paintView(peer);
		else {super.paint(g);
			
		}
		
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CLOSE)) {
			dispose();
		}
	}
	
	public void setPeers(PeerCollection peers) {
		this.peers = peers;
		boardPanel.updatePeerModel(peers);
	}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}
}