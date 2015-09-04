/*
 * Created on 8 juil. 2005
 *
 */
package orage.model.superviser.system;

import java.util.ArrayList;
import java.util.List;

public class OccurenceNet {

    private List<Event> events = new ArrayList<Event>();
    private List<Condition> conditions = new ArrayList<Condition>();
    private List<Edge> edges = new ArrayList<Edge>();
    
    public void addEvent (Event e) {
        events.add(e);
    }
    
    public void addCondition(Condition c) {
        conditions.add(c);
    }
    
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    public void addOccurenceNet(OccurenceNet on) {
        int size = on.conditions.size();
        for (int i = 0; i < size; i ++) {
            if (!this.conditions.contains(on.conditions.get(i))) this.conditions.add(on.conditions.get(i));
        }
        
        size = on.events.size();
        for (int i = 0; i < size; i ++) {
            if (!this.events.contains(on.events.get(i))) this.events.add(on.events.get(i));
        }
        
        size = on.edges.size();
        for (int i = 0; i < size; i ++) {
            if (!this.edges.contains(on.edges.get(i))) this.edges.add(on.edges.get(i));
        }
    }
        
    private List<Edge> getInEdges(Node n) {
        List<Edge> result = new ArrayList<Edge>();
        
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            if (edges.get(i).getTarget().equals(n)) result.add(edges.get(i));
        }
        return result;
    }
    
    private List<Edge> getOutEdges(Node n) {
        List<Edge> result = new ArrayList<Edge>();
        
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            if (edges.get(i).getSource().equals(n)) result.add(edges.get(i));
        }
        return result;
    }
    
    private boolean isIntersectionNull(OccurenceNet l1, OccurenceNet l2) {
        
        int size2 = l2.conditions.size();
        
        for (int j = 0; j < size2; j++) {
            if (l1.conditions.contains(l2.conditions.get(j))) return false;
        }
        
        size2 = l2.events.size();
        for (int j = 0; j < size2; j++) {
            if (l1.events.contains(l2.events.get(j))) return false;
        }
        
        return true;
    }

    public void removeNode(Node n) {
        
        List<Edge> edgesIn = getInEdges(n);
        List<Edge> edgesOut = getOutEdges(n);
        Node temp;
        edges.removeAll(edgesIn);
        edges.removeAll(edgesOut);
        
        // Check if edgesOut targets have other sources
        int size = edgesOut.size();
        for (int i = 0; i < size; i++) { 
            if (getInEdges(edgesOut.get(i).getTarget()).size() == 0) {
                temp = edgesOut.get(i).getTarget();
                removeNode (temp);
                if (temp instanceof Condition) conditions.remove(temp);
                else if (temp instanceof Event) events.remove(temp);
            }
        }
    }
    
    private void purge() {
        List<Edge> edgesOut;
        List<Edge> edgesIn;
        List<Condition> toRemove = new ArrayList<Condition>();
        int size = conditions.size();
        for (int i = 0; i < size; i++) { 
            edgesOut = getOutEdges(conditions.get(i));
            edgesIn = getInEdges(conditions.get(i));
            if ( (edgesOut == null && edgesIn == null) || (edgesOut.size() == 0 && edgesIn.size() == 0)) {
                toRemove.add(conditions.get(i));
            }
        }
        conditions.removeAll(toRemove);
    }
    
    public void project(PetriNet pn) {
        List<String> labels = new ArrayList<String>();

        // get the places labels
        List<Place> places = pn.getPlaces();
        int size = places.size();
        for (int i = 0; i < size; i++) {
            labels.add(places.get(i).getLabel());
        }
        
        size  = conditions.size();
        for (int i = 0; i < size; i++) {
            if (!labels.contains(conditions.get(i).getLabel())) {
                removeNode(conditions.get(i));
                conditions.remove(i);
                size = conditions.size();
            }
        }
        
        // Some conditions may be orphans
        //purge();
    }
    
    public Condition getLastCondition(String label, String id) {
        Condition temp = new Condition (label, id);
        for (int i = conditions.size() - 1; i > -1; i--) {
            if (conditions.get(i).equals(temp)) return conditions.get(i);
        }
        return null;
    }
    
    private List<Node> getCausalNodes(Node c) {
        // Get source edges of c1
        List<Edge> sourceedges = getInEdges(c);
        List<Node> temp = new ArrayList<Node>();
        List<Node> result = new ArrayList<Node>();
        
        int size = sourceedges.size();
        for (int i = 0; i < size; i++) {
            temp.add(sourceedges.get(i).getSource());
        }
        
        size = temp.size();
        for (int i = 0; i < size; i++) {
            List<Edge> tempedges = getInEdges(temp.get(i));
            int size2 = tempedges.size();
            
            for (int j = 0; j < size2; j ++) {
                result.add(tempedges.get(j).getSource());
            }
        }
        
        return result;
    }
    /**
     * check if c2 < c1
     * @param c1
     * @param c2
     * @return
     */
    public boolean areCausal(Node c1, Node c2) {
        
        List<Node> liste = getCausalNodes(c2);
        boolean isprevious = false;
        
        int size = liste.size();
        
        for (int i = 0; i < size; i++) {
           if (liste.get(i)== c1) return true;
        }
        
        for (int i = 0; i < size && !isprevious ; i++) {
            isprevious = areCausal(c1, liste.get(i));
        }
        return isprevious;
    }
    
    public List<Node> getPrefix(Node c) {
        List<Node> result = getCausalNodes(c);
        
        int size = result.size();
        
        for (int i = 0; i < size; i++) {
            result.addAll(getPrefix(result.get(i)));
        }
        return result;
    }
    
    public OccurenceNet getPreset(Node n) {
        List<Edge> sourceedges = getInEdges(n);
        OccurenceNet temp = new OccurenceNet();

        if (n instanceof Condition) temp.conditions.add((Condition)n);
        else temp.events.add((Event)n);
        
        int size = sourceedges.size();
        for (int i = 0; i < size; i++) {
            temp.edges.add(sourceedges.get(i));
            //temp.add(sourceedges.get(i).getSource());
            temp.addOccurenceNet( getPreset(sourceedges.get(i).getSource()));
        }
        
        
        return temp;
    }
    
    public OccurenceNet getPostset(Node n) {
        List<Edge> targetedges = getOutEdges(n);
        OccurenceNet temp = new OccurenceNet();

        if (n instanceof Condition) temp.conditions.add((Condition)n);
        else temp.events.add((Event)n);
        
        int size = targetedges.size();
        for (int i = 0; i < size; i++) {
            temp.edges.add(targetedges.get(i));
            //temp.add(sourceedges.get(i).getSource());
            temp.addOccurenceNet( getPostset(targetedges.get(i).getTarget()));
        }
        
        
        return temp;
    }
    
    private List<Node> getConsequentNodes(Node c) {
        // Get source edges of c1
        List<Edge> targetedges = getOutEdges(c);
        List<Node> temp = new ArrayList<Node>();
        List<Node> result = new ArrayList<Node>();
        
        int size = targetedges.size();
        for (int i = 0; i < size; i++) {
            temp.add(targetedges.get(i).getTarget());
        }
        
        size = temp.size();
        for (int i = 0; i < size; i++) {
            List<Edge> tempedges = getOutEdges(temp.get(i));
            int size2 = tempedges.size();
            
            for (int j = 0; j < size2; j ++) {
                result.add(tempedges.get(j).getTarget());
            }
        }
        
        return result;
    }
     
    public List<Node> getPostfix(Node c) {
        List<Node> result = getConsequentNodes(c);
        
        int size = result.size();
        
        for (int i = 0; i < size; i++) {
            result.addAll(getPostfix(result.get(i)));
        }
        return result;
    }
    
    /**
     * Check c1#c2
     * @param c1
     * @param c2
     * @return
     */
    public boolean areConflictEvents(Event e1, Event e2) {
       
       if (e1 == e2) return false;
        
       OccurenceNet e1Preset = getPreset(e1);      
       OccurenceNet e2Preset = getPreset(e2); 
       
       return !isIntersectionNull(e1Preset, e2Preset) &&
               (!e1Preset.events.contains(e2) && !e2Preset.events.contains(e1));
    }

    public boolean areConflictConditions (Condition c1, Condition c2) {
        if (c1 == c2) return false;
        
        List<Edge> edgesC1 = getInEdges(c1);
        List<Edge> edgesC2 = getInEdges(c2);
        
        
        
        if (edgesC1.size() == 1 && edgesC2.size() == 1) {
            Event e1 = (Event)edgesC1.get(0).getSource();
            Event e2 = (Event)edgesC2.get(0).getSource();
            return areConflictEvents(e1, e2);
        }
        
        return false;
    }
    
    public List<Node> getCosetsConditions() {
        List<Node> result = new ArrayList<Node>();
        List<Node> set = new ArrayList<Node>();
        set.addAll(conditions);
        
        int size = set.size();
        result.add(set.get(size-1));
        for (int i = size-1; i  > -1; i--) {
            if(isConcurrent(set.get(i),result))
                result.add(set.get(i));
        }
        result.remove(0);
        return result;
    }
    
    public List<Node> getCoset(Node entrypoint) {
        List<Node> result = new ArrayList<Node>();
        List<Node> set = new ArrayList<Node>();
        
        if (entrypoint instanceof Condition) set.addAll(conditions);
        else set.addAll(events);
        
        int size = set.size();
        result.add(entrypoint);
        for (int i = size-1; i  > -1; i--) {
            if(isConcurrent(set.get(i), result))
                result.add(set.get(i));
        }
        result.remove(entrypoint);
        return result;
    }
    
    public List<Node> getConcurrentNodesInList(Condition entrypoint, List<Condition> set) {
        List<Node> result = new ArrayList<Node>();
        
        int size = set.size();
        result.add(entrypoint);
        for (int i = size-1; i  > -1; i--) {
            if(isConcurrent(set.get(i), result))
                result.add(set.get(i));
        }
        result.remove(entrypoint);
        return result;
    }
    
    private boolean isConcurrent(Node c1, List<Node> list) {
    
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!areConcurrent(c1, list.get(i))) return false;
        }
        return true;
    }
    
    public boolean areConcurrent (Node c1, Node c2) {
        if (c1 == c2) return false;
        
        boolean conflict = false;
        
        if (c1 instanceof Event && c2 instanceof Event) conflict = areConflictEvents((Event)c1, (Event)c2);
        if (c1 instanceof Condition && c2 instanceof Condition)
            conflict = areConflictConditions((Condition)c1, (Condition)c2);
        
        return (!areCausal(c1,c2) && !areCausal(c2,c1) && !conflict);
    }
    
    /**
     * Return the list of conditions without targeted events
     * @return
     */
    public List<Condition> getFinalConditions() {
        List<Condition> result = new ArrayList<Condition>();
        List<Edge> temp;
        
        int size = conditions.size();
        for (int i = 0; i < size; i++) {
           temp = getOutEdges(conditions.get(i));
           if (temp == null || temp.size() == 0) result.add(conditions.get(i));
        }
        
        return result;
    }
    
    /**
     * Return the list of conditions without source events
     * @return
     */
    public List<Condition> getInitialConditions() {
        List<Condition> result = new ArrayList<Condition>();
        List<Edge> temp;
        
        int size = conditions.size();
        for (int i = 0; i < size; i++) {
           temp = getInEdges(conditions.get(i));
           if (temp == null || temp.size() == 0) result.add(conditions.get(i));
        }
        
        return result;
    }
    
    public List<OccurenceNet> getConfigurations() {
        List<Condition> ends = getFinalConditions();
        List<OccurenceNet> result = new ArrayList<OccurenceNet>();
        List<Node> coset;
        OccurenceNet temp = new OccurenceNet();
        
        while(ends.size() > 0) {
            coset = getConcurrentNodesInList(ends.get(0), ends);
            int size = coset.size();
            temp.addOccurenceNet(getPreset(ends.get(0)));
            for (int i = 0; i < size; i++) {
                temp.addOccurenceNet(getPreset(coset.get(i)));
                ends.remove(coset.get(i));
            }
            result.add(temp);
            ends.remove(0);
            temp = new OccurenceNet();
        }
        return result;
    }
    
    public void order() {
        
        List<Condition> conds = new ArrayList<Condition>();
        int size = conditions.size();
        for (int i = 0; i < size ; i++) {
            if (getInEdges(conditions.get(i)).size() == 0) conds.add(conditions.get(i));
        }
        
        size = conds.size();
        OccurenceNet temp = new OccurenceNet();
        for (int i = 0; i < size ; i++) {
            temp.addOccurenceNet(getPostset(conds.get(i)));
        }
       
        this.conditions = temp.conditions;
        this.events = temp.events;
        this.edges = temp.edges;
    }
    
    public void getLevel() {
        List<Condition> temp = getInitialConditions();
        int size = temp.size();
        /*for (int i = 0; i < size ; i ++) {
            if (getOutEdges(temp.get(i)).size() > 0)
        }*/
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
        String s = "";
        int size = edges.size();
        
        if (size > 0) {
            for (int i = 0; i < size ; i++) {
                s = s + edges.get(i).getSource() +  "->" + edges.get(i).getTarget() +  "\r\n";
            }
        } else {
            size = conditions.size();
            for (int i = 0; i < size ; i++) {
                s = s + conditions.get(i) +  "\r\n";
            }
        }
        
        return s;
    }
    
}
