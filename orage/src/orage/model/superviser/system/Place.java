/*
 * Created on 5 juil. 2005
 *
 */
package orage.model.superviser.system;

/**
 * @author hpouylla
 */
public class Place extends Node {
	
    
	/**
	 * @param label
	 * @param id
	 */
	public Place(String label, String id) {
		super(label, id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "p(" + getLabel() + "," + getId()+ ")";
	}
	
    public void mark() {
        marked = true;
    }
	
    public void unmark() {
        marked = false;
    }
    
    public boolean isMarked() {
        return marked;
    }
}
