
package orage.control.peer;

import orage.control.Command;
import orage.model.Model;
import orage.ui.main.OrageWindow;
import orage.ui.peer.JColorChooserDialog;

public class CdOpenColorChooser extends Command {

	public CdOpenColorChooser(Model m, OrageWindow v) {
		super(m,v);
	}
	
	public void execute() {
		JColorChooserDialog chooser = new JColorChooserDialog(view.getListener());
		view.setColorChooserDialog(chooser);
		chooser.setModal(true);
		chooser.setLocationRelativeTo(view);
		chooser.show();
	}
}
