
package orage.model;


public class ActionPonderation {


	private int coef = 1 ;
	private Class actionClass;
	
	public ActionPonderation (int coef, Class actionClass) {
		this.coef = coef;
		this.actionClass = actionClass;
	}
		
	public Class getActionClass() {
		return actionClass;
	}
	
	public void setActionClass(Class actionClass) {
		this.actionClass = actionClass;
	}
	
	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}
	
	public String toXML() {
		String s = "\t\t\t\t<actionponderation>\r\n" +
							"\t\t\t\t\t<coef>" + coef + "</coef>\r\n" +
							"\t\t\t\t\t<class>" + actionClass.getName() + "</class>\r\n" +
		 			"\t\t\t\t</actionponderation>\r\n";
		
		return s;
	}
}
