/*
 * Created on 5 juil. 2005
 *
 */
package orage.model.superviser.system;

/**
 * @author hpouylla
 */
public class Node {

	private String label;
	private String id;
    protected boolean marked = false;
	
	
	/**
	 * @param label
	 * @param id
	 */
	public Node(String label, String id) {
		super();
		this.label = label;
		this.id = id;
	}
	
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		return ( (((Node)arg0).id.equals(this.id)) &&
				( ((Node)arg0).label.equals(this.label))); 
	}
	
    public void mark() {}
        
    public void unmark() {}
    
    public boolean isMarked() {return false;}
}
