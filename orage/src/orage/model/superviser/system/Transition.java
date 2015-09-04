/*
 * Created on 5 juil. 2005
 *
 */
package orage.model.superviser.system;

/**
 * @author hpouylla
 */
public class Transition extends Node {

	/**
	 * @param label
	 * @param id
	 */
	public Transition(String label, String id) {
		super(label, id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "t(" + getLabel() +  "," + getId()+ ")";
	}
}
