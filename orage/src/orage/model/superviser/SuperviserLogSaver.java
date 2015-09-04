
package orage.model.superviser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import orage.model.Action;
import orage.model.peer.PeerAction;


public class SuperviserLogSaver {
		
	private ArrayList actionList;
	private ArrayList messages;
	private Superviser superviser;
	
	private boolean kill = true;
	private boolean move = true;
	private boolean query = true;
	private boolean talk = true;
	private boolean avg = true;
	private boolean getquerylist = true;
	
	
	private boolean addpeer = true;
	private boolean addkpeer = true;
	private boolean rempeer = true;
	private boolean remkpeer = true;
	private boolean diagnose = true;
	
	// File variables
	private String filename;
	private FileWriter writer;
	private FileChannel channel;
	private FileLock lock;
	
	// Listeners
	private ArrayList actionLoggerListeners = new ArrayList();
	
	public SuperviserLogSaver(Superviser superviser, String fn) {
		this.superviser = superviser;
		this.filename = fn;
		actionList = new ArrayList();
		messages = new ArrayList();
		start();
	}
	
	public void addAction(Action action) {
		
		if ( (action.getKind().equals(PeerAction.KILL_KIND) && kill) ||
			 (action.getKind().equals(PeerAction.MOVE_KIND) && move) ||
			 (action.getKind().equals(PeerAction.QUERY_KIND) && query) ||
			 (action.getKind().equals(PeerAction.TALK_KIND) && talk) ||
			 (action.getKind().equals(PeerAction.AVG_KIND) && avg) ||
			 (action.getKind().equals(PeerAction.GETQUERYLIST_KIND) && getquerylist) ||
			 (action.getKind().equals(SuperviserAction.ADDPEER_KIND) && addpeer) ||
			 (action.getKind().equals(SuperviserAction.ADDKPEER_KIND) && addkpeer) ||
			 (action.getKind().equals(SuperviserAction.REMPEER_KIND) && rempeer) ||
			 (action.getKind().equals(SuperviserAction.REMKPEER_KIND) && remkpeer) ||
			 (action.getKind().equals(SuperviserAction.DIAGNOSE_KIND) && diagnose)) {
			
			actionList.add(action);
        
			
			try {
				
				writer.write(action.toXML());
				writer.flush();
			} catch (IOException e) {

				e.printStackTrace();
			}	
		}
		
		fireNewMessage(action.toString());
	}
	
	public void addMessage (String msg) {
		messages.add(msg);
		fireNewMessage(msg);
	}
	
	public Action getAction(int index) {
		return (PeerAction)actionList.get(index);
	}
	
	public int size() {
		return actionList.size();
	}
	
	
	public ArrayList getActionList() {
		return actionList;
	}
	
	public ArrayList getMessages() {
		return messages;
	}
	
	public void addActionList(ArrayList list) {
		int size = list.size();
		
		for (int i = 0; i < size; i++) {
			addAction((PeerAction)list.get(i));
		}
	}
	
	public void addMessages(ArrayList list) {
		int size = list.size();
		
		for (int i = 0; i < size; i++) {
			addMessage((String)list.get(i));
		}
		
	}
	
	public void addActionLoggerListener (ActionLoggerListener all) {
		actionLoggerListeners.add(all);
	}
	
	public void fireNewMessage(String msg) {
		int size = actionLoggerListeners.size();
		ActionLoggerListener all;
		for (int i = 0; i < size; i++ ){
			all = (ActionLoggerListener)actionLoggerListeners.get(i);
			all.newMessage(new ActionEvent(msg));
		}
	}
	
	public String getFileName() {
		return filename;
	}
	
	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	private void start() {
		//		 Open the writer
		try {
			File f = new File("logs/");
				 
			if (!f.exists()) f.mkdir();
			f = new File("logs/" + filename + ".xml");
			f.createNewFile();
			
			
			writer = new FileWriter(f);
			// Set lock
			//channel = new RandomAccessFile(f, "rw").getChannel();
			//lock = channel.lock();

			writer.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n");
			writer.write("<actions>\r\n");
			
			
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			writer.write("</actions>");
			
			//lock.release();
			//channel.close();
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public Superviser getSuperviser() {
		return superviser;
	}
	
	public void setActionsLogged(ArrayList list) {
		kill = Boolean.valueOf((String)list.get(0)).booleanValue();
		move = Boolean.valueOf((String)list.get(1)).booleanValue();
		query = Boolean.valueOf((String)list.get(2)).booleanValue();
		talk = Boolean.valueOf((String)list.get(3)).booleanValue();
		avg = Boolean.valueOf((String)list.get(4)).booleanValue();
		getquerylist = Boolean.valueOf((String)list.get(5)).booleanValue();
	}
	
}
