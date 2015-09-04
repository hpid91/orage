package orage.ui.simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import orage.control.Controller;
import orage.ui.board.JBoardPanel;
import orage.ui.peer.JPeerPanel;


public class JSimulPanel extends JPanel  implements DocumentListener,
														ActionListener{

	private ActionListener listener;
	
	private JPeerPanel peerPanel;
	private JSuperviserPanel supPanel;
	private JBoardPanel boardPanel;
	private Document documentLog;
	private JTextArea txtaLogs ;
	private JScrollPane scrollPane;
	
	private final static String ERASE_LOG = "ERASE_LOG";
	
	public JSimulPanel(ActionListener l, 
					   JPeerPanel peerP,
					   JSuperviserPanel supP,
					   JBoardPanel boardP,
					   Document doc) {
		super();
		listener = l;
		peerPanel = peerP;
		supPanel = supP;
		boardPanel = boardP;
		documentLog = doc;
		init();
	}
	
	private void init() {
				
		JToolBar toolbar = new JToolBar();
		JButton btErase = new JButton(new ImageIcon("icons/eraser.png"));
		btErase.setPreferredSize(new Dimension(17,20));
		btErase.addActionListener(this);
		btErase.setActionCommand(ERASE_LOG);
		
		JButton btSave = new JButton(new ImageIcon("icons/save.PNG"));
		btSave.setPreferredSize(new Dimension(17,20));
		btSave.addActionListener(listener);
		btSave.setActionCommand(Controller.AC_SAVE_LOG);
		
		toolbar.add(btErase);
		toolbar.add(btSave);
		toolbar.setFloatable(false);
		toolbar.setRollover(true);
		
		txtaLogs = new JTextArea(documentLog, "", 10, 100);
		txtaLogs.setBackground(new Color(248,248,228));
		txtaLogs.setEditable(false);
		txtaLogs.setAutoscrolls(true);
		documentLog.addDocumentListener(this);
		
		scrollPane = 
		    new JScrollPane(txtaLogs,
		                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);
		
		
		JPanel panelLog = new JPanel();
		panelLog.setLayout(new BorderLayout());
		panelLog.add(toolbar, BorderLayout.PAGE_START);
		panelLog.add(scrollPane, BorderLayout.CENTER);
        panelLog.setPreferredSize(new Dimension(100,100));
        
        JSplitPane splitUp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
							peerPanel, supPanel);
        splitUp.setOneTouchExpandable(true);
        splitUp.setDividerLocation(350);

		JSplitPane splitGlobal = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
												splitUp, panelLog);
		splitGlobal.setOneTouchExpandable(true);
		splitGlobal.setDividerLocation(450);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Peers", splitGlobal);
		tabPane.addTab("Board", boardPanel);
		tabPane.setPreferredSize(new Dimension(600,600));
		
		setBorder(BorderFactory.createEmptyBorder());
		add(tabPane);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		boardPanel.repaint();
		
	}
	
	public void changedUpdate(DocumentEvent e) {
		

	}
	public void insertUpdate(DocumentEvent e) {
		
	}
	public void removeUpdate(DocumentEvent e) {
		

	}
	
	public int getSelectedPeer() {
		return peerPanel.getSelectedPeer();
	}
	
	public int getSelectedSup() {
		return supPanel.getSelectedSup();
	}
	
	public int getNbStep() {
		return peerPanel.getNbStep();
	}
		
	public void setBoardDefined() {
		boardPanel.setBoardDefined();		
	}
	
	public void setPeerPanelEnabled(boolean b) {
		peerPanel.setButtonEnabled(b);
	}
	
	public boolean isWindow() {
		return peerPanel.isWindow();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ERASE_LOG)) txtaLogs.setText("");

	}
}
