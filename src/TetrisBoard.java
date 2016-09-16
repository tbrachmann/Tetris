import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TetrisBoard extends JPanel{

	//Instead of X -> Y's this goes Y -> X's so it will be easier to check if row is ready to be removed.
	private HashMap<Integer, ArrayList<Coordinate>> filledCoordinates = new HashMap<Integer, ArrayList<Coordinate>>();
	private final int rows;
	private final int columns;
	
	public TetrisBoard(int rows, int columns){
		super();
		this.rows = rows;
		this.columns = columns;
		setLayout(new GridLayout(rows, columns));
		setFocusable(true);
		setPreferredSize(new Dimension(240, 480));
		/* Sets border for testing. */
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                this.getBorder()));
		/* For loops that populate the game grid: 10 X 20 grid of gameCells: a 
		 * JComponent that holds its xy coordinates and a boolean of whether or not
		 * it is filled. */
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				add(new GameCell(j, i));
				revalidate();
			}
		}
	}
	
	public boolean fillCells(LinkedHashMap<Coordinate, Color> newCoordinates, Coordinate[] oldCoordinates){
		int x;
		int y;
		for(Coordinate i : newCoordinates.keySet()){
			x = i.getX();
			y = i.getY();
			//If Coordinate is already contained in Tetromino, then it can move there no problem.
			if(oldCoordinates != null && Arrays.asList(oldCoordinates).contains(i)){
				continue;
			} else if(x < 0 || x > 9){
				return false;
			} else if(y < 0 || y > 19){
				return false;
			} else if(filledCoordinates.containsKey(y) && filledCoordinates.get(y).contains(i)){
				//return false if the coordinates that you want to put a piece are already there - when adding new piece!
				//this ends the game
				return false;
			}
		}
		if(oldCoordinates != null) {
			for(Coordinate i : oldCoordinates){
				getComponent(i).empty();
				filledCoordinates.get(i.getY()).remove(i);
			}
		}
		for(Coordinate i : newCoordinates.keySet()){
			x = i.getX();
			y = i.getY();
			getComponent(i).fill(newCoordinates.get(i));
			if(!filledCoordinates.containsKey(y)){
				filledCoordinates.put(y, new ArrayList(Arrays.asList(i)));
			} else {
				filledCoordinates.get(y).add(i);
			}
		}
		return true;
	}
	
	public void emptyCells(ArrayList<Coordinate> oldCoordinates) {
		for(Coordinate coord : oldCoordinates) {
			getComponent(coord).empty();
			filledCoordinates.get(coord.getY()).remove(filledCoordinates.get(coord.getY()).indexOf(coord));
		}
	}
	
	
	//This method is called by Tetris when focusPiece can no longer move
	public void getRowsToRemove(Coordinate[] occupiedCells) {
		int rowsRemoved = 0;
		int lastYCoord = 0;
		//sort occupiedCells to start with greatest Y first
		TreeSet<Integer> yCoords = new TreeSet<Integer>((y1, y2) -> -1*Integer.compare(y1, y2));
		for(Coordinate coord : occupiedCells){
			yCoords.add(coord.getY());
		}
		for (int yCoord : yCoords){
			System.out.println(yCoord);
			ArrayList<Coordinate> currentRow = filledCoordinates.get(yCoord);
			//If its full - add to list to move
			if(currentRow.size() == 10) {
				removeRow(currentRow);
				rowsRemoved++;
			} else {
				moveRow(currentRow, rowsRemoved);
			}
			lastYCoord = yCoord;
		}
		//End for loop, and move on from last used coordinates
		//If any rows have been removed:
		int yCoord = lastYCoord--;
		if(rowsRemoved > 0) {
			while(yCoord >= 0) {
				ArrayList<Coordinate> currentRow = filledCoordinates.get(yCoord);
				//if current row exists, then move it down by the total number of rows removed
				if(currentRow.size() > 0) {
					moveRow(currentRow, rowsRemoved);
				}
				yCoord--;
			}
		}
	}
	
	private void moveRow(ArrayList<Coordinate> row, int numberToMove){
		LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
		for(Coordinate coord : row){
			newCoordinates.put(new Coordinate(coord.getX(), coord.getY() + numberToMove), getComponent(coord).getColor());
		}
		fillCells(newCoordinates, row.toArray(new Coordinate[row.size()]));
	}
	
	private void removeRow(ArrayList<Coordinate> row) {
		Iterator<Coordinate> iter = row.iterator();
		while(iter.hasNext()){
			Coordinate coord = iter.next();
			getComponent(coord).empty();
			iter.remove();
		}
	}
	
	
	public GameCell getComponent(Coordinate i) {
		return (GameCell) super.getComponent(i.getX() + (i.getY()*columns));
	}
	
	public int getColumns(){
		return columns;
	}
	
	public int getRows(){
		return rows;
	}
	
}
