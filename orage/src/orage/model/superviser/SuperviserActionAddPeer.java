
package orage.model.superviser;

import java.awt.Color;
import java.util.ArrayList;

import orage.model.peer.Peer;


public class SuperviserActionAddPeer extends SuperviserAction {

	public SuperviserActionAddPeer(Superviser s) {
		super(s);

		kind = super.ADDPEER_KIND;
	}

	public boolean execute() {
		String id = String.valueOf(superviser.getId() + superviser.peers.size());
		String name = superviser.getName() + "Peer" + superviser.peers.size();

		int rangeX = superviser.board.getXsize();
		int rangeY = superviser.board.getYsize();

		int x = superviser.rd.nextInt(rangeX);
		int y = superviser.rd.nextInt(rangeY);

		Color c = Color.BLACK;

		int iliar = superviser.rd.nextInt(2);
		boolean liar = true;
		if (iliar == 0)
			liar = false;

        SuperviserCollection slss = new SuperviserCollection();
        slss.add(superviser);
		peerTarget = new Peer(id, name, x, y, superviser.board, liar, false, c,
				new ArrayList(), new ArrayList(), slss);
		
		superviser.addPeer(peerTarget);
		
		
		return true;
	}
	
	public String toString() {
		String s = "Superviser " + superviser.getName() + " has ADDED A PEER : " +
					peerTarget.getName();
			
		return s;
	}
	
	public String toXML() {
		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<superviser>" + superviser.getId() + "</superviser>\r\n" +
						"\t\t<peerTarget>" + peerTarget.getId() + "</peerTarget>\r\n" +

				   "\t</action>\r\n";


		return s;
	}

}
