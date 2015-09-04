
package orage.ui.peer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import orage.control.Controller;
import orage.model.PeerCollection;
import orage.model.superviser.SuperviserCollection;
import orage.ui.common.JBannerPanel;
import orage.ui.common.JPanelButton;

public class JPeerDialog extends JDialog implements ActionListener {

	// Controller
	private ActionListener listener;

	// Graphical elements
	private JTextField txtPeerName;
	private JTextField txtSelectedColor;

	private JCheckBox ckLiar;
	private JSpinner spinX;
	private JSpinner spinY;
	private Color color;
	private JList listSelectedPeers;
	private JList listPeers;

	// Model elements
	private String id = ""; // Identifier of the peer (use in modification)
	private int mode = 0; // Mode : creation or modification
	private int maxX = 10; // The maximum for the x coordinate
	private int maxY = 10; // The maximum for the y coordinate
	private PeerCollection peerList;
	private PeerCollection peerSelectedList;
	private SuperviserCollection supList;
	
	// Constants
	private final static String BT_ADD_LIST = "BT_ADD_LIST";

	private final static String BT_REM_LIST = "BT_REM_LIST";

	public final static int MODE_CREATE = 0;

	public final static int MODE_MODIF = 1;

	public JPeerDialog(ActionListener l, PeerCollection list, SuperviserCollection sList,
			int mode, int maxX, int maxY) {
		listener = l;
		this.mode = mode;
		this.maxX = maxX;
		this.maxY = maxY;
		peerList = new PeerCollection(list);
		supList = new SuperviserCollection(sList);
		peerSelectedList = new PeerCollection();
		init();
	}

	private void init() {

		// BANNER
		JBannerPanel banner = new JBannerPanel("Edition of a peer",
												"Set the peer data");

		// NAME AND LYER
		JLabel lblPeerName = new JLabel("Peer name :");
		txtPeerName = new JTextField(10);
		ckLiar = new JCheckBox("Liar");

		JPanel panelName = new JPanel();
		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));
		panelName.add(lblPeerName);
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));
		panelName.add(txtPeerName);
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));
		panelName.add(ckLiar);

		// COLOR
		JPanel panelColor = new JPanel();
		JLabel lblColor = new JLabel("Color :");
		txtSelectedColor = new JTextField(5);
		txtSelectedColor.setBackground(Color.YELLOW);
		txtSelectedColor.setEditable(false);
		txtSelectedColor.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.RAISED));
		JButton btSelectColor = new JButton("Select");
		btSelectColor.addActionListener(listener);
		btSelectColor.setActionCommand(Controller.AC_OPEN_SELECT_COLOR);
		panelColor.setLayout(new BoxLayout(panelColor, BoxLayout.X_AXIS));
		panelColor.add(Box.createRigidArea(new Dimension(10, 0)));
		panelColor.add(lblColor);
		panelColor.add(Box.createRigidArea(new Dimension(30, 0)));
		panelColor.add(txtSelectedColor);
		panelColor.add(Box.createHorizontalStrut(300));
		panelColor.add(btSelectColor);
		panelColor.add(Box.createRigidArea(new Dimension(10, 0)));
		panelColor.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel panelData = new JPanel();
		panelData.setBorder(BorderFactory.createTitledBorder("General data"));
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
		panelData.add(panelName);
		panelData.add(Box.createVerticalStrut(20));
		panelData.add(panelColor);
		panelData.setPreferredSize(new Dimension(500, 65));

		// INIT X and Y
		JPanel panelSpinners = new JPanel();
		JLabel lblX = new JLabel("X :");
		SpinnerNumberModel snm = new SpinnerNumberModel(0, 0, maxX, 1); 
		spinX = new JSpinner(snm);
		JLabel lblY = new JLabel("Y :");
		SpinnerNumberModel snmy = new SpinnerNumberModel(0, 0, maxY, 1);
		spinY = new JSpinner(snmy);

		panelSpinners.setBorder(BorderFactory
				.createTitledBorder("Initial position"));
		panelSpinners.setLayout(new BoxLayout(panelSpinners, BoxLayout.X_AXIS));
		panelSpinners.add(Box.createRigidArea(new Dimension(10, 0)));
		panelSpinners.add(lblX);
		panelSpinners.add(spinX);
		panelSpinners.add(Box.createRigidArea(new Dimension(100, 0)));
		panelSpinners.add(lblY);
		panelSpinners.add(spinY);
		panelSpinners.add(Box.createRigidArea(new Dimension(10, 0)));

		// KNOWN PEERS
		JPanel panelList = new JPanel();

		listPeers = new JList();
		listPeers.setListData(peerList.getListNames());
		JScrollPane scrollListPeers = new JScrollPane(listPeers);
		scrollListPeers.setPreferredSize(new Dimension(200, 180));
		listSelectedPeers = new JList(peerSelectedList.getListNames());
		JScrollPane scrollListSelectedPeers = new JScrollPane(listSelectedPeers);
		scrollListSelectedPeers.setPreferredSize(new Dimension(200, 180));

		JPanel panelListButton = new JPanel();
		panelListButton.setLayout(new BoxLayout(panelListButton,
				BoxLayout.Y_AXIS));
		JButton btAddSelect = new JButton(">");
		btAddSelect.addActionListener(this);
		btAddSelect.setActionCommand(BT_ADD_LIST);
		JButton btRemoveSelect = new JButton("<");
		btRemoveSelect.addActionListener(this);
		btRemoveSelect.setActionCommand(BT_REM_LIST);
		panelListButton.add(btAddSelect);
		panelListButton.add(btRemoveSelect);

		panelList.setBorder(BorderFactory.createTitledBorder("Known Peers"));
		panelList.add(scrollListPeers);
		panelList.add(panelListButton);
		panelList.add(scrollListSelectedPeers);

		// BUTTONS
		JPanelButton panelButton = new JPanelButton();
		JButton btOk = new JButton("Ok");

		switch (mode) {
		case MODE_CREATE:
			btOk.setActionCommand(Controller.AC_CREATE_PEER);
			break;
		case MODE_MODIF:
			btOk.setActionCommand(Controller.AC_MODIF_PEER);
			break;
		}

		btOk.addActionListener(listener);
		JButton btCancel = new JButton("Cancel");
		btCancel.setActionCommand(Controller.AC_CANCEL_CREATE_PEER);
		btCancel.addActionListener(listener);
		panelButton.add(btOk);
		panelButton.add(btCancel);

		// GLOBAL
		JPanel panelGlobal = new JPanel();
		panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
		panelGlobal.add(banner);
		panelGlobal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGlobal.add(panelData);
		panelGlobal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGlobal.add(panelSpinners);
		panelGlobal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGlobal.add(panelList);
		panelGlobal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGlobal.add(panelButton);

		setTitle("Peer");
		setResizable(false);
		setModal(true);
		setSize(500, 530);
		getContentPane().add(panelGlobal);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(BT_ADD_LIST)) {
			int i = listPeers.getSelectedIndex();
			if (i != -1) {
				peerSelectedList.add(peerList.get(i));
				peerList.remove(i);
				listPeers.setListData(peerList.getListNames());
				listSelectedPeers.setListData(peerSelectedList.getListNames());

				if (peerList.size() > 0)
					listPeers.setSelectedIndex(0);
				validate();
			}

		} else if (e.getActionCommand().equals(BT_REM_LIST)) {
			int i = listSelectedPeers.getSelectedIndex();
			if (i != -1) {
				peerList.add(peerSelectedList.get(i));
				peerSelectedList.remove(i);
				listPeers.setListData(peerList.getListNames());
				listSelectedPeers.setListData(peerSelectedList.getListNames());
				if (peerSelectedList.size() > 0)
					listSelectedPeers.setSelectedIndex(0);
				validate();
			}
		}

	}

	public void setColor(Color c) {
		txtSelectedColor.setBackground(c);
	}

	public Color getColor() {
		return txtSelectedColor.getBackground();
	}

	public void setName(String s) {
		txtPeerName.setText(s);
	}

	public String getName() {
		return txtPeerName.getText();
	}

	public int getInitX() {
		return ((Integer) spinX.getValue()).intValue();
	}

	public int getInitY() {
		return ((Integer) spinY.getValue()).intValue();
	}

	public void setInitX(int i) {
		spinX.setValue(new Integer(i));
	}

	public void setInitY(int i) {
		spinY.setValue(new Integer(i));
	}

	public boolean isLyer() {
		return ckLiar.isSelected();
	}

	public void setLyer(boolean b) {
		ckLiar.setSelected(b);
	}

	public void setSelectedList(PeerCollection list) {
		peerSelectedList = null;
		peerSelectedList = list;

		int size = peerSelectedList.size();
		// Update general list
		for (int i = 0; i < size; i++) {
			peerList.remove(peerSelectedList.get(i));
		}
	
		peerList.remove(peerList.getFromId(id)); // Remove the current peer from
											   // the general list

		listPeers.setListData(peerList.getListNames());
		listSelectedPeers.setListData(peerSelectedList.getListNames());
	}

	public PeerCollection getPeerSelectedList() {
		return peerSelectedList;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}