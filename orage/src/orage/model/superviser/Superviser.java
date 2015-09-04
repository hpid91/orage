
package orage.model.superviser;

import java.util.Random;

import orage.model.Action;
import orage.model.ActionPonderation;
import orage.model.ActionPonderationCollection;
import orage.model.Board;
import orage.model.PeerCollection;
import orage.model.peer.Peer;
import orage.model.superviser.system.OragePetriNet;
import orage.model.superviser.system.Transition;

public class Superviser {

	// Name and id of the superviser
	private String id = "";
	private String name = "";
	
	private int ponderation = 1 ;
	
	// Board
	Board board;
	
	// Logger for actions
	private SuperviserLogSaver logger;
	
	// Supervised peers
	PeerCollection peers;
	
	// Known supervisors
	SuperviserCollection supervisers;
	
	// SuperviserAction ponderation
	private ActionPonderationCollection ponderations;

    // System Petri Net
    OragePetriNet petrinet;
    
	// Attributes to perform randomizations
	Random rd = new Random();
	Random actionrd = new Random();
	static final int NB_ACTIONS = 100;
	
	public Superviser (String n, Board b) {
		name = n ;
		board = b;
		logger = new SuperviserLogSaver(this, name);
		peers = new PeerCollection();
		petrinet = new OragePetriNet(peers);
        
		initPonderations(); 
		
		rd.setSeed(System.currentTimeMillis());
	}
	
	public Superviser (String i, String n, Board b) {
		id = i;
		name = n ;
		board = b;
		logger = new SuperviserLogSaver(this, name);
		
		initPonderations();
		
		rd.setSeed(System.currentTimeMillis());
	}
	
	public String toXML() {
		String s = "\t\t<superviser id=\"" + id + "\">\r\n" +
						"\t\t\t<name>" + name + "</name>\r\n" +
						"\t\t\t<ponderation>" + ponderation	+ "</ponderation>\r\n";
		
		// Action ponderations
		s = s + "\t\t\t<actionponderatioons>\r\n";
		ActionPonderation ap;
		int size = ponderations.size();
		for (int i = 0; i < size; i++) {
			ap =  ponderations.getActionPonderation(i);
			s = s + ap.toXML();
		}
		s = s + "\t\t\t</actionponderatioons>\r\n";
		
		// Supervised peers
		s = s + "\t\t\t<supervisedpeers>\r\n";
		Peer peer;
		size = peers.size();
		for (int i = 0; i < size; i++) {
			peer =  peers.get(i);
			s = s + "\t\t\t\t<supervisedpeer id=\"";
			s = s + peer.getId();
			s = s + "\" />\r\n";
		}
		s = s + "\t\t\t</supervisedpeers>\r\n";
		s = s +	"\t\t</superviser>\r\n";
		
		return s;
	}
	
   
	public String getName() {
		return name;
	}
	
	public SuperviserLogSaver getLogger() {
		return logger;
	}
	
	public void addPeer(Peer p) {
		peers.add(p);
	}
	
	public void removePeer(Peer p) {
		peers.remove(p);
	}
	
	public PeerCollection getPeers() {
		return peers;
	}
	
	public void setPeers(PeerCollection peers) {
		this.peers = peers;
        updatePetriNet();
	}
	
	public void addSuperviser(Superviser s) {
		supervisers.add(s);
	}
	
	public void removeSuperviser(Superviser s) {
		supervisers.remove(s);
	}
	
	public SuperviserCollection getSupervisers() {
		return supervisers;
	}

	public void setSupervisers(SuperviserCollection supervisers) {
		this.supervisers = supervisers;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public boolean equals(Object obj) {
		Superviser sup = (Superviser)obj; 
		return this.id.equals(sup.id);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ActionPonderationCollection getPonderations() {
		return ponderations;
	}

	public void setPonderations(ActionPonderationCollection ponderations) {
		this.ponderations = ponderations;
	}

	private void initPonderations() {
		ponderations = new ActionPonderationCollection();
		ponderations.addActionPonderation(3, SuperviserActionAddPeer.class);
		ponderations.addActionPonderation(3, SuperviserActionAddKPeer.class);
		ponderations.addActionPonderation(1, SuperviserActionRemovePeer.class);
		ponderations.addActionPonderation(1, SuperviserActionRemoveKPeer.class);
	}
	
    protected void updatePetriNet() {
        if (petrinet != null) petrinet.update(peers);
        else petrinet = new OragePetriNet(peers);
    }
    
    public void firePetriNetTransition (String label, String id) {
        petrinet.fireTransition(new Transition(label, id));
        
        
    }
    
   /* public void setAlarmPattern(AlarmPattern ap) {
        petrinet.setAlarmPattern(ap);
    }*/
    
    public void addAction (Action a) {
        logger.addAction(a);
    }
    
    public void addMessage (String msg) {
        logger.addMessage(msg);
    }
    
	public Peer makeAnAction(String kind) {
		SuperviserAction action = null;
		boolean ok = false;
		Peer target = null;
		
		if (kind.equals(SuperviserAction.ADDPEER_KIND)) {
			action = new SuperviserActionAddPeer(this);
		} else if (kind.equals(SuperviserAction.REMPEER_KIND)) {
			action = new SuperviserActionRemovePeer(this);
		} else if (kind.equals(SuperviserAction.ADDKPEER_KIND)) {
			action = new SuperviserActionAddKPeer(this);
		} else if (kind.equals(SuperviserAction.REMKPEER_KIND)) {
			action = new SuperviserActionRemoveKPeer(this);
		} else if (kind.equals(SuperviserAction.DIAGNOSE_KIND)) {
			action = new SuperviserActionDiagnose(this);
		}

		if (action != null)	{
			ok = action.execute();
			target = action.getPeerTarget();
		}
		
		if (ok)	{
            logger.addAction(action);
            // Update petri net for every kind of action except diagnosis
            if (!kind.equals(SuperviserAction.DIAGNOSE_KIND))  updatePetriNet();
        }
		
		return target;
	}
	
	public void makeAleaAction() {
		int actionNb = actionrd.nextInt(NB_ACTIONS);
		
		float sum = ponderations.getPonderationSum();
		
		if (actionNb < (int) (NB_ACTIONS * ((float)(ponderations
				.getPonderation(SuperviserActionAddPeer.class) / sum)) )) {
			makeAnAction(SuperviserAction.ADDPEER_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ((float) (ponderations
				.getPonderation(SuperviserActionAddPeer.class) + ponderations
				.getPonderation(SuperviserActionAddKPeer.class)) / sum ) ) ) {
			makeAnAction(SuperviserAction.ADDKPEER_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ( (float)(ponderations
				.getPonderation(SuperviserActionAddPeer.class)
				+ ponderations.getPonderation(SuperviserActionAddKPeer.class) + ponderations
				.getPonderation(SuperviserActionRemovePeer.class) ) / sum ))) {
			makeAnAction(SuperviserAction.REMPEER_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ( (float)(ponderations
				.getPonderation(SuperviserActionAddPeer.class)
				+ ponderations.getPonderation(SuperviserActionAddKPeer.class)
				+ ponderations.getPonderation(SuperviserActionRemovePeer.class) + ponderations
				.getPonderation(SuperviserActionRemoveKPeer.class) ) / sum ))) {
			makeAnAction(SuperviserAction.REMKPEER_KIND);
		}

	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public int getPonderation() {
		return ponderation;
	}
	
	public void setPonderation(int ponderation) {
		this.ponderation = ponderation;
	}
}
