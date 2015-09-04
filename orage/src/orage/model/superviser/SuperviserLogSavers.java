/*
 * Created on 14 juin 2005
 *
 */
package orage.model.superviser;

import java.util.ArrayList;

import orage.model.Action;

/**
 * @author hpouylla
 */
public class SuperviserLogSavers {
	private ArrayList list;

	public SuperviserLogSavers() {
		list = new ArrayList();
	}
	
	public SuperviserLogSavers (SuperviserLogSavers toCopy) {
		list = new ArrayList();
		int size = toCopy.size();
		
		for (int i = 0; i < size; i ++) {
			list.add(toCopy.get(i));
		}
		
	}
	
	public SuperviserLogSavers(ArrayList list) {
		this.list = list;
	}
	
	public void add (SuperviserLogSaver s) {
		list.add(s);
		
	}
	
	public void remove (int index) {
		list.remove (index);
	}
	
	public void remove (SuperviserLogSaver s) {
		list.remove (s);
	}
	
	public int indexOf(SuperviserLogSaver s) {
		return list.indexOf(s);
	}
	
	public SuperviserLogSaver get(int index) {
		return (SuperviserLogSaver)list.get(index);
	}
	
	public void addAction(Action a) {
		int size = list.size();
		
		for (int i = 0; i < size; i++) {
			get(i).addAction(a);
		}
	}
	
	public void addMessage(String s) {
		int size = list.size();
		
		for (int i = 0; i < size; i++) {
			get(i).addMessage(s);
		}
	}
	
	public ArrayList getList() {
		return list;
	}
	
	public int size() {
		return list.size();
	}
}
