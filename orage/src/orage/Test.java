
package orage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import orage.model.superviser.system.Edge;
import orage.model.superviser.system.OccurenceNet;
import orage.model.superviser.system.PetriNet;
import orage.model.superviser.system.Place;
import orage.model.superviser.system.Transition;


public class Test implements ActionListener {

	public Test() {
        PetriNet p = new PetriNet();
        
        Place p1 = new Place ("1", "0");
        Place p2 = new Place ("2", "1");
        Place p3 = new Place ("3", "2");
        Place p4 = new Place ("4", "3");
        Place p5 = new Place ("5", "4");
        Place p6 = new Place ("6", "5");
        Place p7 = new Place ("7", "6");
        
        p.addPlace(p1);
        p.addPlace(p2);
        p.addPlace(p3);
        p.addPlace(p4);
        p.addPlace(p5);
        p.addPlace(p6);
        p.addPlace(p7);
        
        Transition beta1 = new Transition("beta", "beta1");
        Transition beta2 = new Transition("beta", "beta2");
        Transition alpha1 = new Transition("alpha", "alpha1");
        Transition alpha2 = new Transition("alpha", "alpha2");
        Transition rho1 = new Transition("rho", "rho1");
        Transition rho2 = new Transition("rho", "rho2");
        
        p.addTransition(beta1);
        p.addTransition(beta2);
        p.addTransition(alpha1);
        p.addTransition(alpha2);
        p.addTransition(rho1);
        p.addTransition(rho2);
        
        p.addEdge(new Edge(p1, beta1));
        p.addEdge(new Edge(p1, beta2));
        p.addEdge(new Edge(p7, beta2));
        p.addEdge(new Edge(p2, rho1));
        p.addEdge(new Edge(p3, alpha1));
        p.addEdge(new Edge(p4, alpha1));
        p.addEdge(new Edge(p4, alpha2));
        p.addEdge(new Edge(p5, rho2));
        p.addEdge(new Edge(beta1, p2));
        p.addEdge(new Edge(beta2, p2));
        p.addEdge(new Edge(beta2, p3));
        p.addEdge(new Edge(alpha1, p5));
        p.addEdge(new Edge(alpha1, p7));
        p.addEdge(new Edge(alpha2, p6));
        p.addEdge(new Edge(rho1, p1));
        p.addEdge(new Edge(rho2, p4));
        
        PetriNet a = new PetriNet();
        
        Place pi = new Place ("i", "7");
        Place pii = new Place ("ii", "8");
        Place piii = new Place ("iii", "9");
        Place piv = new Place ("iv", "10");
        
        a.addPlace(pi);
        a.addPlace(pii);
        a.addPlace(piii);
        a.addPlace(piv);
        
        Transition betaa = new Transition("beta", "betaa");
        Transition alphaa = new Transition("alpha", "alphaa");
        Transition rhoa = new Transition("rho", "rhoa");
        
        a.addTransition(betaa);
        a.addTransition(alphaa);
        a.addTransition(rhoa);
        
        a.addEdge(new Edge(pi, betaa));
        a.addEdge(new Edge(betaa, pii));
        a.addEdge(new Edge(pii, alphaa));
        a.addEdge(new Edge(alphaa, piii));
        a.addEdge(new Edge(piii, rhoa));
        a.addEdge(new Edge(rhoa, piv));
        
        // P x A
        PetriNet sp = p.getSynchronizedProduct(a);
        
        //System.out.println(sp);
        
        sp.markPlace(p1);
        sp.markPlace(p7);
        sp.markPlace(p4);
        sp.markPlace("i", "7");
        
        /*// fire transitions following alarm pattern A
        List<Transition> t = sp.getTransitionsFromLabel("beta");
        int size = t.size();
        for (int i = 0; i < size; i++) {
            sp.fireTransition(t.get(i));
        }*/
       sp.unfold("beta");
       sp.unfold("alpha");
       sp.unfold("rho");
       
       sp.getNet().project(p);
       
       System.out.println(sp.getNet());
       
       List <OccurenceNet> conf = sp.getNet().getConfigurations();
       for (int i = 0; i < conf.size(); i++) {
           System.out.println("************* CONF " + i + " **************");
           conf.get(i).order();

           System.out.print(conf.get(i));
          
           System.out.println("");
       }
             
         
      //  Condition c = sp.getNet().getLastCondition("6", "5");
        
        /*List<Node> coset = sp.getNet().getCoset(c);
        int size = coset.size();
        System.out.println("COSET : "  + c + "**********");
        for (int ic1 = 0; ic1 < size; ic1++) {
            System.out.print(" " + coset.get(ic1));
        }
        System.out.println(" ");
        
        List<Node> postfix = sp.getNet().getPrefix(c);
        int size = postfix.size();
        System.out.println("PREFIX : "  + c + "**********");
        for (int ic1 = 0; ic1 < size; ic1++) {
            System.out.print(" " + postfix.get(ic1));
        }*/
        
       /* t = sp.getTransitionsFromLabel("alpha");
        size = t.size();
        for (int i = 0; i < size; i++) {
            sp.fireTransition(t.get(i));
        }
        
        t = sp.getTransitionsFromLabel("rho");
        size = t.size();
        for (int i = 0; i < size; i++) {
            sp.fireTransition(t.get(i));
        }*/
        
        
        /*Board board = new Board(20, 20, 4,0);
        Superviser sup = new Superviser("sup1", board);
        
        SuperviserLogSavers slss = new SuperviserLogSavers();
        slss.add(sup.getLogger());
        
        Peer alpha = new Peer("alpha", 1, 1, board, false, false, Color.YELLOW, slss);
        alpha.setId("1");
        Peer beta = new Peer("beta", 10, 10, board, true, false, Color.BLUE, slss);
        beta.setId("2");
        Peer epsilon = new Peer("epsilon", 17, 4, board, false, false, Color.CYAN, slss);
        epsilon.setId("3");
        Peer bishop = new Peer("bishop", 12, 19, board, true, false, Color.RED, slss);
        bishop.setId("4");
        
        // A -> B -> E -> A ...
        alpha.addAKnownPeer(beta);
        beta.addAKnownPeer(epsilon);
        epsilon.addAKnownPeer(alpha);
        alpha.addAKnownPeer(bishop);
        beta.addAKnownPeer(bishop);
        epsilon.addAKnownPeer(bishop);
        bishop.addAKnownPeer(beta);
        bishop.addAKnownPeer(epsilon);
        bishop.addAKnownPeer(alpha);
        
        PeerCollection collec = new PeerCollection();
        
        collec.add(alpha);
        collec.add(beta);
        collec.add(epsilon);
        collec.add(bishop);
        
        AlarmPattern pattern = new AlarmPattern();
        
        SystemModel sys = new SystemModel(collec);
        Transition t = sys.getTransition(Labels.TRANSITION_ACTIONKILL, alpha.getId());
        sys.fireTransition(t);       
        
        System.out.println("PETRI NET : " + sys);
        System.out.println("OCCURENCE NET : " + sys.getNet());
        
        */
        
        
		/*OccurenceNet on = new OccurenceNet();
        
        Condition c11 = new Condition("1", "1.1");
        Condition c71 = new Condition("7", "7.1");
        Condition c221 = new Condition("2", "2.2.1");
        Condition c222 = new Condition("2", "2.2.2");
        Condition c32 = new Condition("3", "3.2");
        Condition c42 = new Condition("4", "4.2");
        Condition c131 = new Condition("1", "1.3.1");
        Condition c132 = new Condition("1", "1.3.2");
        Condition c73 = new Condition("7", "7.3");
        Condition c53 = new Condition("5", "5.3");
        Condition c63 = new Condition("6", "6.3");
        Condition c44 = new Condition("4", "4.4");
        
        Event ebeta11 = new Event("beta", "beta.1.1");
        Event ebeta12 = new Event("beta", "beta.1.2");
        Event erho21 = new Event("rho", "rho.2.1");
        Event erho22 = new Event("rho", "rho.2.2");
        Event ealpha21 = new Event("alpha", "alpha.2.1");
        Event ealpha22 = new Event("alpha", "alpha.2.2");
        Event erho3 = new Event("rho", "rho.3");
        
        on.addEvent(ebeta11);
        on.addEvent(ebeta12);
        on.addEvent(erho21);
        on.addEvent(erho22);
        on.addEvent(ealpha21);
        on.addEvent(ealpha22);
        on.addEvent(erho3);
        
        
        on.addCondition(c11);
        on.addCondition(c71);
        on.addCondition(c221);
        on.addCondition(c222);
        on.addCondition(c32);
        on.addCondition(c42);
        on.addCondition(c131);
        on.addCondition(c132);
        on.addCondition(c73);
        on.addCondition(c53);
        on.addCondition(c63);
        on.addCondition(c44);
        
        
        on.addEdge(new Edge(c11, ebeta11));
        on.addEdge(new Edge(c11, ebeta12));
        on.addEdge(new Edge(c71, ebeta12));
        on.addEdge(new Edge(ebeta11, c221));
        on.addEdge(new Edge(ebeta12, c222));
        on.addEdge(new Edge(ebeta12, c32));
        on.addEdge(new Edge(c221, erho21));
        on.addEdge(new Edge(c222, erho22));
        on.addEdge(new Edge(c32, ealpha21));
        on.addEdge(new Edge(c42, ealpha21));
        on.addEdge(new Edge(c42, ealpha22));
        on.addEdge(new Edge(erho21, c131));
        on.addEdge(new Edge(erho22, c132));
        on.addEdge(new Edge(ealpha21, c73));
        on.addEdge(new Edge(ealpha21, c53));
        on.addEdge(new Edge(ealpha22, c63));
        on.addEdge(new Edge(c53, erho3));
        on.addEdge(new Edge(erho3, c44));
        
        System.out.println(on);
         
        conf = on.getConfigurations();
        for (int i = 0; i < conf.size(); i++) {
            System.out.println("************* CONF " + i + " **************");
            conf.get(i).order();
            System.out.print(conf.get(i));
           
            System.out.println("");
        }
        /*List<Node> coset = on.getCoset(c73);
        int size = coset.size();
        System.out.println("COSET : "  + c73 + "**********");
        for (int ic1 = 0; ic1 < size; ic1++) {
            System.out.print(" " + coset.get(ic1));
        }
        System.out.println(" ");
        
        List<Node> postfix = on.getPostfix(ebeta12);
        size = postfix.size();
        System.out.println("POSTFIX : "  + ebeta12 + "**********");
        for (int ic1 = 0; ic1 < size; ic1++) {
            System.out.print(" " + postfix.get(ic1));
        }
        System.out.println(" ");*/
        /*System.out.println("erho3 # erho22 : " + on.areConflictEvent(erho3, erho22));
        System.out.println("erho22 # erho3 : " + on.areConflictEvent(erho22, erho3));
        System.out.println("ebeta11 # ebeta12 : " + on.areConflictEvent(ebeta11, ebeta12));
        System.out.println("erho22 # ealpha21 : " + on.areConflictEvent(erho22, ealpha21));
        System.out.println("erho22 # ealpha22 : " + on.areConflictEvent(erho22, ealpha22));
        System.out.println("erho3 # erho3 : " + on.areConflictEvent(erho3, erho3));*/
        
        /*System.out.println("c221 # c222 : " + on.areConflictConditions(c221, c222));
        System.out.println("c221 # c32 : " + on.areConflictConditions(c221, c32));
        System.out.println("c222 # c32 : " + on.areConflictConditions(c222, c32));*/
                
	}
	
	
	public void actionPerformed(ActionEvent e) {
		

	}
	public static void main(String[] args) {
		new Test();
	
	}
}
