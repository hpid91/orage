package orage.ui.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import orage.model.Cell;

public class CellComponent extends JComponent implements MouseListener {
	
	private static int WIDTH = 20;
	private static int HEIGHT = 20;
	
	private Cell cell;
	
	private Color color = ORIGINAL_COLOR;
	private int posX = 0;
	private int posY = 0;
	private int value;
	
	private boolean select = false;
	
	public final static Color ORIGINAL_COLOR = new Color(248,248,228);
	
	public CellComponent(Cell cell) {
		super();
		this.cell = cell; 
		posX = cell.getX() * 20;
		posY = cell.getY() * 20;
		value = cell.getValue();
		
		setLayout(new GridLayout(1,1));
		addMouseListener(this);
	}
	
	public CellComponent(Color c, int x, int y) {
		super();
		color = c;
		posX = x;
		posY = y;
		
		setLayout(new GridLayout(1,1));
		addMouseListener(this);
	}
	
	public void mouseEntered(MouseEvent e) {
		
		String s = "";
		PeerComponent selectPeer = null;
		if (e.getSource().getClass().equals(PeerComponent.class)) {
			selectPeer = (PeerComponent) e.getSource();
			s = "Peer : " + selectPeer.getPeerModel().getName() + " on Cell : ";
		}
		s = s + " X :" + String.valueOf(cell.getX()) +
		   		" Y :" + String.valueOf(cell.getY()) +
				" Value : " + String.valueOf(value);
		if (selectPeer != null) selectPeer.setToolTipText(s);
		else setToolTipText(s);
	}
	
	public void mouseClicked(MouseEvent e) {
		
		//getParent().dispatchEvent(e);
	}
	
	public void mouseExited(MouseEvent e) {
		//select = false;
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void paint(Graphics g) {
		super.paint(g);
	
		g.setColor(color);
		g.fillRect(posX, posY , WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(posX, posY , WIDTH, HEIGHT);
		
		if (select) {
			g.setColor(Color.GREEN);
			g.drawRect(posX, posY , WIDTH, HEIGHT);
			g.drawRect(posX+1, posY+1 , WIDTH-2, HEIGHT-2);
		}
		
		//paintChildren(g);
	}

	protected void paintBorder(Graphics g) {
		
		super.paintBorder(g);
		if (select) {
			
			g.setColor(Color.GREEN);
			//g.drawRect(posX, posY , WIDTH, HEIGHT);
			
			g.drawRect(posX+1, posY+1 , WIDTH-1, HEIGHT-1);
		} else {
			g.setColor(Color.BLACK);
			g.drawRect(posX, posY , WIDTH, HEIGHT);
		}
		
	}
	
	public void setColor(Color color) {
		this.color = color;
		validate();
	}
	
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		validate();
	}
	
	public boolean isSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	public boolean equals(Cell obj) {
		
		return (this.cell.equals(obj));
	}
	
	public boolean equals(CellComponent obj) {
		CellComponent toCompare = (CellComponent) obj;
		return (this.cell.equals(toCompare.cell));
	}
	
	public Cell getCell() {
		return cell;
	}
	
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
}
