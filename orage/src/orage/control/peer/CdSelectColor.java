
package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;

public class CdSelectColor extends Command {

	public CdSelectColor(Model m, OrageWindow v) {
		super(m,v);
	}
	
	public void execute() {
		
		view.getCreatePeerDialog().setColor(view.getColorChooserDialog().getSelectedColor());
		view.closeColorChooserDialog();
	}
}