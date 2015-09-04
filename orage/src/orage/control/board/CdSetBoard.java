
package orage.control.board;

import orage.control.Command;
import orage.model.Board;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdSetBoard extends Command {
	
	public CdSetBoard(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		if (view.getSimulPanel() != null) {
			int xsize = view.getBoardPanel().getSpinX();
			int ysize = view.getBoardPanel().getSpinY();
			int max = view.getBoardPanel().getSpinMax();
			int min = view.getBoardPanel().getSpinMin();
			
			boolean ok = model.setBoard(new Board(xsize, ysize, max, min));
			
			view.getBoardPanel().setBoard(model.getBoard());
			
			view.getSimulPanel().setPeerPanelEnabled(ok);
			
		}
	}
}
