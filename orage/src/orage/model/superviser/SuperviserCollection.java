
package orage.model.superviser;

import java.util.ArrayList;

import orage.model.Action;


public class SuperviserCollection {
	
	private ArrayList list;
	
	public SuperviserCollection() {
		list = new ArrayList();
	}
	
	public SuperviserCollection (SuperviserCollection toCopy) {
		list = new ArrayList();
		int size = toCopy.size();
		
		for (int i = 0; i < size; i ++) {
			list.add(toCopy.get(i));
		}
		
	}
	
	public SuperviserCollection(ArrayList list) {
		this.list = list;
	}
	
	public void add (Superviser superviser) {
		list.add(superviser);
		
	}
	
	public void remove (int index) {
		list.remove (index);
	}
	
	public void remove (Superviser superviser) {
		list.remove (superviser);
	}
	
	public int indexOf(Superviser sup) {
		return list.indexOf(sup);
	}
	public Superviser get(int index) {
		return (Superviser)list.get(index);
	}
	
	public String[] getListNames() {
		int size = list.size();
		String[] names = new String[size];
		
		String name = "";
		
		for (int i = 0; i < size; i++) {
			name = ((Superviser)list.get(i)).getName();
			names[i] = name;
		}
		
		return names;
	}
	
	public Superviser getFromId(String id) {
		int size = list.size();
		Superviser result = null;
		
		for (int i = 0; i < size; i++) {
			if (((Superviser)list.get(i)).getId().equals(id)) {
				result = (Superviser)list.get(i);
			}
		}
		return result;
	}
	
     public void addAction(Action a) {
         int size = list.size();
         
         for (int i = 0; i < size; i++) {
             get(i).addAction(a);
         }
     }

     public void notify(String label, String id) {
         int size = list.size();
         
         for (int i = 0; i < size; i++) {
             get(i).firePetriNetTransition(label, id);
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
