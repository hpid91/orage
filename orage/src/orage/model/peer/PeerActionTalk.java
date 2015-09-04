package orage.model.peer;

import orage.model.Cell;
import orage.model.Messages;


public class PeerActionTalk extends PeerAction {

	public PeerActionTalk(Peer p) {
		super(p);

		kind = super.TALK_KIND;
	}

	public boolean execute() {
		target = getAPeerToAsk();
		Cell req = getAnUnknowCellOnBoard();
		request = req;
		return talk(target, req);
	}

	public String toXML() {
		String s = "\t<action>\r\n" + "\t\t<kind>" + kind + "</kind>\r\n"
				+ "\t\t<peer>" + peer.getId() + "</peer>\r\n";

		if (target != null) {
			s = s + "\t\t<target>" + target.getId() + "</target>\r\n";
		}

		s = s + "\t\t<request>\r\n" + request.toXML() + "\t\t</request>\r\n"
				+ "\t\t<answer>\r\n" + answer.toXML() + "\t\t</answer>\r\n"
				+ "\t</action>\r\n";

		return s;
	}

	public String toString() {
		// Talk is only about Cell so we show the cell value of the answer
		String s = peer.getName() + " TALK to ";

		if (target == null)
			s = s + " null ";
		else
			s = s + target.getName();

		s = s + " ABOUT " + request + " ANSWER IS : value =";

		if (answer != null)
			s = s + answer.getValue();
		else
			s = s + "null ( A PROBLEM OCCURED : dead bot, isolated asker...)";

		return s;
	}

	private boolean talk(Peer b, Cell queryOn) {
		if (peer.dead) {
			String s = Messages.getString("Peer.0") + peer.name + Messages.getString("Peer.1"); //$NON-NLS-1$ //$NON-NLS-2$

			if (peer.dead)
				s = s + Messages.getString("Peer.2"); //$NON-NLS-1$
			else
				s = s + Messages.getString("Peer.3"); //$NON-NLS-1$

			peer.loggers.addMessage(s);
			
			return false;
		} else {
			if (b != null) {

				int value = b.valueOf(queryOn);

				if (value != -1) {
					queryOn.setValue(value);
					peer.knownCellsBoard.add(queryOn);

					answer = queryOn;

				} else {
					Cell temp = new Cell(queryOn.getX(), queryOn.getY(), value);
					answer = temp;
				}
				
				return true;
			} else {
				peer.loggers
						.addMessage(peer.name + Messages.getString("Peer.4")); //$NON-NLS-1$
				return false;
			}
		}
	}
	
	private Cell getAnUnknowCellOnBoard() {
		int iX = 0;
		int iY = 0;
		int range = 0;

		range = peer.maxX - 1;
		iX = peer.rd.nextInt(range) + 1;

		range = peer.maxY - 1;
		iY = peer.rd.nextInt(range) + 1;

		Cell result = new Cell(peer.board.getCell(iX, iY));

		return result;

	}

}