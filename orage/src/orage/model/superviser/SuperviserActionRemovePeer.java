
package orage.model.superviser;

public class SuperviserActionRemovePeer extends SuperviserAction {

	public SuperviserActionRemovePeer(Superviser s) {
		super(s);

		kind = super.REMPEER_KIND;
	}

	public boolean execute() {
		setTarget();
		if (peerTarget != null) {
			superviser.removePeer(peerTarget);
			return true;
		} else {
			superviser.getLogger().addMessage(
					"ERROR : " + superviser.getName()
							+ " doesn't supervise any peer");
			return false;
		}

	}

	public String toString() {
		String s = "Superviser " + superviser.getName()
				+ " has REMOVED A PEER : " + peerTarget.getName() + " .";

		return s;
	}

	public String toXML() {
		String s = "\t<action>\r\n" + "\t\t<kind>" + kind + "</kind>\r\n"
				+ "\t\t<superviser>" + superviser.getId() + "</superviser>\r\n"
				+ "\t\t<peerTarget>" + peerTarget.getId() + "</peerTarget>\r\n"
				+ "\t</action>\r\n";

		return s;
	}

}