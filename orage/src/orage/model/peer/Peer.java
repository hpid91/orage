package orage.model.peer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import orage.model.ActionPonderation;
import orage.model.ActionPonderationCollection;
import orage.model.Board;
import orage.model.Cell;
import orage.model.PeerCollection;
import orage.model.superviser.Superviser;
import orage.model.superviser.SuperviserCollection;


public class Peer extends Element {

	/**
	 * PEER DATA
	 */
	String id;

	String name;

	Color color = Color.BLACK;

	boolean liar = false;

	boolean dead = false;

	private int ponderation = 1;

	// Board limits : they represent the possible range
	// i.e : maxX == 20 => x :{0,20}
	int maxX = 0;

	int maxY = 0;

	// PEER known cells of its query zone : +5/-5 in X, Y
	ArrayList knownCellsZone;

	//PEER known cells of the board
	ArrayList knownCellsBoard;

	// TOPOLOGICAL NETWORK
	PeerCollection knownPeers;

	int zoneCellsSize = 0;

	Board board;

	// SuperviserAction ponderation
	private ActionPonderationCollection ponderations;

	// Attributes to perform randomizations
	Random rd = new Random();

	Random actionrd = new Random();

	static final int NB_ACTIONS = 100;

	// Suppervisers
	SuperviserCollection loggers;

	public Peer(String i, String n, int x, int y, int mX, int mY, boolean l,
				boolean d, Color c, ArrayList kcZone, ArrayList kcBoard) {
		super(x, y);
		id = i;
		name = n;
		liar = l;
		dead = d;
		color = c;

		maxX = mX;
		maxY = mY;

		knownCellsZone = kcZone;
		knownCellsBoard = kcBoard;
		knownPeers = new PeerCollection();

		computeZoneCellsSize();
		rd.setSeed(System.currentTimeMillis());
		actionrd.setSeed(System.currentTimeMillis());

	}
	
	public Peer( int x, int y, int mX, int mY, ArrayList kcZone, ArrayList kcBoard) {
		super(x, y);
		maxX = mX;
		maxY = mY;
	
		knownCellsZone = kcZone;
		knownCellsBoard = kcBoard;
		knownPeers = new PeerCollection();
	
		computeZoneCellsSize();
		rd.setSeed(System.currentTimeMillis());
		actionrd.setSeed(System.currentTimeMillis());
	
	}
	
	public Peer(String s, boolean l, SuperviserCollection loggers) {
		name = s;
		liar = l;
		knownCellsZone = new ArrayList();
		knownCellsBoard = new ArrayList();
		knownPeers = new PeerCollection();

		this.loggers = loggers;
		initPonderations();

		rd.setSeed(System.currentTimeMillis());
		actionrd.setSeed(System.currentTimeMillis());
	}

	public Peer(String s, int x, int y, Board b, boolean l,
            SuperviserCollection loggers) {
		super(x, y);
		name = s;
		liar = l;
		board = b;
		this.loggers = loggers;

		maxX = board.getXsize() - 1;
		maxY = board.getYsize() - 1;

		knownCellsZone = new ArrayList();
		knownCellsBoard = new ArrayList();
		knownPeers = new PeerCollection();

		computeZoneCellsSize();
		rd.setSeed(System.currentTimeMillis());
		actionrd.setSeed(System.currentTimeMillis());
		initPonderations();
	}

	public Peer(String s, int x, int y, Board b, boolean l, boolean d, Color c,
            SuperviserCollection loggers) {
		super(x, y);
		name = s;
		liar = l;
		dead = d;
		color = c;
		board = b;
		this.loggers = loggers;

		maxX = board.getXsize() - 1;
		maxY = board.getYsize() - 1;

		knownCellsZone = new ArrayList();
		knownCellsBoard = new ArrayList();
		knownPeers = new PeerCollection();

		computeZoneCellsSize();
		rd.setSeed(System.currentTimeMillis());
		actionrd.setSeed(System.currentTimeMillis());

		initPonderations();
	}

	public Peer(String i, String s, int x, int y, Board b, boolean l,
			boolean d, Color c, ArrayList kcZone, ArrayList kcboard,
            SuperviserCollection loggers) {
		super(x, y);
		id = i;
		name = s;
		liar = l;
		dead = d;
		color = c;
		board = b;
		this.loggers = loggers;

		maxX = board.getXsize() - 1;
		maxY = board.getYsize() - 1;

		knownCellsZone = new ArrayList();
		knownCellsZone.addAll(kcZone);
		knownCellsBoard = new ArrayList();
		knownCellsBoard.addAll(kcboard);
		knownPeers = new PeerCollection();

		initPonderations();

		computeZoneCellsSize();
		rd.setSeed(System.currentTimeMillis());
	}

	private int getValueOf(Cell bc) {
		int val = -1;
		ArrayList global = getKnownCells();
		int size = global.size();
		for (int i = 0; i < size; i++) {
			if (((Cell) global.get(i)).equals(bc)) {
				val = ((Cell) global.get(i)).getValue();
			}
		}

		return val;
	}

	private boolean isAKnownCell(Cell b) {
		boolean result = false;
		ArrayList global = getKnownCells();
		int size = global.size();

		for (int i = 0; i < size; i++) {
			if (((Cell) global.get(i)).equals(b))
				result = true;
		}

		return result;
	}

	private void computeZoneCellsSize() {
		zoneCellsSize = 0;
		int iMaxX, iMinX, iMaxY, iMinY;

		if (posX - 5 < 0)
			iMinX = 0;
		else
			iMinX = posX - 5;

		if (posX + 5 > maxX)
			iMaxX = maxX;
		else
			iMaxX = posX + 5;

		if (posY - 5 < 0)
			iMinY = 0;
		else
			iMinY = posY - 5;

		if (posY + 5 > maxY)
			iMaxY = maxY;
		else
			iMaxY = posY + 5;

		zoneCellsSize = (iMaxX - iMinX + 1) * (iMaxY - iMinY + 1);

	}

	public int valueOf(Cell bc) {
		if (!isAKnownCell(bc) || dead)
			return -1;

		else if (isAKnownCell(bc) && !liar)
			return getValueOf(bc);

		else if (isAKnownCell(bc) && liar) {

			return rd.nextInt(getValueOf(bc) + 2);
		}
		return -1;
	}

	public float computeAverage() {
		PeerActionGetQueryList action = new PeerActionGetQueryList(this);
		float result = -1;
		if (action.execute()) {

			ArrayList list = action.getQueryList();
			int size = list.size();

			for (int i = 0; i < size; i++) {
				result = result + ((Cell) list.get(i)).getValue();
			}

			result = (float) (result / size);
		}

		loggers.addAction(action);
		return result;

	}

	/**
	 * Move the bot to new coordinates. This operation needs to transfer the
	 * known cells of the zone to the known cells of the board
	 * 
	 * @param x :
	 *            the new x coordinate
	 * @param y :
	 *            the new y coordinate
	 */
	public boolean move(int x, int y) {
		int size = knownCellsZone.size();
		int iMaxX, iMinX, iMaxY, iMinY = 0;

		setX(x);
		setY(y);

		// COMPUTE new query zone limits
		if (posX - 5 < 0)
			iMinX = 0;
		else
			iMinX = posX - 5;

		if (posX + 5 > maxX)
			iMaxX = maxX;
		else
			iMaxX = posX + 5;

		if (posY - 5 < 0)
			iMinY = 0;
		else
			iMinY = posY - 5;

		if (posY + 5 > maxY)
			iMaxY = maxY;
		else
			iMaxY = posY + 5;

		// Build new query zone cell list
		Cell temp;
		ArrayList tempList = new ArrayList();
		// Add common cells
		for (int i = 0; i < size; i++) {
			temp = (Cell) knownCellsZone.get(i);

			// If the cell coordinates from the previous zone corresponds
			// to the new zone , then don't remove it from the known zone list
			if ((temp.getX() >= iMinX) && (temp.getX() <= iMaxX)
					&& (temp.getY() >= iMinY) && (temp.getY() <= iMaxY)) {
				tempList.add(temp);
			} else {
				knownCellsBoard.add(temp);
			}

		}
		knownCellsZone = null;
		knownCellsZone = new ArrayList();
		knownCellsZone.addAll(tempList);

		// Compute the query zone cell list size
		zoneCellsSize = (iMaxX - iMinX + 1) * (iMaxY - iMinY + 1);

		if ((posX < maxX) && (posY < maxY))
			return true;
		else
			return false;
	}

	public void makeAnAction(String kind) {
		PeerAction action = null;
		boolean ok = false;

		if (kind.equals(PeerAction.QUERY_KIND)) {
			action = new PeerActionQuery(this);
		} else if (kind.equals(PeerAction.TALK_KIND)) {
			action = new PeerActionTalk(this);
		} else if (kind.equals(PeerAction.MOVE_KIND)) {
			action = new PeerActionMove(this);
		} else if (kind.equals(PeerAction.KILL_KIND)) {
			action = new PeerActionKill(this);
		} else if (kind.equals(PeerAction.AVG_KIND)) {
			action = new PeerActionAvg(this);
		}

		if (action != null)
			ok = action.execute();
		if (ok) {
            loggers.addAction(action);
        }

	}

	public void makeAleaAction() {
		int actionNb = actionrd.nextInt(NB_ACTIONS);
		float sum = ponderations.getPonderationSum();

		if (actionNb < (int) (NB_ACTIONS * ((float) (ponderations
				.getPonderation(PeerActionQuery.class) / sum)))) {
			makeAnAction(PeerAction.QUERY_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ((float) (ponderations
				.getPonderation(PeerActionQuery.class) + ponderations
				.getPonderation(PeerActionTalk.class)) / sum))) {
			makeAnAction(PeerAction.TALK_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ((float) (ponderations
				.getPonderation(PeerActionQuery.class)
				+ ponderations.getPonderation(PeerActionTalk.class) + ponderations
				.getPonderation(PeerActionMove.class)) / sum))) {
			makeAnAction(PeerAction.MOVE_KIND);

		} else if (actionNb < (int) (NB_ACTIONS * ((float) (ponderations
				.getPonderation(PeerActionQuery.class)
				+ ponderations.getPonderation(PeerActionTalk.class)
				+ ponderations.getPonderation(PeerActionMove.class) + ponderations
				.getPonderation(PeerActionKill.class)) / sum))) {
			makeAnAction(PeerAction.KILL_KIND);
			
		} else if (actionNb < (int) (NB_ACTIONS * ((float) 
				(ponderations.getPonderation(PeerActionQuery.class)	+
				 ponderations.getPonderation(PeerActionTalk.class) +
				 ponderations.getPonderation(PeerActionMove.class) + 
				 ponderations.getPonderation(PeerActionKill.class) + 
				 ponderations.getPonderation(PeerActionAvg.class) ) / sum))) {
			makeAnAction(PeerAction.AVG_KIND);
		}

	}

	public String toXML() {
		String s = "\t\t<peer id=\"" + id + "\">\r\n" + "\t\t\t<name>" + name
				+ "</name>\r\n" + "\t\t\t<color>\r\n" + "\t\t\t\t<r>"
				+ color.getRed() + "</r>\r\n" + "\t\t\t\t<g>"
				+ color.getGreen() + "</g>\r\n" + "\t\t\t\t<b>"
				+ color.getBlue() + "</b>\r\n" + "\t\t\t</color>\r\n"
				+ "\t\t\t<liar>" + liar + "</liar>\r\n" + "\t\t\t<dead>" + dead
				+ "</dead>\r\n" + "\t\t\t<x>" + posX + "</x>\r\n" + "\t\t\t<y>"
				+ posY + "</y>\r\n" + "\t\t\t<maxX>" + maxX + "</maxX>\r\n"
				+ "\t\t\t<maxY>" + maxY + "</maxY>\r\n" + "\t\t\t<ponderation>"
				+ ponderation + "</ponderation>\r\n";

		// Action ponderations
		s = s + "\t\t\t<actionponderatioons>\r\n";
		ActionPonderation ap;
		int size = ponderations.size();
		for (int i = 0; i < size; i++) {
			ap = ponderations.getActionPonderation(i);
			s = s + ap.toXML();
		}

		s = s + "\t\t\t</actionponderatioons>\r\n";

		// known cells zone
		s = s + "\t\t\t<knowncellszone>\r\n";
		size = knownCellsZone.size();
		for (int i = 0; i < size; i++) {
			s = s + ((Cell) knownCellsZone.get(i)).toXML();
		}
		s = s + "\t\t\t</knowncellszone>\r\n";

		// known cells board
		size = knownCellsBoard.size();
		s = s + "\t\t\t<knowncellsboard>\r\n";
		for (int i = 0; i < size; i++) {
			s = s + ((Cell) knownCellsBoard.get(i)).toXML();
		}
		s = s + "\t\t\t</knowncellsboard>\r\n";

		// known peers
		s = s + "\t\t\t<knownpeers>\r\n";
		String knownpeer;
		size = knownPeers.size();
		for (int i = 0; i < size; i++) {
			knownpeer = ((Peer) knownPeers.get(i)).getId();
			s = s + "\t\t\t\t<knownpeer id=\"" + knownpeer + "\"/>\r\n";
		}
		s = s + "\t\t\t</knownpeers>\r\n";

		s = s + "\t\t</peer>\r\n";
		return s;
	}

	public SuperviserCollection getLoggers() {
		return loggers;
	}

	public int sizeKnownCellsZone() {
		return knownCellsZone.size();
	}

	public int sizeKnownCellsBoard() {
		return knownCellsBoard.size();
	}

	public void setKnownCellsBoard(ArrayList knownCellsBoard) {
		this.knownCellsBoard = knownCellsBoard;
	}

	public void setKnownCellsZone(ArrayList knownCellsZone) {
		this.knownCellsZone = knownCellsZone;
	}

	public void setLoggers(SuperviserCollection loggers) {
		this.loggers = loggers;
	}

	public void addLogger(Superviser logger) {
		if (this.loggers == null) this.loggers = new SuperviserCollection();
			
		this.loggers.add(logger);
	}
	
	public boolean setBoard(Board board) {
		this.board = board;

		maxX = board.getXsize() - 1;
		maxY = board.getYsize() - 1;

		if (posX < maxX && posY < maxY)
			return true;
		else
			return false;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isLiar() {
		return liar;
	}

	public void setLiar(boolean lyer) {
		this.liar = lyer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public boolean isDead() {
		return dead;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void removeAllKnownPeers() {
		knownPeers = null;
		knownPeers = new PeerCollection();
	}

	public void removeAKnownPeer(Peer b) {
		knownPeers.remove(b);
	}

	public void addAKnownPeer(Peer b) {
		knownPeers.add(b);
	}

	public PeerCollection getKnownPeers() {
		return knownPeers;
	}

	public void setKnownPeers(PeerCollection knownPeers) {
		this.knownPeers = knownPeers;
	}

	public ArrayList getKnownCells() {
		ArrayList global = new ArrayList();
		global.addAll(knownCellsZone);
		global.addAll(knownCellsBoard);
		return global;
	}

	public ActionPonderationCollection getPonderations() {
		return ponderations;
	}

	public void setPonderations(ActionPonderationCollection ponderations) {
		this.ponderations = ponderations;
	}

	private void initPonderations() {
		ponderations = new ActionPonderationCollection();
		ponderations.addActionPonderation(3, PeerActionQuery.class);
		ponderations.addActionPonderation(3, PeerActionTalk.class);
		ponderations.addActionPonderation(3, PeerActionAvg.class);
		ponderations.addActionPonderation(3, PeerActionMove.class);
		ponderations.addActionPonderation(1, PeerActionKill.class);
	}

	public boolean isAKnownPeer(Peer p) {
		return (knownPeers.isIn(p));
	}

	public boolean equals(Peer obj) {
		return (this.id.equals(obj.id));
	}

	public int getPonderation() {
		return ponderation;
	}

	public void setPonderation(int ponderation) {
		this.ponderation = ponderation;
	}
	/**
	 * @return Returns the zoneCellsSize.
	 */
	public int getZoneCellsSize() {
		return zoneCellsSize;
	}
	/**
	 * @return Returns the maxX.
	 */
	public int getMaxX() {
		return maxX;
	}
	/**
	 * @return Returns the maxY.
	 */
	public int getMaxY() {
		return maxY;
	}
	/**
	 * @return Returns the rd.
	 */
	public Random getRd() {
		return rd;
	}
	/**
	 * @return Returns the knownCellsZone.
	 */
	public ArrayList getKnownCellsZone() {
		return knownCellsZone;
	}
}