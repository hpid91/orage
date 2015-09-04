/*
 * Created on 14 juin 2005
 *
 */
package orage.model.superviser;

import java.util.List;

import orage.model.superviser.system.Node;
import orage.model.superviser.system.OccurenceNet;

/**
 * @author hpouylla
 */
public class SuperviserActionDiagnose extends SuperviserAction {

    private List <OccurenceNet> conf;
    
	public SuperviserActionDiagnose(Superviser s) {
		super(s);

		kind = super.DIAGNOSE_KIND;
		
	}
	/* (non-Javadoc)
	 * @see orage.model.Action#execute()
	 */
	public boolean execute() {
        conf = superviser.petrinet.getNet().getConfigurations();
        List<Node> initCond;
        
        int size = conf.size(), size2;
        for (int i = 0; i < size; i++) {
           System.out.println("************* CONF " + i + " POSSIBLE CAUSES **************");
           conf.get(i).order();
           initCond = conf.get(i).getCosetsConditions();
           
           System.out.println(conf.get(i));
               
          
           System.out.println("");
        }
		return true;
	}
	
	public String toString() {
        String s = "";
        int size = conf.size(), size2;
        for (int i = 0; i < size; i++) {
           s = s + "************* CONF " + i + " POSSIBLE CAUSES **************\r\n";
           conf.get(i).order();
           s =  s + conf.get(i);
           s = s + "\r\n";
        }
		return s;//superviser.petrinet.getNet().toString();
	}
	
	/* (non-Javadoc)
	 * @see orage.model.Action#getKind()
	 */
	public String getKind() {
		return super.getKind();
	}
	/* (non-Javadoc)
	 * @see orage.model.Action#toXML()
	 */
	public String toXML() {
		return "";
	}
}
