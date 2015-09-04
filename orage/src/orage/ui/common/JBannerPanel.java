package orage.ui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JBannerPanel extends JPanel {
	
	private String text;
	private String subtext;
	
	public JBannerPanel(String text, String subtext) {
		super();
		this.text = text;
		this.subtext = subtext;
		
		JLabel lblText = new JLabel(text);
		lblText.setVerticalAlignment(JLabel.TOP);
		lblText.setHorizontalAlignment(JLabel.CENTER);
		lblText.setForeground(Color.WHITE);
		lblText.setBackground(new Color(145, 139, 156));
		lblText.setOpaque(true);
		lblText.setFont(new Font("SansSerif",  Font.BOLD, 13));
		
		JLabel lblSubText = new JLabel(subtext);
		lblSubText.setVerticalAlignment(JLabel.CENTER);
		lblSubText.setHorizontalAlignment(JLabel.CENTER);
		lblSubText.setForeground(Color.WHITE);
		lblSubText.setBackground(new Color(145, 139, 156));
		lblSubText.setOpaque(true);
		lblSubText.setFont(new Font("SansSerif",  Font.BOLD | Font.ITALIC, 10));
		
		JPanel panelText = new JPanel();
		panelText.setBorder(BorderFactory.createEmptyBorder());
		panelText.setBackground(new Color(145, 139, 156));
		panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
		panelText.add(Box.createRigidArea(new Dimension(25,20)));
		panelText.add(lblText);
		panelText.add(lblSubText);
		
		JLabel lblImgLeft = new JLabel(new ImageIcon("icons/logoDC.png"));
		JLabel lblImgRight = new JLabel(new ImageIcon("icons/logoIRISA.png"));
		
		setLayout(new GridLayout(1,3));
		add(lblImgLeft);
		add(panelText);
		add(lblImgRight);
		
		setPreferredSize(new Dimension(500, 70));
		setBackground(new Color(145, 139, 156));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

}
