
package orage.ui.main;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import orage.control.Controller;


public class OrageMenuBar extends JMenuBar {

	// Controller 
	private ActionListener listener;
	
	// Graphical elements
	private JMenu simuMenu;
	
	public OrageMenuBar(ActionListener l) {
		super();
		listener = l;
		init();
	}
	
	private void init() {
		JMenu fileMenu = new JMenu("File");
		
		ImageIcon imgNew = new ImageIcon("icons/new.png");
		JMenuItem fileMenuNew = new JMenuItem("New simulation", imgNew);
		fileMenuNew.setActionCommand(Controller.AC_MENU_NEWSIMUL);
		fileMenuNew.addActionListener(listener);
		
		JMenuItem fileMenuOpen = new JMenuItem("Open simulation");
		fileMenuOpen.setActionCommand(Controller.AC_MENU_OPENSIMUL);
		fileMenuOpen.addActionListener(listener);
		
		JMenuItem fileMenuSave = new JMenuItem("Save simulation");
		ImageIcon imgSave = new ImageIcon("icons/save.png");
		fileMenuSave.setIcon(imgSave);
		fileMenuSave.setActionCommand(Controller.AC_MENU_SAVESIMUL);
		fileMenuSave.addActionListener(listener);
		
		JMenuItem fileMenuQuit = new JMenuItem("Exit");
		fileMenuQuit.setActionCommand(Controller.AC_MENU_EXIT);
		fileMenuQuit.addActionListener(listener);
		
		fileMenu.add(fileMenuNew);
		fileMenu.add(fileMenuOpen);
		fileMenu.add(fileMenuSave);
		fileMenu.addSeparator();
		fileMenu.add(fileMenuQuit);
		
		simuMenu = new JMenu("Simulation");
		
		/*JMenuItem simuMenuSup = new JMenuItem("Supervisers");
		simuMenuSup.setActionCommand(Controller.AC_MENU_SUPERVISERS);
		simuMenuSup.addActionListener(listener);
		simuMenu.setEnabled(false);
		simuMenu.add(simuMenuSup);*/
		
		JMenuItem simuMenuPref = new JMenuItem("Preferences");
		simuMenuPref.setActionCommand(Controller.AC_MENU_PREFERENCES);
		simuMenuPref.addActionListener(listener);
		simuMenu.setEnabled(false);
		//simuMenu.add(simuMenuSup);
		simuMenu.add(simuMenuPref);
		
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem helpMenuAbout = new JMenuItem("About");
		helpMenuAbout.setActionCommand(Controller.AC_MENU_SPLASH);
		helpMenuAbout.addActionListener(listener);
		
		helpMenu.add(helpMenuAbout);
		
		this.add(fileMenu);
		this.add(simuMenu);
		this.add(helpMenu);
	}
	
	public void setSimuMenuEnabled(boolean b) {
		simuMenu.setEnabled(b);
	}
}
