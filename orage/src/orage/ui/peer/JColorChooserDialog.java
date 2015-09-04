
package orage.ui.peer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import orage.control.Controller;
import orage.ui.common.JPanelButton;



public class JColorChooserDialog extends JDialog implements ChangeListener {

    protected JColorChooser tcc;
    protected JLabel banner;
    private ActionListener listener;
    private Color selectedColor = Color.YELLOW;
    
    public JColorChooserDialog(ActionListener l) {
        super();
        listener = l;
        
        
        JPanel panelGlobal = new JPanel(new BorderLayout());
        
        //Set up the banner at the top of the window
        banner = new JLabel("Select a color",
                            JLabel.CENTER);
        banner.setForeground(Color.WHITE);
        banner.setBackground(Color.DARK_GRAY);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 24));
        banner.setPreferredSize(new Dimension(100, 65));

        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(banner, BorderLayout.CENTER);

        //Set up color chooser for setting text color
        tcc = new JColorChooser(selectedColor);
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
        
        JPanelButton panelButton = new JPanelButton();
        JButton btSelect = new JButton("Select");
        btSelect.setActionCommand(Controller.AC_SELECT_COLOR);
        btSelect.addActionListener(listener);
        
        JButton btCancel = new JButton("Cancel");
        btCancel.setActionCommand(Controller.AC_CANCEL_SELECT_COLOR);
        btCancel.addActionListener(listener);
        
        panelButton.add(btSelect);
        panelButton.add(btCancel);
        
        panelGlobal.add(bannerPanel, BorderLayout.PAGE_START);
        panelGlobal.add(tcc, BorderLayout.CENTER);
        panelGlobal.add(panelButton, BorderLayout.PAGE_END);
        
        getContentPane().add(panelGlobal);
        pack();
     
    }

    public void stateChanged(ChangeEvent e) {
    	selectedColor = tcc.getColor();
    }
    
	public Color getSelectedColor() {
		return selectedColor;
	}
	
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
}
