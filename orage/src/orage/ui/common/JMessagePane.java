
package orage.ui.common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import orage.ui.main.OrageWindow;

public class JMessagePane {

	private String message 	= "";
	private String title 	= "";
	private int type 		= 0;
	private OrageWindow view;
	private int option		= 0;	
	private int selectValue = 0 ;
	
	public final static int YES_ANSWER = 0;
	public final static int NO_ANSWER = 1;
	public final static int CANCEL_ANSWER = 2;
	
	public final static int OK_OPTION = 0;
	public final static int YES_NO_CANCEL_OPTION = 1;
	public final static int OK_CANCEL_OPTION = 2;
	
	private final static String INFO_TITLE = "Information";
	private final static String WARNING_TITLE = "Warning";
	private final static String ERROR_TITLE = "Error";
	private final static String QUESTION_TITLE = "?";
	
	public final static int WINDOW_TYPE_INFO = 0;
	public final static int WINDOW_TYPE_WARNING = 1;
	public final static int WINDOW_TYPE_ERROR = 2;
	public final static int WINDOW_TYPE_QUESTION = 3;
	
	public JMessagePane (OrageWindow v, String s, int t, int o) {
		message = s;
		view = v;
				
		switch (t) {
			case WINDOW_TYPE_INFO:
				type = JOptionPane.INFORMATION_MESSAGE;
				title = INFO_TITLE;
				
			case WINDOW_TYPE_WARNING:
				type = JOptionPane.WARNING_MESSAGE;
				title = WARNING_TITLE;
				
			case WINDOW_TYPE_ERROR:
				type = JOptionPane.ERROR_MESSAGE;
				title = ERROR_TITLE;
				
			case WINDOW_TYPE_QUESTION: 
				type = JOptionPane.QUESTION_MESSAGE;
				title = QUESTION_TITLE;
				
			default :
				type = JOptionPane.INFORMATION_MESSAGE;
				title = INFO_TITLE;
		}
		
		switch (o) {
			case OK_OPTION : option = JOptionPane.OK_OPTION;
			case OK_CANCEL_OPTION : option = JOptionPane.OK_CANCEL_OPTION;
			case YES_NO_CANCEL_OPTION : option = JOptionPane.YES_NO_CANCEL_OPTION;
		}
		init();
		
	}
	
	private void init() {
		JOptionPane pane = new JOptionPane(message, type, option);
		JDialog dialog = pane.createDialog(view, title);
		dialog.show();
		
		Integer selectedValue = (Integer)pane.getValue();
		if (selectedValue != null) {
			if ((selectedValue.intValue() == JOptionPane.YES_OPTION) ||
				(selectedValue.intValue() == JOptionPane.OK_OPTION) ) {
				selectValue = YES_ANSWER;
			} else if (selectedValue.intValue() == JOptionPane.NO_OPTION){
				selectValue = NO_ANSWER;
			} else if (selectedValue.intValue() == JOptionPane.CANCEL_OPTION){
				selectValue = CANCEL_ANSWER;
			}
		} else selectValue = CANCEL_ANSWER;
	}
	public int getSelectValue() {
		return selectValue;
	}
}
