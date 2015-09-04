
package orage.model.peer;

import orage.model.Cell;
import orage.model.Messages;

public class PeerActionQuery extends PeerAction {

	
	public PeerActionQuery (Peer p) {
		super(p);
		
		kind = super.QUERY_KIND;
	}
	
	/* (non-Javadoc)
	 * @see orage.model.SuperviserAction#execute()
	 */
	public boolean execute() {
		Cell req = getAnUnknowCellOnZone();
		request = req;
		answer = query(req);
		
		if (answer == null) return false;
		else return true;
	}

	/* (non-Javadoc)
	 * @see orage.model.SuperviserAction#toXML()
	 */
	public String toXML() {
		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<peer>" + peer.getId() + "</peer>\r\n" +
						"\t\t<request>\r\n" + request.toXML() + "\t\t</request>\r\n";
		
		if (answer != null) {
			s = s + "\t\t<answer>\r\n" + answer.toXML() + "\t\t</answer>\r\n";
			
		} else s = s + "null";
		
		s = s +	"\t</action>\r\n";
		
		return s;
	}
	
	public String toString() {
		String s = peer.getName() + " QUERY on " +
				   request + " [value = " + answer.getValue() + "]";
		
		return s;
		
	}
	
	private Cell getAnUnknowCellOnZone() {
		if (isZoneDiscovered()) {
			String s = Messages.getString("Peer.5") + peer.name + Messages.getString("Peer.6"); //$NON-NLS-1$ //$NON-NLS-2$

			peer.loggers.addMessage(s);
			return null;
		} else {
			int aleaX = getAleaX();
			int aleaY = getAleaY();
			Cell result = new Cell(aleaX, aleaY);

			// Recursive call : if the cell is known and the knownCells list
			// uncomplete
			if (isAKnownCellOfZone(result))
				result = getAnUnknowCellOnZone();

			return result;
		}
	}
	
	private Cell query(Cell queryOn) {
		// If the bot is dead, no action is possible
		if (peer.dead) {
			String s = Messages.getString("Peer.0") + peer.name + Messages.getString("Peer.1"); //$NON-NLS-1$ //$NON-NLS-2$

			if (peer.dead)
				s = s + Messages.getString("Peer.2"); //$NON-NLS-1$
			else
				s = s + Messages.getString("Peer.3"); //$NON-NLS-1$

			peer.loggers.addMessage(s);
			return null;
		} else if (isZoneDiscovered()) {
			String s = Messages.getString("Peer.5") + peer.name + Messages.getString("Peer.6"); //$NON-NLS-1$ //$NON-NLS-2$

			peer.loggers.addMessage(s);
			return null;
		} else {
			// The Bot can make query on a zone of +/- 5 of its x,y
			int value = peer.board.valueOf(queryOn);
			queryOn.setValue(value);

			peer.knownCellsZone.add(queryOn);

			return queryOn;
		}
	}
	
	private boolean isZoneDiscovered() {
		return (peer.zoneCellsSize == peer.knownCellsZone.size());
	}
	
	private boolean isAKnownCellOfZone(Cell b) {
		boolean result = false;
		int size = peer.knownCellsZone.size();

		for (int i = 0; i < size; i++) {
			if (((Cell) peer.knownCellsZone.get(i)).equals(b))
				result = true;
		}

		return result;
	}

	private int getAleaX() {
		int aleaMaxX, aleaMinX, aleaX = 0;

		if (peer.posX - 5 < 0)
			aleaMinX = 0;
		else
			aleaMinX = peer.posX - 5;

		if (peer.posX + 5 > peer.maxX)
			aleaMaxX = peer.maxX;
		else
			aleaMaxX = peer.posX + 5;

		aleaX = aleaMinX;

		int range = aleaMaxX - aleaMinX + 1;

		aleaX = aleaX + peer.rd.nextInt(range);

		return aleaX;
	}

	private int getAleaY() {
		int aleaMaxY, aleaMinY, aleaY = 0;

		if (peer.posY - 5 < 0) {
			aleaMinY = 0;
		} else {
			aleaMinY = peer.posY - 5;
		}

		if (peer.posY + 5 > peer.maxY) {
			aleaMaxY = peer.maxY;
		} else {
			aleaMaxY = peer.posY + 5;
		}

		aleaY = aleaMinY;

		int range = aleaMaxY - aleaMinY + 1;

		aleaY = aleaY + peer.rd.nextInt(range);

		return aleaY;
	}
}
