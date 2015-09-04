package orage.model;
import java.util.ArrayList;
import java.util.Random;

public class Board {

	private int xsize = 0;
	private int ysize = 0;
	private int matMaxVal = 0;
	private int matMinVal = 0;
	
	private ArrayList cellList;
	
	public Board () {
		
	}
	
	public Board (int x, int y, int max, int min) {
		xsize = x;
		ysize = y;
		matMaxVal = max;
		matMinVal = min;
		
		cellList = new ArrayList();
		 //Generate random values and fill the map
        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());
        int range = matMaxVal - matMinVal;
        int rande;
        int val;
        Cell c;
        
      
        for (int i=0; i < xsize ;i++) {
            for (int j=0; j < ysize ; j++) {  
                rande = rd.nextInt(range);
                  
                val = (rande + matMinVal);
                c = new Cell(i, j, val);
                cellList.add(c);
            }
        } 
	}
	
	public Cell getCell(int x, int y) {
		Cell cell = new Cell(x, y);
		int size = cellList.size();
		
		for (int i = 0; i < size; i++) {
			if ( ((Cell)cellList.get(i)).equals(cell) )
					cell = (Cell)cellList.get(i);
		}
	
		return cell;
	}
	public int valueOf(Cell cell) {
		int size = cellList.size();
		
		for(int i = 0; i < size; i++) {
			if ( ((Cell) cellList.get(i)).equals(cell) )
					cell = (Cell)cellList.get(i);
		}
		
		return cell.getValue();
	}
	
	public int getXsize() {
		return xsize;
	}
	
	public int getYsize() {
		return ysize;
	}
	
	public ArrayList getCellList() {
		return cellList;
	}
	
	
	public void setMatMaxVal(int matMaxVal) {
		this.matMaxVal = matMaxVal;
	}
	
	public void setMatMinVal(int matMinVal) {
		this.matMinVal = matMinVal;
	}
	
	public void setXsize(int xsize) {
		this.xsize = xsize;
	}
	
	public void setYsize(int ysize) {
		this.ysize = ysize;
	}
	
	public void setCellList(ArrayList cellList) {
		this.cellList = null;
		this.cellList = cellList;
	}
	
	public String toString() {
		
		return "BOARD : " + String.valueOf(xsize) + "," + String.valueOf(ysize);
	}
	
	public String toXML() {
		String s = "\t<board>\r\n" +
						"\t\t<xsize>" + xsize + "</xsize>\r\n" +
						"\t\t<ysize>" + ysize + "</ysize>\r\n" +
						"\t\t<maxval>" + matMaxVal + "</maxval>\r\n" +
						"\t\t<minval>" + matMinVal + "</minval>\r\n" +
						"\t\t<cells>\r\n";
		int size = cellList.size();
		for (int i = 0; i < size; i++) {
			s = s + ((Cell)cellList.get(i)).toXML();
		}
		s = s + "\t\t</cells>\r\n" + "\t</board>\r\n";
		
		return s;
	}      
}
