package orage.model;

import java.util.ArrayList;


public class ActionPonderationCollection {

	private int SUM = 0;
	
	private ArrayList ponderations;

	public ActionPonderationCollection() {
		ponderations = new ArrayList();
	}
	
	public void addActionPonderation(ActionPonderation ap) {
		ponderations.add(ap);
		computeSum();
	}

	public void addActionPonderation(int f, Class c) {
		ponderations.add(new ActionPonderation(f, c));
		computeSum();
	}

	public int getPonderation(Class c) {
		int size = ponderations.size();
		int result = 0;

		for (int i = 0; i < size; i++) {
			if (((ActionPonderation) ponderations.get(i)).getActionClass()
					.equals(c)) {
				result = ((ActionPonderation) ponderations.get(i)).getCoef();
			}
		}

		return result;
	}
	
	public void setPonderation(Class c, int f) {
		int size = ponderations.size();

		for (int i = 0; i < size; i++) {
			if (((ActionPonderation) ponderations.get(i)).getActionClass()
					.equals(c)) {
				((ActionPonderation) ponderations.get(i)).setCoef(f);
			
			}
		}
		computeSum();

	}
	
	private void computeSum() {
		int size = ponderations.size();
		SUM = 0;
		for (int i = 0; i < size; i++) {
			SUM = SUM +	((ActionPonderation) ponderations.get(i)).getCoef();
		}
	}
	public int getPonderationSum() {
		return SUM;
	}
	
	public ActionPonderation getActionPonderation(int index) {
		return (ActionPonderation) ponderations.get(index);
	}
	
	public int size() {
		return ponderations.size();
	}
}