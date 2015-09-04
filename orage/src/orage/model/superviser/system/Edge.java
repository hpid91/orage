/*
 * Created on 5 juil. 2005
 *
 */
package orage.model.superviser.system;

/**
 * @author hpouylla
 */
public class Edge {

	private Node source;
	private Node target;
	
	/**
	 * @param source
	 * @param target
	 */
	public Edge(Node source, Node target) {
		super();
		this.source = source;
		this.target = target;
	}
	/**
	 * @return Returns the source.
	 */
	public Node getSource() {
		return source;
	}
	/**
	 * @param source The source to set.
	 */
	public void setSource(Node source) {
		this.source = source;
	}
	/**
	 * @return Returns the target.
	 */
	public Node getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(Node target) {
		this.target = target;
	}
    
    /* (non-Javadoc)
     * @see orage.model.superviser.system.Node#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        return ( super.equals(arg0) && ((Edge)arg0).hashCode() == this.hashCode() );
    }
    
}
