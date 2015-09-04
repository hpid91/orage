
package orage.ui.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.border.AbstractBorder;

public class ButtonPanelBorder extends AbstractBorder {
	
	private Color shadow;
	
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = 0;
        insets.top = 10;
        insets.right = 10;
        insets.bottom = 10;
        return insets;
	}
	public Insets getBorderInsets(Component c) {
		return new Insets(0,10,10,10);
	}
	public Rectangle getInteriorRectangle(Component c, int x, int y, int width,
			int height) {
		return super.getInteriorRectangle(c, x, y, width, height);
	}
	
	public boolean isBorderOpaque() {
		return false;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		int w = width;
		int h = height;
		g.translate(x, y);
		
		g.setColor(getShadowColor(c));
		g.drawLine(0, 0, width, 0);
		g.translate(-x, -y);
	}
	
	public Color getShadowColor(Component c)   {
        return shadow != null? shadow : c.getBackground().darker();
    }
}
