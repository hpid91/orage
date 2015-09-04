
package orage.model.peer;

import orage.model.Messages;
import orage.model.superviser.system.Labels;


public class PeerActionKill extends PeerAction {

	private boolean oldstate;
	
	public PeerActionKill(Peer p) {
		super(p);
		oldstate = peer.dead;
		
		kind = super.KILL_KIND;
	}
	
	public boolean execute() {
        
        peer.loggers.notify(Labels.TRANSITION_ACTIONKILL,
                peer.id);
       
		return setDead(!peer.isDead());

	}

	public String toString() {
		String s = peer.name + Messages.getString("Peer.1");

		if (peer.dead)
			s = s + Messages.getString("Peer.2");
		else
			s = s + Messages.getString("Peer.3");
		
		return s;
	}
	
	/* (non-Javadoc)
	 * @see orage.model.peer.SuperviserAction#toXML()
	 */
	public String toXML() {
		String s = "\t<action>\r\n" +
						"\t\t<kind>" + kind + "</kind>\r\n" +
						"\t\t<peer>" + peer.getId() + "</peer>\r\n" +
						"\t\t<oldstate>" + oldstate + "</oldstate>\r\n" +
						"\t\t<newstate>" + peer.isDead() + "</newstate>\r\n" +
				   "\t</action>\r\n";


		return s;
	}

	private boolean setDead(boolean dead) {
		peer.dead = dead;
		return true;
	}
	
}
