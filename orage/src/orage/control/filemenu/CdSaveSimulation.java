
package orage.control.filemenu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import orage.control.Command;
import orage.model.Model;
import orage.ui.common.XmlSelectFilter;
import orage.ui.main.OrageWindow;


public class CdSaveSimulation extends Command {
	
	public CdSaveSimulation(Model m, OrageWindow v) {
		super(m,v);
	}
	
	public void execute() {
		JFileChooser chooser = new JFileChooser();
		
		FileFilter filter = new XmlSelectFilter();
		
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false) ;
		
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setApproveButtonText("Select");
		
		int state = chooser.showSaveDialog(view);
		
		File choosen = chooser.getSelectedFile();
		if ( (model != null) && (choosen != null) &&
				(state == JFileChooser.APPROVE_OPTION) ) {
						
			String s = choosen.getAbsolutePath().replaceAll("\\\\", "/");
			model.save(s);
		}
		
	}
}