package orage.ui.simulation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import orage.control.Controller;
import orage.model.PeerCollection;
import orage.ui.common.JBannerPanel;
import orage.ui.common.JPanelButton;

public class JSuperviserDialog extends JDialog implements ActionListener {

	// Controller
	private ActionListener listener;

	// Graphical elements
	private JTextField txtSuperviserName;
	private JList listSelectedPeers;
	private JList listPeers;

	// Model elements
	private String id = ""; // Identifier of the peer (use in modification)
	private int mode = 0; // Mode : creation or modification
	private PeerCollection peerList;
	private PeerCollection peerSelectedList;
	
	// Constants
	private final static String BT_ADD_LIST = "BT_ADD_LIST";
	private final static String BT_REM_LIST = "BT_REM_LIST";
	public final static int MODE_CREATE = 0;
	public final static int MODE_MODIF = 1;

	public JSuperviserDialog(ActionListener l, PeerCollection list,
							int mode) {
		listener = l;
		this.mode = mode;
		peerList = new PeerCollection(list);
		peerSelectedList = new PeerCollection();
		init();
	}

	private void init() {

		// BANNER
		JBannerPanel banner = new JBannerPanel("Edition of a superviser",
												"Set the superviser data");

		// NAME AND LYER
		JLabel lblSupName = new JLabel("Name :");
		txtSuperviserName = new JTextField(10);
		
		JPanel panelName = new JPanel();
		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));
		panelName.add(lblSupName);
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));
		panelName.add(txtSuperviserName);
		panelName.add(Box.createRigidArea(new Dimension(10, 0)));

		
		JPanel panelData = new JPanel();
		panelData.setBorder(BorderFactory.createTitledBorder("General data"));
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
		panelData.add(panelName);
		panelData.add(Box.createVerticalStrut(5));

		// SUPERVISED PEERS
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

		panelList.setBorder(BorderFactory.createTitledBorder("Supervised Peers"));
		panelList.add(scrollListPeers);
		panelList.add(panelListButton);
		panelList.add(scrollListSelectedPeers);

		// BUTTONS
		JPanelButton panelButton = new JPanelButton();
		JButton btOk = new JButton("Ok");

		switch (mode) {
		case MODE_CREATE:
			btOk.setActionCommand(Controller.AC_CREATE_SUP);
			break;
		case MODE_MODIF:
			btOk.setActionCommand(Controller.AC_MODIF_SUP);
			break;
		}

		btOk.addActionListener(listener);
		JButton btCancel = new JButton("Cancel");
		btCancel.setActionCommand(Controller.AC_CANCEL_CREATE_SUP);
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
		panelGlobal.add(panelList);
		panelGlobal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGlobal.add(panelButton);

		setTitle("Superviser");
		setResizable(false);
		setModal(true);
		setSize(500, 400);
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

	public void setName(String s) {
		txtSuperviserName.setText(s);
	}

	public String getName() {
		return txtSuperviserName.getText();
	}


	public void setSelectedList(PeerCollection list) {
		peerSelectedList = null;
		peerSelectedList = list;

		int size = peerSelectedList.size();
		// Update general list
		for (int i = 0; i < size; i++) {
			peerList.remove(peerSelectedList.get(i));
		}
	
		
		listPeers.setListData(peerList.getListNames());
		listSelectedPeers.setListData(peerSelectedList.getListNames());
	}

	public PeerCollection getPeerSelectedList() {
		return peerSelectedList;
	}
	
	public PeerCollection getPeerNotSelectedList() {
		return peerList;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}