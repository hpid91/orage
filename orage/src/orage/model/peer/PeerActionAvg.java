
package orage.model.peer;

import orage.model.superviser.system.Labels;


public class PeerActionAvg extends PeerAction {

	private float average = 0f;
	
	public PeerActionAvg(Peer p) {
		super(p);
		
		kind = super.AVG_KIND;
	}
	
	public boolean execute() {
		target = getAPeerToAsk();
        
		// Call the ComputeAverage service on target
		if (target != null) {
            
            peer.loggers.notify(Labels.TRANSITION_ACTIONAVG, peer.id + target.id);
            
			average = target.computeAverage();
		
			// if average == -1 => FAULT PROPAGATION
			if (average == -1) {
                peer.loggers.notify(Labels.TRANSITION_AVGNOTOK, peer.id + target.id);
				peer.dead = true;
			} else {
                peer.loggers.notify(Labels.TRANSITION_AVGOK, peer.id + target.id);
            }
		} else {
			
			String s = "ERROR : peer " + peer.name + " doesn't know any peer.";
			peer.loggers.addMessage(s);
			return false;
		}
		
		return true;
	}

	public String toString() {
		String s = "AVG: " + peer.name + " ask " + 
					target.name + " for the average of cells values : " + average + " .";
		return s;
	}
	
	/* (non-Javadoc)
	 * @see orage.model.peer.SuperviserAction#toXML()
	 */
	public String toXML() {
		String s = "\t<action>\r\n" + "\t\t<kind>" + kind + "</kind>\r\n"
						+ "\t\t<peer>" + peer.getId() + "</peer>\r\n";

		if (target != null) {
			s = s + "\t\t<target>" + target.getId() + "</target>\r\n";
		}
		
		s = s + "\t\t<answer>\r\n" + average + "\t\t</answer>\r\n"
			+ "\t</action>\r\n";

		return s;
	}

	
	public float getAverage() {
		return average;
	}
}
