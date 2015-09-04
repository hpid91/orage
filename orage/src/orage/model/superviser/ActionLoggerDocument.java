
package orage.model.superviser;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ActionLoggerDocument implements ActionLoggerListener {
	
	private Document document;
	
	private ArrayList actionLoggers;
	
	
	public ActionLoggerDocument() {
		document = new PlainDocument();
		actionLoggers = new ArrayList();
	}
	
	public ActionLoggerDocument(ArrayList list) {
		document = new PlainDocument();
		int size = list.size();
		actionLoggers = new ArrayList();
		for (int i = 0; i < size; i++) {
			addActionLogger((SuperviserLogSaver)list.get(i));
		}
	}
	
	public void addActionLogger (SuperviserLogSaver al) {
		actionLoggers.add(al);
		al.addActionLoggerListener(this);
	}
	
	public void addMessage (String s) {
		try {
			
			document.insertString(0, s + "\r\n", null);
		} catch (BadLocationException e1) {
			
			e1.printStackTrace();
		}
	}
	
	public void newMessage(ActionEvent e) {
		try {
			
			document.insertString(0, e.getMessage() + "\r\n", null);
		} catch (BadLocationException e1) {
			
			e1.printStackTrace();
		}
	}
	
	public Document getDocument() {
		return document;
	}
	
	public ArrayList getActionLoggers() {
		return actionLoggers;
	}
}
