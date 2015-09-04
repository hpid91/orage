
package orage.model.peer;

import orage.model.Messages;

public class PeerActionMove extends PeerAction {

	private int oldX;
	private int oldY;

	public PeerActionMove(Peer p) {
		super(p);
		oldX = peer.posX;
		oldY = peer.posY;
		kind = super.MOVE_KIND;
	}

	public boolean execute() {
		return moveNext();
	}

	public String toXML() {

		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<peer>" + peer.getId() + "</peer>\r\n" +
						"\t\t<from>\r\n" +  
							 "\t\t\t<x>" + oldX + "</x>\r\n" +
							"\t\t\t<y>"	+ oldY + "</y>\r\n" +
						"\t\t</from>\r\n" +
						"\t\t<to>\r\n" +  
							"\t\t\t<x>" + peer.posX + "</x>\r\n" +
							"\t\t\t<y>"	+ peer.posY + "</y>\r\n" +
						"\t\t</to>\r\n" +
					"\t</action>\r\n";
		
		
		return s;
	}

	public String toString() {
		String msg = peer.name	+ Messages.getString("Peer.20") +
							oldX + Messages.getString("Peer.21") +
							oldY + Messages.getString("Peer.22") +
					Messages.getString("Peer.23") + peer.posX +
					Messages.getString("Peer.24") + peer.posY +
					Messages.getString("Peer.25");
		
		return msg;
	}

	private boolean moveNext() {
		if (peer.dead) {
			String s = Messages.getString("Peer.0") + peer.name + Messages.getString("Peer.1"); //$NON-NLS-1$ //$NON-NLS-2$

			if (peer.dead)
				s = s + Messages.getString("Peer.2"); //$NON-NLS-1$
			else
				s = s + Messages.getString("Peer.3"); //$NON-NLS-1$

			peer.loggers.addMessage(s);
			return false;
		} else {
			
			int nextX = 0, nextY = 0;

			int range = 3;

			nextX = peer.rd.nextInt(range);

			if (nextX == 2)	nextX = 1;
			else if (nextX == 1) nextX = 0;
			else nextX = -1;

			nextY = peer.rd.nextInt(range);

			if (nextY == 2)	nextY = 1;
			else if (nextY == 1) nextY = 0;
			else nextY = -1;

			if ( ((peer.posX == 0) && (nextX == -1)) ||
				 ((peer.posX == peer.maxX) && (nextX == 1)) )nextX = 0;
		
			if ( ((peer.posY == 0) && (nextY == -1)) ||
				 ((peer.posY == peer.maxY) && (nextY == 1)) )nextY = 0;
			
			nextX = nextX + peer.posX;
			nextY = nextY + peer.posY;
			
			if ((nextX == peer.posX) && (nextY == peer.posY))return moveNext();
			else return peer.move(nextX, nextY);

		}

	}
}