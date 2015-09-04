
package orage.ui.simulation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class JConfigLogPanel extends JPanel {
	
	// Graphical element
	private JCheckBox ckActionKill;
	private JCheckBox ckActionMove;
	private JCheckBox ckActionQuery;
	private JCheckBox ckActionTalk;
	private JCheckBox ckActionAvg;
	private JCheckBox ckActionGetQueryList;
	
	
	public JConfigLogPanel(ArrayList actionsLogged) {
		
		ckActionKill = new JCheckBox("Kill", Boolean.valueOf((String)actionsLogged.get(0)).booleanValue());
		ckActionMove = new JCheckBox("Move", Boolean.valueOf((String)actionsLogged.get(1)).booleanValue());
		ckActionQuery = new JCheckBox("Query", Boolean.valueOf((String)actionsLogged.get(2)).booleanValue());
		ckActionTalk = new JCheckBox("Talk", Boolean.valueOf((String)actionsLogged.get(3)).booleanValue());
		ckActionAvg = new JCheckBox("Avg", Boolean.valueOf((String)actionsLogged.get(4)).booleanValue());
		ckActionGetQueryList = new JCheckBox("Get Query List", Boolean.valueOf((String)actionsLogged.get(5)).booleanValue());
		
		
		setLayout(new GridLayout(3,2));
		setBorder(BorderFactory.createTitledBorder("Actions logged"));
		add(ckActionKill);
		add(ckActionMove);
		add(ckActionQuery);
		add(ckActionTalk);
		add(ckActionAvg);
		add(ckActionGetQueryList);
		setPreferredSize(new Dimension(250,100));
	}

	public ArrayList getActionsLogged() {
		ArrayList list = new ArrayList();
		list.add(String.valueOf(ckActionKill.isSelected()));
		list.add(String.valueOf(ckActionMove.isSelected()));
		list.add(String.valueOf(ckActionQuery.isSelected()));
		list.add(String.valueOf(ckActionTalk.isSelected()));
		list.add(String.valueOf(ckActionAvg.isSelected()));
		list.add(String.valueOf(ckActionGetQueryList.isSelected()));
		return list;
	}
	
}
