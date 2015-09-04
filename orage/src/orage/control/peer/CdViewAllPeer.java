package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdViewAllPeer extends Command {
	
	
	public CdViewAllPeer(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		view.getBoardPanel().getBoardPanel().repaint();
		
	}
}
