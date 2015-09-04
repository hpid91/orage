/*
 * Created on 6 juil. 2005
 *
 */
package orage.model.superviser.system;

import orage.model.PeerCollection;
import orage.model.peer.Peer;

public class OragePetriNet {

	private PetriNet petrinet = new PetriNet();
	
        
	public OragePetriNet(PeerCollection peers) {
		super();
		init(peers);
	}

	private void init(PeerCollection peers) {
		int size = peers.size();
		Peer peer;
		
		
		for (int i = 0; i < size; i++) {
			peer = peers.get(i);
			
			Place ok = new Place(Labels.PLACE_OK, peer.getId());
			Place dead = new Place(Labels.PLACE_DEAD, peer.getId());
			Place alpha = new Place(Labels.PLACE_ALPHA, peer.getId());
			Place beta = new Place(Labels.PLACE_BETA, peer.getId());
            
            petrinet.addPlace(ok);
            petrinet.addPlace(dead);
            petrinet.addPlace(alpha);
            petrinet.addPlace(beta);
			
            // Initial marking
            if (peer.isDead()) petrinet.markPlace(dead);
            else petrinet.markPlace(ok);
            
			// Forget the talk, move and query action for the moment
			Transition peerAvg = new Transition(Labels.TRANSITION_ACTIONAVG, peer.getId());
			Transition peerGetQueryList = new Transition(Labels.TRANSITION_ACTIONGETQL, peer.getId());
			Transition peerKill = new Transition(Labels.TRANSITION_ACTIONKILL, peer.getId());
            Transition peerLive = new Transition(Labels.TRANSITION_ACTIONLIVE, peer.getId()); 
			Transition peerAvgOk = new Transition(Labels.TRANSITION_AVGOK, peer.getId());
			Transition peerAvgNotOk = new Transition(Labels.TRANSITION_AVGNOTOK, peer.getId());
			Transition peerQLNotOk = new Transition(Labels.TRANSITION_QLNOTOK, peer.getId());
			Transition peerQLOk = new Transition(Labels.TRANSITION_QLOK, peer.getId());
			
            petrinet.addTransition(peerAvg);
            petrinet.addTransition(peerGetQueryList);
            petrinet.addTransition(peerKill);
            petrinet.addTransition(peerAvgOk);
            petrinet.addTransition(peerAvgNotOk);
            petrinet.addTransition(peerQLNotOk);
            petrinet.addTransition(peerQLOk);
			
            petrinet.addEdge(new Edge(ok, peerAvg));
            petrinet.addEdge(new Edge(ok, peerGetQueryList));
            
            petrinet.addEdge(new Edge(peerAvg, alpha));
            petrinet.addEdge(new Edge(alpha, peerAvgOk));
            petrinet.addEdge(new Edge(alpha, peerAvgNotOk));
            petrinet.addEdge(new Edge(peerAvgOk, ok));
            petrinet.addEdge(new Edge(peerAvgNotOk, dead));
            
            petrinet.addEdge(new Edge(peerGetQueryList, beta));
            petrinet.addEdge(new Edge(beta, peerQLNotOk ));
            petrinet.addEdge(new Edge(beta, peerQLOk));
            petrinet.addEdge(new Edge(peerQLOk, ok ));
            petrinet.addEdge(new Edge(peerQLNotOk, dead ));
            
            petrinet.addEdge(new Edge(ok, peerKill));
            petrinet.addEdge(new Edge(peerKill, dead));
            petrinet.addEdge(new Edge(dead, peerLive));
            petrinet.addEdge(new Edge(peerLive, ok));
		}
		
		// PRODUIT SYNCHONE : TO DO
		// In fact it is not really a synchronization because we haven't modelized the answering transition.
		// Therefore, we add arcs regarding the topology. It's a synchronization with implicit transitions which
		// don't exist and would have to disappear otherwise.
	
		for (int i = 0; i < size; i++) {
			peer = peers.get(i);
			PeerCollection kpeers = peer.getKnownPeers();
			Place pok, pdead, pkok, pkdead;
            
            pok = getPlace (Labels.PLACE_OK, peer.getId());
            pdead = getPlace (Labels.PLACE_DEAD, peer.getId());
            
			Transition peerAvg = getTransition(Labels.TRANSITION_ACTIONAVG, peer.getId());
            //Transition peerGetQueryList = getTransition("PeerActionGetQueryList", peer.getId());
            Transition peerAvgOk = getTransition(Labels.TRANSITION_AVGOK, peer.getId());
            Transition peerAvgNotOk = getTransition(Labels.TRANSITION_AVGNOTOK, peer.getId());
            Transition peerQLOk = getTransition(Labels.TRANSITION_QLOK, peer.getId());
            Transition peerQLNotOk = getTransition(Labels.TRANSITION_QLNOTOK, peer.getId());
			
            int size2 = kpeers.size();
            if (size2 > 0) {
                petrinet.removeNode(peerAvg);
                petrinet.removeNode(peerAvgOk);
                petrinet.removeNode(peerAvgNotOk);
                petrinet.removeNode(peerQLOk);
                petrinet.removeNode(peerQLNotOk);
            }
			for (int j = 0; j < size2; j++) {
				pkok = getPlace (Labels.PLACE_OK, kpeers.get(j).getId());
				pkdead = getPlace (Labels.PLACE_DEAD, kpeers.get(j).getId());
         
                Transition peerAvgK = new Transition(Labels.TRANSITION_ACTIONAVG, 
                                                    peer.getId() + kpeers.get(j).getId());
                Transition peerAvgOkK = new Transition(Labels.TRANSITION_AVGOK, 
                                                    peer.getId() + kpeers.get(j).getId());
                Transition peerAvgNotOkK = new Transition(Labels.TRANSITION_AVGNOTOK, 
                                                    peer.getId() + kpeers.get(j).getId());
                Transition peerQLOkK = new Transition(Labels.TRANSITION_QLOK, 
                                                    peer.getId() + kpeers.get(j).getId());
                Transition peerQLNotOkK = new Transition(Labels.TRANSITION_QLNOTOK, 
                                                    peer.getId() + kpeers.get(j).getId());
                
                petrinet.addTransition(peerAvgK);
                petrinet.addTransition(peerAvgOkK);
                petrinet.addTransition(peerAvgNotOkK);
                petrinet.addTransition(peerQLOkK);
                petrinet.addTransition(peerQLNotOkK);
               
                petrinet.addEdge(new Edge(pok, peerAvgK));
                petrinet.addEdge(new Edge(peerAvgK, pok ));
                
                petrinet.addEdge(new Edge(pok, peerAvgOkK));
                petrinet.addEdge(new Edge(peerAvgOkK, pok ));
                
                petrinet.addEdge(new Edge(pok, peerQLOkK));
                petrinet.addEdge(new Edge(peerQLOkK, pok ));
                
                petrinet.addEdge(new Edge(pok, peerAvgNotOkK));
                petrinet.addEdge(new Edge(peerAvgNotOkK, pdead ));
                
                petrinet.addEdge(new Edge(pok, peerQLNotOkK));
                petrinet.addEdge(new Edge(peerQLNotOkK, pdead ));
                
                petrinet.addEdge(new Edge(pkok, peerAvgK));
                petrinet.addEdge(new Edge(peerAvgK, pkok ));
                
                petrinet.addEdge(new Edge(pkok, peerAvgOkK));
                petrinet.addEdge(new Edge(peerAvgOkK, pkok ));
                
                petrinet.addEdge(new Edge(pkok, peerQLOkK));
                petrinet.addEdge(new Edge(peerQLOkK, pkok ));
                
                petrinet.addEdge(new Edge(pkdead, peerAvgNotOkK));
                petrinet.addEdge(new Edge(peerAvgNotOkK, pkdead ));
                
                petrinet.addEdge(new Edge(pkdead, peerQLNotOkK));
                petrinet.addEdge(new Edge(peerQLNotOkK, pkdead ));
			}
			kpeers = null;
			
		}
	}
	
    public void update(PeerCollection peers) {
        petrinet = null;
        petrinet = new PetriNet();
        init(peers);
    }

  /* public void setAlarmPattern(AlarmPattern ap) {
        pattern = ap;
    }*/
    
	public Transition getTransition(String name, String id) {
		return petrinet.getTransition(name, id);
	}
	
	public Place getPlace(String name, String id) {
        return petrinet.getPlace(name, id);
	}


	public boolean fireTransition (Transition t) {
    
        return petrinet.fireTransition(t);
        
    }
    
        
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	
		return petrinet.toString();
	}

    /**
     * @return Returns the net.
     */
    public OccurenceNet getNet() {
        return petrinet.getNet();
    }
	
	
}
