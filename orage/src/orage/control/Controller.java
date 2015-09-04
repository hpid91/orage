
package orage.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import orage.control.board.CdSetBoard;
import orage.control.filemenu.CdLoadSimulPanel;
import orage.control.filemenu.CdOpenSimulPanel;
import orage.control.filemenu.CdSaveSimulation;
import orage.control.peer.CdCloseColorChooser;
import orage.control.peer.CdCloseCreatePeer;
import orage.control.peer.CdCreatePeer;
import orage.control.peer.CdModifPeer;
import orage.control.peer.CdOpenColorChooser;
import orage.control.peer.CdOpenCreatePeer;
import orage.control.peer.CdOpenModifPeer;
import orage.control.peer.CdRemovePeer;
import orage.control.peer.CdSelectColor;
import orage.control.peer.CdViewAPeer;
import orage.control.peer.CdViewAllPeer;
import orage.control.peer.action.CdActionAvg;
import orage.control.peer.action.CdActionKill;
import orage.control.peer.action.CdActionMove;
import orage.control.peer.action.CdActionQuery;
import orage.control.peer.action.CdActionStep;
import orage.control.peer.action.CdActionTalk;
import orage.control.simulmenu.CdBackToSimulPanel;
import orage.control.simulmenu.CdCloseCreateSuperviser;
import orage.control.simulmenu.CdCreateSuperviser;
import orage.control.simulmenu.CdModifSuperviser;
import orage.control.simulmenu.CdOpenCreateSuperviser;
import orage.control.simulmenu.CdOpenModifSuperviser;
import orage.control.simulmenu.CdOpenPreferencesPanel;
import orage.control.simulmenu.CdRemoveSuperviser;
import orage.control.simulmenu.CdSetPreferences;
import orage.control.simulmenu.action.CdAddKPeerAction;
import orage.control.simulmenu.action.CdAddPeerAction;
import orage.control.simulmenu.action.CdDiagnoseAction;
import orage.control.simulmenu.action.CdRemoveKPeerAction;
import orage.control.simulmenu.action.CdRemovePeerAction;
import orage.model.Board;
import orage.model.Model;
import orage.model.peer.Peer;
import orage.model.peer.PeerAction;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserAction;
import orage.model.superviser.SuperviserCollection;
import orage.model.superviser.system.AlarmPattern;
import orage.ui.common.JMessagePane;
import orage.ui.main.OrageWindow;

public class Controller implements ActionListener { 
	
	// MENU ACTION COMMANDS
	public final static String AC_MENU_NEWSIMUL		= "AC_MENU_NEWSIMUL";
	public final static String AC_MENU_OPENSIMUL	= "AC_MENU_OPENSIMUL";
	public final static String AC_MENU_SAVESIMUL	= "AC_MENU_SAVESIMUL";
	public final static String AC_MENU_EXIT			= "AC_MENU_EXIT";
	public final static String AC_MENU_SPLASH		= "AC_MENU_SPLASH";
	public final static String AC_MENU_PREFERENCES	= "AC_MENU_PREFERENCES";
	
	
	// PEER CREATION ACTION COMMANDS
	public final static String AC_OPEN_SELECT_COLOR = "AC_OPEN_SELECT_COLOR";
	public final static String AC_SELECT_COLOR = "AC_SELECT_COLOR";
	public final static String AC_CANCEL_SELECT_COLOR = "AC_CANCEL_SELECT_COLOR";
	public final static String AC_OPEN_CREATE_PEER = "AC_OPEN_CREATE_PEER";
	public final static String AC_OPEN_MODIF_PEER = "AC_OPEN_MODIF_PEER";
	public final static String AC_REMOVE_PEER = "AC_REMOVE_PEER";
	public final static String AC_CREATE_PEER = "AC_CREATE_PEER";
	public final static String AC_MODIF_PEER = "AC_MODIF_PEER";
	public final static String AC_CANCEL_CREATE_PEER = "AC_CANCEL_CREATE_PEER";
	
	// TABLE PEERS COMMANDS
	public final static String AC_TABLE_PEERS = "AC_TABLE_PEERS";
	public final static String AC_VIEW_PEER = "AC_VIEW_PEER";
	public final static String AC_VIEW_ALLPEER = "AC_VIEW_ALLPEER";
	public final static String AC_STEP = "AC_STEP";
	
	// PREFERENCES COMMANDS 
	public final static String AC_BACK_TO_SIMUL = "AC_BACK_TO_SIMUL";
	public final static String AC_SET_PREFERENCES = "AC_SET_PREFERENCES";
	
	// SUPERVISER COMMANDS
	public final static String AC_OPEN_CREATE_SUP = "AC_OPEN_CREATE_SUP";
	public final static String AC_OPEN_MODIF_SUP = "AC_OPEN_MODIF_SUP";
	public final static String AC_REMOVE_SUP = "AC_REMOVE_SUP";
	public final static String AC_CREATE_SUP = "AC_CREATE_SUP";
	public final static String AC_MODIF_SUP = "AC_MODIF_SUP";
	public final static String AC_CANCEL_CREATE_SUP = "AC_CANCEL_CREATE_SUP";
	public final static String AC_TABLE_SUP = "AC_TABLE_SUP";
		
	// BOARD COMMANDS
	public final static String AC_SET_BOARD = "AC_SET_BOARD";
	
	// LOG COMMAND
	public final static String AC_SAVE_LOG = "AC_SAVE_LOG";
	
	// MODEL
	private Model model;
	// VIEW
	private OrageWindow view;
	
	public Controller() {
		view = new OrageWindow(this);
	}
	
	public void initModel() {
		
		if (model != null) model.empty();
		model = null;
		
		Board board = new Board(20, 20, 4,0);
        AlarmPattern ap = new AlarmPattern();
        
		Superviser sup = new Superviser("sup1", board);
		
		SuperviserCollection collec = new SuperviserCollection();
		collec.add(sup);
		Peer alpha = new Peer("alpha", 1, 1, board, false, false, Color.YELLOW, collec);
		Peer beta = new Peer("beta", 10, 10, board, true, false, Color.BLUE, collec);
		Peer epsilon = new Peer("epsilon", 17, 4, board, false, false, Color.CYAN, collec);
		Peer bishop = new Peer("bishop", 12, 19, board, true, false, Color.RED, collec);

		
		// A -> B -> E -> A ...
		alpha.addAKnownPeer(beta);
		beta.addAKnownPeer(epsilon);
		epsilon.addAKnownPeer(alpha);
		alpha.addAKnownPeer(bishop);
		beta.addAKnownPeer(bishop);
		epsilon.addAKnownPeer(bishop);
		bishop.addAKnownPeer(beta);
		bishop.addAKnownPeer(epsilon);
		bishop.addAKnownPeer(alpha);
		
		model = new Model(board);
		
		model.addSuperviserPeer(sup, alpha);
		model.addSuperviserPeer(sup, beta);
		model.addSuperviserPeer(sup, epsilon);
		model.addSuperviserPeer(sup, bishop);
		model.addSuperviser(sup);
	}
	
	public void actionPerformed(ActionEvent e) {
		Command cd;
		
		if (e.getActionCommand().equals(AC_OPEN_SELECT_COLOR)) {
			cd = new CdOpenColorChooser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_SELECT_COLOR)) {
			cd = new CdSelectColor(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_CANCEL_SELECT_COLOR)) {
			cd = new CdCloseColorChooser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_OPEN_CREATE_PEER)) {
			cd = new CdOpenCreatePeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_CREATE_PEER)) {
			cd = new CdCreatePeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_OPEN_MODIF_PEER)) {
			cd = new CdOpenModifPeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MODIF_PEER)) {
			cd = new CdModifPeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_CANCEL_CREATE_PEER)) {
			cd = new CdCloseCreatePeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MENU_NEWSIMUL)) {
			initModel();
			cd = new CdOpenSimulPanel(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MENU_OPENSIMUL)) {
			cd = new CdLoadSimulPanel(model, view);
			cd.execute();
			model = ((CdLoadSimulPanel)cd).getNewModel();
		} else if (e.getActionCommand().equals(AC_MENU_SAVESIMUL)) {
			cd = new CdSaveSimulation(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MENU_EXIT)) {
			JMessagePane mw = new JMessagePane(view,
											 "Exit Orage platform ?",
											 JMessagePane.WINDOW_TYPE_QUESTION,
											 JMessagePane.OK_CANCEL_OPTION);

			if (mw.getSelectValue() == JMessagePane.YES_ANSWER) {
				if (model != null) model.stop();
				System.runFinalization();
				System.gc();
				System.exit(0);
			}
		
		} else if (e.getActionCommand().equals(AC_MENU_SPLASH)) {	
			cd = new CdShowSplash(model,view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_REMOVE_PEER)) {
			cd = new CdRemovePeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().indexOf(AC_TABLE_PEERS) > -1) {
			String s = e.getActionCommand();
			String action = s.substring(s.indexOf(AC_TABLE_PEERS) + 14, s.indexOf("#"));
			String pos = s.substring(s.indexOf("#")+1);
					
			
					
			if (action.equals(PeerAction.QUERY_KIND)) {
				cd = new CdActionQuery(model, view);
				cd.execute();
			} else if (action.equals(PeerAction.TALK_KIND)) {
				cd = new CdActionTalk(model, view);
				cd.execute();
			} else if (action.equals(PeerAction.MOVE_KIND)) {
				cd = new CdActionMove(model, view);
				cd.execute();
			} else if (action.equals(PeerAction.KILL_KIND)) {
				cd = new CdActionKill(model, view);
				cd.execute();
			} else if (action.equals(PeerAction.AVG_KIND)) {
				cd = new CdActionAvg(model, view);
				cd.execute();
			}
		} else if (e.getActionCommand().equals(AC_VIEW_PEER)) {	
			cd = new CdViewAPeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_VIEW_ALLPEER)) {
			cd = new CdViewAllPeer(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_STEP)) {
			cd = new CdActionStep(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_SET_BOARD)) {
			cd = new CdSetBoard(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_SAVE_LOG)) {
			cd = new CdSaveLogs(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MENU_PREFERENCES)) {
			cd = new CdOpenPreferencesPanel(model, view);
			cd.execute();
		}  else if (e.getActionCommand().equals(AC_BACK_TO_SIMUL)) {
			cd = new CdBackToSimulPanel(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_SET_PREFERENCES)) {
			cd = new CdSetPreferences (model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_OPEN_CREATE_SUP)) {
			cd = new CdOpenCreateSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_OPEN_MODIF_SUP)) {
			cd = new CdOpenModifSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_REMOVE_SUP)) {
			cd = new CdRemoveSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_CREATE_SUP)) {
			cd = new CdCreateSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_MODIF_SUP)) {
			cd = new CdModifSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().equals(AC_CANCEL_CREATE_SUP)) {
			cd = new CdCloseCreateSuperviser(model, view);
			cd.execute();
		} else if (e.getActionCommand().indexOf(AC_TABLE_SUP) > -1) {
			String s = e.getActionCommand();
			String action = s.substring(s.indexOf(AC_TABLE_SUP) + 12, s.indexOf("#"));
			String pos = s.substring(s.indexOf("#")+1);
			
			if (action.equals(SuperviserAction.ADDPEER_KIND)) {
				cd = new CdAddPeerAction(model, view);
				cd.execute();
			} else if (action.equals(SuperviserAction.ADDKPEER_KIND)) {
				cd = new CdAddKPeerAction(model, view);
				cd.execute();
			} else if (action.equals(SuperviserAction.REMPEER_KIND)) {
				cd = new CdRemovePeerAction(model, view);
				cd.execute();
			} else if (action.equals(SuperviserAction.REMKPEER_KIND)) {
				cd = new CdRemoveKPeerAction(model, view);
				cd.execute();
			} else if (action.equals(SuperviserAction.DIAGNOSE_KIND)) {
				cd = new CdDiagnoseAction(model, view);
				cd.execute();
			}
			
		}

	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel ( 								 
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		new Controller();
	}
}
