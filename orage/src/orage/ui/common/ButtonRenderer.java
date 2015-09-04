
package orage.ui.common;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
	
	//private ActionListener listener;
	private String action;
	public ButtonRenderer(String action) {
		super();
		this.action = action;
		setText("Do");
		setEnabled(true);
	}


	//fonction appelée à l'affichage
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		return this;
	}
}
