/*
 * Created on 22 juil. 2005
 *
 */
package orage.ui.diagnose;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import orage.model.superviser.system.Condition;
import orage.model.superviser.system.OccurenceNet;

public class JOccurenceNetPanel extends JPanel{

    private OccurenceNet net;
    
    private List<JConditionComponent> conditions = new ArrayList<JConditionComponent>();
    private List<JButton> events = new ArrayList<JButton>();
    
    public JOccurenceNetPanel (OccurenceNet net) {
        this.net = net;
        //this.net.order();
        init();
    }
    
    private void init() {
        /*List<Condition> cond = net.getInitialConditions();
        int size = cond.size();
        for (int i = 0; i < size; i ++) {
            
        }*/
        GridLayout layout = new GridLayout(4, 4);
        this.setLayout(layout);
        
        add(new JConditionComponent(new Condition ("dead", "1"), 1, 1));
        setPreferredSize(new Dimension(4 * 30, 4 * 30));
        setVisible(true);
    }
    
    public void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x,
            int y, float stroke) {
        double aDir = Math.atan2(xCenter - x, yCenter - y);
        g2d.drawLine(x, y, xCenter, yCenter);
        g2d.setStroke(new BasicStroke(0.5f)); // make the arrow head solid even
                                              // if
                                              // dash pattern has been specified
        Polygon tmpPoly = new Polygon();
        int i1 = 12 + (int) (stroke);
        int i2 = 6 + (int) stroke; // make the arrow head the same size
        // regardless of the length length
        tmpPoly.addPoint(x, y); // arrow tip
        tmpPoly.addPoint(x + xCor(i1, aDir + .5), y + yCor(i1, aDir + .5));
        tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
        tmpPoly.addPoint(x + xCor(i1, aDir - .5), y + yCor(i1, aDir - .5));
        tmpPoly.addPoint(x, y); // arrow tip
        g2d.drawPolygon(tmpPoly);
        g2d.fillPolygon(tmpPoly); // remove this line to leave arrow head
        // unpainted
    }
    
    private static int yCor(int len, double dir) {
        return (int) (len * Math.cos(dir));
    }

    private static int xCor(int len, double dir) {
        return (int) (len * Math.sin(dir));
    }
}
