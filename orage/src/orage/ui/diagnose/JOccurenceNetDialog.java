/*
 * Created on 22 juil. 2005
 *
 */
package orage.ui.diagnose;

import javax.swing.JFrame;

import orage.model.superviser.system.OccurenceNet;

public class JOccurenceNetDialog extends JFrame{

    JOccurenceNetPanel panel;
    
    public JOccurenceNetDialog( OccurenceNet net) {
        //super(mother);
        panel = new JOccurenceNetPanel(net);

        getContentPane().add(panel);

        setTitle("Diagnose");
        setResizable(false);
        //setModal(true);

        //setLocationRelativeTo(mother);

        pack();
        setVisible(true);
        
    }
   
}
