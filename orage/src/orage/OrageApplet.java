
package orage;

import java.applet.Applet;

import javax.swing.UIManager;

import orage.control.Controller;


public class OrageApplet extends Applet {

	public void init() {

		super.init();
		try {
			UIManager.setLookAndFeel ( 								 
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

			e.printStackTrace();
		}
		new Controller();
	}
	
	public void start() {
	
		super.start();
	}
	public void stop() {

		super.stop();
	}
}
