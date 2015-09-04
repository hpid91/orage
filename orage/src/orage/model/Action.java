
package orage.model;

public abstract class Action {

	protected String kind = "";
	
	public abstract boolean execute();
	
	public abstract String toXML();
	
	public String getKind() {
		return kind;
	}
}
