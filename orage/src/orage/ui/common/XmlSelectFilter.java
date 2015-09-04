
package orage.ui.common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XmlSelectFilter extends FileFilter {
	

	public boolean accept(File f) {
		
		return (f.isDirectory() ||
				f.getName().toLowerCase().endsWith(".xml"));
	}
	public String getDescription() {
		
		return "XML Files";
	}
}
