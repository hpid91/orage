
package orage.ui.board;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpinnerNumberModel;

import orage.control.Controller;
import orage.model.Board;
import orage.model.PeerCollection;


public class JBoardPanel extends JPanel {

	// Controller
	private ActionListener listener;
	
	// Model elements
	private Board board;
	private PeerCollection peers;
	
	// View elements
	private BoardPanel 		boardPanel;
	private JScrollPane 	scrollPane;
	private JSpinner 		spinX;
	private JSpinner 		spinY;
	private JSpinner 		spinMax;
	private JSpinner 		spinMin;
	private JButton 		btSet;
	private JPanel 			panelBoardData;
	
	public JBoardPanel(ActionListener l, Board b, PeerCollection peers) {
		super();
		listener = l;
		board = b;
		this.peers = peers;
		init();
	}
	
	
	private void init() {
		boardPanel = new BoardPanel(board, peers);
		scrollPane = new JScrollPane(boardPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPane.setPreferredSize(new Dimension(350,200));
		scrollPane.setLayout(new ScrollPaneLayout());
		
		// INIT X and Y
		JPanel panelXY = new JPanel();
		JLabel lblX = new JLabel("X :");
		SpinnerNumberModel snm = new SpinnerNumberModel(board.getXsize(),0,1500,1); // on d�finit les valeurs: d�faut, min, max, pas
		spinX = new JSpinner(snm);
		JLabel lblY = new JLabel("Y :");
		SpinnerNumberModel snmy = new SpinnerNumberModel(board.getYsize(),0,1500,1); // on d�finit les valeurs: d�faut, min, max, pas
		spinY = new JSpinner(snmy);
				
		// INIT MAX MIN
		JPanel panelMaxMin = new JPanel();
		JLabel lblMax = new JLabel("Max Value :");
		SpinnerNumberModel snmMax = new SpinnerNumberModel(4,0,1500,1); // on d�finit les valeurs: d�faut, min, max, pas
		spinMax = new JSpinner(snmMax);
		JLabel lblMin = new JLabel("Min Value :");
		SpinnerNumberModel snmMin = new SpinnerNumberModel(0,0,1500,1); // on d�finit les valeurs: d�faut, min, max, pas
		spinMin = new JSpinner(snmMin);
		
		// SET BUTTON
		btSet = new JButton("Set");
		btSet.setActionCommand(Controller.AC_SET_BOARD);
		btSet.addActionListener(listener);
		
		panelBoardData = new JPanel();
		panelBoardData.setBorder(BorderFactory.createEmptyBorder());
		panelBoardData.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        
        c.gridx = 0;
        c.gridy = 0;
        panelBoardData.add(lblX, c);

        c.gridx = 1;
        c.gridy = 0;
        panelBoardData.add(spinX, c);
        
        c.gridx = 2;
        c.gridy = 0;
        panelBoardData.add(lblY, c);

        c.gridx = 3;
        c.gridy = 0;
        panelBoardData.add(spinY, c);
        
        c.gridx = 0;
        c.gridy = 1;
        panelBoardData.add(lblMin, c);

        c.gridx = 1;
        c.gridy = 1;
        panelBoardData.add(spinMin, c);
        
        c.gridx = 2;
        c.gridy = 1;
        panelBoardData.add(lblMax, c);

        c.gridx = 3;
        c.gridy = 1;
        panelBoardData.add(spinMax, c);
               
        c.ipady = 0;       
        c.weighty = 1.0;   
        c.gridx = 3;       
        c.gridwidth = 1;
        c.gridy = 2;       
        panelBoardData.add(btSet, c);
        panelBoardData.setPreferredSize(new Dimension(250, 70));
        
		setBorder(BorderFactory.createTitledBorder("Board"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(panelBoardData);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(scrollPane);
		setPreferredSize(new Dimension(500,400));
		
	}
	
	public int getSpinX() {
		return ((Integer)spinX.getValue()).intValue();
	}
	
	public int getSpinY() {
		return ((Integer)spinY.getValue()).intValue();
	}
	
	public int getSpinMax() {
		return ((Integer)spinMax.getValue()).intValue();
	}
	
	public int getSpinMin() {
		return ((Integer)spinMin.getValue()).intValue();
	}
	
	public void setBoardDefined() {
		btSet.setEnabled(false);
		spinX.setEnabled(false);
		spinY.setEnabled(false);
		spinMax.setEnabled(false);
		spinMin.setEnabled(false);
	}
	
	public void setPeers(PeerCollection peers) {
		this.peers = peers;
		boardPanel = null;
		boardPanel = new BoardPanel(board, peers);
		
		scrollPane.removeAll();
		scrollPane = null;
		scrollPane = new JScrollPane(boardPanel);
		
		removeAll();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(panelBoardData);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scrollPane);
		
		validate();
	}
	
	public void setBoard(Board b) {
		board = b;
		boardPanel = null;
		boardPanel = new BoardPanel(board, peers);
		
		scrollPane.removeAll();
		scrollPane = null;
		scrollPane = new JScrollPane(boardPanel);
		
		removeAll();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(panelBoardData);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scrollPane);
		
		validate();
		
	}
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
}
