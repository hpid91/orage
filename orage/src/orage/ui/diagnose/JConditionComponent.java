/*
 * Created on 22 juil. 2005
 *
 */
package orage.ui.diagnose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import orage.model.superviser.system.Condition;

public class JConditionComponent extends JComponent implements MouseListener {
    
    private Condition condition;
    private int posX = 0;
    private int posY = 0;
    private int rayon = 25;
    
    public JConditionComponent(Condition c) {
        this.condition = c;
        addMouseListener(this);
    }
    
    public JConditionComponent(Condition c, int x, int y) {
        this.condition = c;
        posX = x + 2;
        posY = y + 2;
        addMouseListener(this);
    }
    
    public void mouseEntered(MouseEvent e) {
        
        String s = condition.toString();
        setToolTipText(s);
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawOval(posX ,
                posY,
               rayon,
               rayon);
        g.drawString(condition.getLabel(), posX + rayon/2, posY+ rayon/2);
        
    }

    protected void paintBorder(Graphics g) {
        
        super.paintBorder(g);
        
            g.setColor(Color.BLACK);
            g.drawOval(posX ,
                    posY,
                   rayon,
                   rayon);
        
        
    }
    

}
