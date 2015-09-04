
package orage.control;

import java.io.File;

import javax.swing.JFileChooser;

import orage.model.Model;
import orage.ui.main.OrageWindow;


public class CdSaveLogs extends Command {
	
	public CdSaveLogs(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		
		JFileChooser chooser = new JFileChooser();
		
		chooser.setMultiSelectionEnabled(false) ;
		
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setApproveButtonText("Select");
		
		int state = chooser.showSaveDialog(view);
		
		File choosen = chooser.getSelectedFile();
		if ( (choosen != null) && (state == JFileChooser.APPROVE_OPTION) ){
						
			String s = choosen.getAbsolutePath().replaceAll("\\\\", "/");
			model.saveLogs(s);
		}
		
		
	}
}