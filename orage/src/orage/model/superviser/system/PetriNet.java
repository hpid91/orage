/*
 * Created on 13 juil. 2005
 *
 */
package orage.model.superviser.system;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {

    private List<Place> places = new ArrayList<Place>();
    private List<Transition> transitions = new ArrayList<Transition>();
    private List<Edge> edges = new ArrayList<Edge>();
    
    private OccurenceNet net = new OccurenceNet();
        
    public PetriNet() {
        super();
    }

    public void addPlace(Place p) {
        places.add(p);
    }
    
    public void addTransition (Transition t) {
        transitions.add(t);
    }
    
    public void addEdge (Edge e) {
        edges.add(e);
    }
    
    public Transition getTransition(String name, String id) {
        int size = transitions.size();
        Transition t = null;
        Transition temp = new Transition(name, id);
        for(int i = 0; i < size; i++) {
            if (transitions.get(i).equals(temp)) {
                t = transitions.get(i);
                temp = null;
                return t;
            }
        }
        return null;
    }
    
    public Place getPlace(String name, String id) {
        int size = places.size();
        Place t = null;
        Place temp = new Place(name, id);
    
        for(int i = 0; i < size; i++) {
            if (places.get(i).equals(temp)) {
                t = places.get(i);
                temp = null;
                return t;
            }
        }
        return null;
    }

    public void markPlace (Place p) {
        
        p.mark();
        net.addCondition(new Condition(p.getLabel(), p.getId()));
    }
 
    public void markPlace (String label, String id) {
        Place place = getPlace(label, id);
        place.mark();
        net.addCondition(new Condition(place.getLabel(), place.getId()));
    }
    
    
    public void removeNode(Node n) {
            
        List<Edge> edgesIn = getInEdges(n);
        List<Edge> edgesOut = getOutEdges(n);
        
        edges.removeAll(edgesIn);
        edges.removeAll(edgesOut);
        
        if (n instanceof Transition) transitions.remove(n);
        else if (n instanceof Place) places.remove(n);
    }
     
    public List<Edge> getInEdges(Node n) {
        List<Edge> result = new ArrayList<Edge>();
        
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            if (edges.get(i).getTarget().equals(n)) result.add(edges.get(i));
        }
        return result;
    }
        
    public List<Edge> getOutEdges(Node n) {
        List<Edge> result = new ArrayList<Edge>();
        
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            if (edges.get(i).getSource().equals(n)) result.add(edges.get(i));
        }
        return result;
    }
    
    public List<Transition> getTransitionsFromLabel(String s) {
        List<Transition> result = new ArrayList<Transition>();
        int size = transitions.size();
        for (int i = 0; i < size; i ++) {
            if (transitions.get(i).getLabel().equals(s))
                result.add(transitions.get(i));
        }
        return result;
    }
    
    private List<Place> getSourcesOf(Transition t) {
        List<Place> result = new ArrayList<Place>();
        List<Edge> list = getInEdges(t);
        int size = list.size();
        for (int i = 0 ; i < size; i++) {
            result.add((Place)list.get(i).getSource());
        }
        return result;
    }
    
    private List<Place> getTargetsOf(Transition t) {
        List<Place> result = new ArrayList<Place>();
        List<Edge> list = getOutEdges(t);
        int size = list.size();
        for (int i = 0 ; i < size; i++) {
            result.add((Place)list.get(i).getTarget());
        }
        return result;
    }
    
    public List<Place> getMarkedPlaces() {
        List<Place> result = new ArrayList<Place>();
        
        int size = places.size();
        
        for (int i = 0; i < size; i++) {
            if (places.get(i).isMarked()) result.add(places.get(i));
        }
        
        return result;
    }
    
    private List<Transition> getFireableTransitions() {
        List<Place> mPlaces = getMarkedPlaces();
        List<Transition> result = new ArrayList<Transition>();
        List<Edge> outedges;
        
        int size = mPlaces.size();
        
        for (int i = 0; i < size; i++) {
           outedges = getOutEdges(mPlaces.get(i));
           int size2 = outedges.size();
           
           for (int j = 0; j < size2; j++) {
               result.add((Transition)outedges.get(j).getTarget());
           }
            
        }
        
        return result;
    }
    /**
     * Return the synchronized product of the current petri net with
     * another one.
     * @param pn The other petri net
     * @return this x pn
     */
    public PetriNet getSynchronizedProduct(PetriNet pn) {
        PetriNet result = new PetriNet();
        
        result.places.addAll(pn.places);
        result.places.addAll(this.places);
        
        result.transitions.addAll(this.transitions);
        
        result.edges.addAll(this.edges);
        
        // Add transitions of pn not present in this.transitions
        int size = pn.transitions.size();
        List<Transition> t = null;
        List<Place> list = null;
        for (int i = 0; i < size; i++) {
            t = result.getTransitionsFromLabel(pn.transitions.get(i).getLabel());
            if (t.size()<= 0) { 
                result.addTransition(pn.transitions.get(i));
                result.edges.addAll(pn.getOutEdges(pn.transitions.get(i)));
                result.edges.addAll(pn.getInEdges(pn.transitions.get(i)));
            } else {
                for (int j = 0; j < t.size(); j++) {
                    t.get(j).setId(t.get(j).getId() + pn.transitions.get(i).getId());
                    list = pn.getSourcesOf(pn.transitions.get(i));
                    for (int k = 0; k < list.size(); k++) {
                        result.addEdge(new Edge(result.places.get(
                                                    result.places.indexOf(list.get(k))) ,
                                                 t.get(j)));
                    }
                    list = pn.getTargetsOf(pn.transitions.get(i));
                    for (int k = 0; k < list.size(); k++) {
                        result.addEdge(new Edge(t.get(j),
                                                result.places.get(
                                                    result.places.indexOf(list.get(k)))
                                                 ));
                    }
                }
            }
        }
        
        
        return result;
    }
    
    private boolean oneCanFire (List<Condition> cs, Transition t) {
        int size = cs.size();
        List<Place> placeSource;
        Place p;
        
        placeSource = getSourcesOf(t);
        int size2 = placeSource.size();
        
        for (int j = 0; j < size2; j++) {
            for (int i = 0; i < size; i ++) {
                p = getPlace(cs.get(i).getLabel(), cs.get(i).getId());
                // The transition is fireable by one place
                if (p != null && placeSource.get(j).equals(p) ) return true;
            }
        }
        
        return false;
    }
    private boolean canFire (String placeLabel, String placeId, Transition t) {
        
        List<Place> tempPlace;
        Place p = getPlace(placeLabel, placeId);
        
        if (p != null) {
            
            tempPlace = getSourcesOf(t);
                
            int size = tempPlace.size();
            for (int j = 0; j < size; j++) {
                if (tempPlace.get(j).equals(p)) return true;
            }
            
        }
        
        return false;
        
    }
    
   
    // CHANGER ALGO !!!!
    // Pour chaque condition sourceC sans target de mon ON faire
    //      List<Transition> ts = canFire (c.Label, c.Id, label);
    //      if ( ts.size() > 0) {
    //          Pour chaque t € ts faire
    //              ajouter l'event e de label label mon ON
    //              ajouter les arcs de sourceC vers e
    //              pour chaque place p target de t faire
    //                  ajouter une condition targetC basée sur p
    //                  ajouter un arc e -> targetC
    //              finpour
    //          finpour
    //      }
    // fin
    public boolean unfold (String transitionLabel) {
        
        List<Transition> ts = getTransitionsFromLabel(transitionLabel);
        List<Place> ps;
        List<Condition> temp = net.getFinalConditions();
        List<Condition> finalconditions = new ArrayList<Condition>();
        Event e = null;
        int size = ts.size();
        boolean cfire = false;
           
        for (int i = 0; i < size; i ++) {
                    
            
            
            finalconditions.addAll(temp);
            while(finalconditions.size() > 0) {
                cfire = canFire (finalconditions.get(0).getLabel(),
                        finalconditions.get(0).getId(),
                        ts.get(i));
                    
                if (cfire) {
                    // Add an event to the occurence net
                    e = new Event (ts.get(i).getLabel(), ts.get(i).getId());
                    net.addEvent(e);  
                    net.addEdge(new Edge(finalconditions.get(0), e));
                    List<Node> coset = net.getConcurrentNodesInList(finalconditions.get(0), finalconditions);
                        
                    for (int k = 0; k < coset.size(); k++) {
                        cfire = canFire (coset.get(k).getLabel(),
                                coset.get(k).getId(),
                                ts.get(i));
                        if (cfire) net.addEdge(new Edge(coset.get(k), e));
                        if (finalconditions.contains(coset.get(k))) finalconditions.remove(coset.get(k));
                    }
                    
                    ps = getTargetsOf(ts.get(i));
                    int size3 = ps.size();
                    for (int k = 0; k < size3; k ++) {
                        Condition c = new Condition (ps.get(k).getLabel(), ps.get(k).getId());
                        net.addCondition(c);
                        net.addEdge(new Edge(e, c));
                    }
                }
                
                finalconditions.remove(0);
            }  
        }
        
        return true;
    }
    
    public boolean unfoldTransition (Transition t) {
        
        List<Place> ps;
        List<Condition> finalconditions = net.getFinalConditions();
        Event e = null;
        boolean cfire = false;
        
                    
        while(finalconditions.size() > 0) {
            cfire = canFire (finalconditions.get(0).getLabel(),
                    finalconditions.get(0).getId(),
                    t);
                
            if (cfire) {
                // Add an event to the occurence net
                e = new Event (t.getLabel(), t.getId());
                net.addEvent(e);  
                net.addEdge(new Edge(finalconditions.get(0), e));
                List<Node> coset = net.getConcurrentNodesInList(finalconditions.get(0), finalconditions);
                    
                for (int k = 0; k < coset.size(); k++) {
                    cfire = canFire (coset.get(k).getLabel(),
                            coset.get(k).getId(),
                            t);
                    if (cfire) net.addEdge(new Edge(coset.get(k), e));
                    if (finalconditions.contains(coset.get(k))) finalconditions.remove(coset.get(k));
                }
                
                ps = getTargetsOf(t);
                int size3 = ps.size();
                for (int k = 0; k < size3; k ++) {
                    Condition c = new Condition (ps.get(k).getLabel(), ps.get(k).getId());
                    net.addCondition(c);
                    net.addEdge(new Edge(e, c));
                }
            }
            
            finalconditions.remove(0);
             
        }
        
        return true;
    }
    
    public boolean fireTransition (Transition t) {
    
        // Search edges with t as target
        List<Edge> edgesT = getInEdges(t);
        
        // Check all source places are marked
        Place p;
        int size = edgesT.size();
        for (int i = 0; i < size; i ++) {
            p = (Place)edgesT.get(i).getSource();
            if (!p.isMarked()) return false;
        }
        
        // Add an event to the occurence net
        //Event e = new Event (t.getLabel(), t.getId());
        //net.addEvent(e);
        // Unmark sources
        for (int i = 0; i < size; i ++) {
            p = (Place)edgesT.get(i).getSource();
            p.unmark();
            // Add edges to occurence net
            //net.addEdge(new Edge(net.getLastCondition(p.getLabel(), p.getId()), e));
        }
        
        edgesT = getOutEdges(t);
        size = edgesT.size();
        // Mark targets
        for (int i = 0; i < size; i ++) {
            p = (Place)edgesT.get(i).getTarget();
            p.mark();
            
            // Add edges to occurence net
            //Condition c = new Condition (p.getLabel(), p.getId());
            //net.addCondition(c);
            //net.addEdge(new Edge(e, c));
        }
                
        return unfoldTransition(t);
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
        String s = "";
        
        for (int i = 0; i < edges.size(); i++) {
            s = s + edges.get(i).getSource() + "->" + edges.get(i).getTarget() + "\r\n";
        }
        
        return s;
    }

    /**
     * @return Returns the net.
     */
    public OccurenceNet getNet() {
        return net;
    }

    /**
     * @return Returns the places.
     */
    public List<Place> getPlaces() {
        return places;
    }
}
