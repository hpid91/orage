/*
 * Created on 8 juil. 2005
 *
 */
package orage.model.superviser.system;

public class Condition extends Node{
    /**
     * @param label
     * @param id
     */
    public Condition(String label, String id) {
        super(label, id);
    }
    
    public Condition(String label) {
        super(label, "");
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "c(" + getLabel() +  "," + getId()+ "," + hashCode() + ")";
    }

    /* (non-Javadoc)
     * @see orage.model.superviser.system.Node#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        return ( super.equals(arg0) && ((Condition)arg0).hashCode() == this.hashCode() );
    }
   
}
