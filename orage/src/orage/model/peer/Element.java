package orage.model.peer;


public class Element {
	
	protected int posX = 0;
	protected int posY = 0;
	
	public Element() {
		
	}
	
	public Element(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getX() {
		return posX;
	}
	
	public void setX(int posX) {
		this.posX = posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setY(int posY) {
		this.posY = posY;
	}
	
	public int getValue(){return 0;} 
	
	public String toXML() {return "";}
}
