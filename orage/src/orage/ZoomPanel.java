package orage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 
public class ZoomPanel extends JPanel {
	private JComboBox zoom_combo;
	private JPanel widget_panel;
	private ZoomedPanel child_panel;
 
	public ZoomPanel(ZoomedPanel child) {
		super(new BorderLayout());
		zoom_combo = new JComboBox(new Double[]{new Double(0.10), new Double(0.25), new Double(0.33), new Double(0.50), new Double(0.75), new Double(1.00), new Double(1.25), new Double(1.50), new Double(2.00), new Double(3.00), new Double(4.00), new Double(5.00), });
		zoom_combo.setEditable(true);
		zoom_combo.setSelectedIndex(5);
		zoom_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				child_panel.setZoomFactor(((Double)zoom_combo.getSelectedItem()).doubleValue());
				revalidate();
				repaint();
			}
		});
		widget_panel = new JPanel();
		widget_panel.add(zoom_combo);
		add(widget_panel, BorderLayout.NORTH);
		child_panel = child;
		JPanel pan1 = new JPanel(new BorderLayout());
		JPanel pan2 = new JPanel(new BorderLayout());
		pan1.add(pan2, BorderLayout.NORTH);
		pan2.add(child_panel, BorderLayout.WEST);
		add(pan1, BorderLayout.CENTER);
	}
 
	public JPanel getChild() {
		return child_panel;
	}
 
	private static class ZoomedPanel extends JPanel {
		private double zoomFactor;
 
		public ZoomedPanel() {
			this(true);
		}
 
		public ZoomedPanel(boolean isDoubleBuffered) {
			this(new FlowLayout(), isDoubleBuffered);
		}
 
		public ZoomedPanel(LayoutManager layout) {
			this(layout, true);
		}
 
		public ZoomedPanel(LayoutManager layout, boolean isDoubleBuffered) {
			super(layout, isDoubleBuffered);
			zoomFactor = 1.0;
		}
 
		public void setZoomFactor(double aZoomFactor) {
			zoomFactor = aZoomFactor;
		}
 
		public void paint(Graphics g) {
			int w = getWidth();
			int h = getHeight();
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2D = (Graphics2D)bi.getGraphics();
			super.paint(g2D);
			Rectangle clipBounds = g.getClipBounds();
			g.setClip(clipBounds.x, clipBounds.y,
					  (int)(clipBounds.width * zoomFactor),
					  (int)(clipBounds.height * zoomFactor));
			((Graphics2D)g).drawImage(bi, AffineTransform.getScaleInstance(zoomFactor, zoomFactor), null);
		}
	}
 
	public static void main(String[] args) {
		final JFrame frame = new JFrame("TestZoomPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ZoomedPanel insidePanel = new ZoomedPanel(new GridLayout(2, 2));
		insidePanel.add(new JButton("totot"));
		insidePanel.add(new JLabel("tutututu"));
		insidePanel.add(new JTextArea("qmsdfkqsdmlfkqsd\nqsdfqsdfqsdfkqsdf\nqsdfqsdfqsdf\nqsdfqsdfqsdfqsdf"));
		insidePanel.add(new JTextField("sdqsdfqsdf"));
		ZoomPanel zoomPanel = new ZoomPanel(insidePanel);
		frame.setContentPane(zoomPanel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}