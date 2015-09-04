package orage.model;

import orage.model.peer.Element;

/*
 * Created on 18 oct. 2004
 */

/**
 * @author REA/pierre
 */
public class Cell extends Element{

   
    /**
     * The value
     */
    private int value;
    
    /**
     * Constructs a new Cell
     * @param x row
     * @param y column
     */
    public Cell(int x, int y){
       super(x,y);
    }
    
    /**
     * Constructs a new Cell
     * @param x row
     * @param y column
     * @param val value
     */
    public Cell(int x, int y, int val){
    	super(x,y);
        value = val;
    } 
    
    
    public Cell (Cell cell) {
    	super(cell.posX,cell.posY);
        this.value = cell.value;
    }
    /**
     * Sets the value of the Cell
     * @param val The value to set
     */
    public void setValue(int val){
        value = val;
    }
    
    /**
     * Gets the value of the Cell
     * @return The value of the cell
     */
    public int getValue(){
        return value;
    }  
    
	public boolean equals(Cell obj) {
		Cell cell = obj;
		return ((this.posX == cell.posX) && (this.posY == cell.posY));
	}
	
	public String toString() {
		return new String ("Cell(" + posX + ", " + posY + ")");
	}
	
	public String toXML() {
		String s = "\t\t\t<cell>\r\n" +
				   		"\t\t\t\t<x>" + posX + "</x>\r\n" +
						"\t\t\t\t<y>" + posY + "</y>\r\n" +
						"\t\t\t\t<value>" + value + "</value>\r\n" +
					"\t\t\t</cell>\r\n";
		return s;
						
	}
}