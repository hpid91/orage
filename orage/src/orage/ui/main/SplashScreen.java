
package orage.ui.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class SplashScreen extends Window implements MouseListener{
	private static final String LOGO_FILE_NAME = "icons/splash.png";

	Image logo = null;

	public SplashScreen(JFrame parent) {
		super(parent);
		// Load image and wait to complete
		logo = Toolkit.getDefaultToolkit().getImage(LOGO_FILE_NAME);

		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(logo, 0);
		try {
			tracker.waitForID(0);
		} catch (Exception e) {
		}

		// Set window size and position
		int w = logo.getWidth(this);
		int h = logo.getHeight(this);
		setSize(w, h);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocationRelativeTo(parent);
		
		addMouseListener(this);
		setVisible(true);
		show();
		
	}

	public void mouseClicked(MouseEvent e) {
		hide();
		dispose();
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(logo, 0, 0, this);
	}

}