
package orage.control;

import orage.model.Model;
import orage.ui.main.OrageWindow;
import orage.ui.main.SplashScreen;

public class CdShowSplash extends Command {
	public CdShowSplash(Model m, OrageWindow v) {
		super(m,v);
	}
	
	
	public void execute() {
		SplashScreen splash = new SplashScreen(view);
		
	}
}
