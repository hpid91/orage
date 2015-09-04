package orage.control.peer;

import orage.control.Command;
import orage.control.Messages;
import orage.model.Model;
import orage.model.peer.Peer;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JPeerDialog;


public class CdOpenModifPeer extends Command {

	public CdOpenModifPeer(Model m, OrageWindow v) {
		super(m, v);
	}

	public void execute() {
		JPeerDialog peerDialog = new JPeerDialog(view.getListener(), model
				.getPeers(), model.getSupervisers(), JPeerDialog.MODE_MODIF,
				model.getBoard().getXsize() - 1,
				model.getBoard().getYsize() - 1);

		// Initialize the dialog
		int index = view.getSimulPanel().getSelectedPeer();
		if ( index != -1) {
			Peer peer = model.getPeers().get(index);
			peerDialog.setId(peer.getId());
			peerDialog.setName(peer.getName());
			peerDialog.setLyer(peer.isLiar());
			peerDialog.setColor(peer.getColor());
			peerDialog.setInitX(peer.getX());
			peerDialog.setInitY(peer.getY());
			peerDialog.setSelectedList(peer.getKnownPeers());
			/* TO REMOVE : 1 PEER -> N SUP
			 * peerDialog.setSelectedSuperviser(model.getSupervisers().indexOf(
					peer.getLogger().getSuperviser()));
					*/
			view.setCreatePeerDialog(peerDialog);
			peerDialog.setLocationRelativeTo(view);
			peerDialog.show();
		} else {
			model.getDocument().addMessage(
					Messages.getString("CdOpenModifPeer.0")); //$NON-NLS-1$
		}
	}
}