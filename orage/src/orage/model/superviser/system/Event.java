/*
 * Created on 8 juil. 2005
 *
 */
package orage.model.superviser.system;

public class Event extends Node{

    /**
     * @param label
     * @param id
     */
    public Event(String label, String id) {
        super(label, id);
    }
 
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "e(" + getLabel() +  "," + getId()+ ")";
    }
    
    /* (non-Javadoc)
     * @see orage.model.superviser.system.Node#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        return ( super.equals(arg0) && ((Event)arg0).hashCode() == this.hashCode() );
    }

}
