/*
 * Created on 7 juil. 2005
 *
 */
package orage.model.superviser.system;

import java.util.ArrayList;
import java.util.List;

public class AlarmPattern {

    private List<Place> places = new ArrayList<Place>();
    private List<Transition> transitions = new ArrayList<Transition>();
    private List<Edge> edges = new ArrayList<Edge>();
    
    private Transition current;
    
    public AlarmPattern() {
        super();
    }
    
    public void addPlace(Place p) {
        places.add(p);
    }

    public void addTransition (Transition t) {
        if (transitions.size() == 0) current = t;
        transitions.add(t);
    }
    
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    public Transition getTransition(String label) {
        
        int size = transitions.size();
        
        for (int i = 0; i < size; i++) {
            if (transitions.get(i).getLabel().equals(label)) {
                return transitions.get(i);
                
            }
        }
        
        return null;
    }
    
    public boolean hasTransition (String label) {
        int size = transitions.size();
        
        for (int i = 0; i < size; i++) {
            if (transitions.get(i).getLabel().equals(label)) {
                return true;
                
            }
        }
        return false;
    }
    
    public boolean isCurrent(String label) {
        if (current.getLabel().equals(label)) return true;
        return false;
    }
    
    public void next() {
        int i = transitions.indexOf(current);
        if (i < transitions.size() ) current = transitions.get(i+1);
    }
    
}
