
package orage.ui.common;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class JPanelButton extends JPanel {
	
	private JPanel subpanel = new JPanel();
	
	public JPanelButton() {
		super();
		subpanel.setLayout(new BoxLayout(subpanel, BoxLayout.X_AXIS));
		subpanel.add(Box.createHorizontalGlue());
		
		setBorder(new ButtonPanelBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(subpanel);
	}
	
	

	public void add(JButton button) {
		subpanel.add(button);
		subpanel.add(Box.createRigidArea(new Dimension(10, 0)));
		this.repaint();
	}
	
}
