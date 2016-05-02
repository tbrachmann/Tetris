import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TetrisBoard extends JPanel{

	//Instead of X -> Y's this goes Y -> X's so it will be easier to check if row is ready to be removed.
	private HashMap<Integer, ArrayList<Coordinate>> filledCoordinates = new HashMap<Integer, ArrayList<Coordinate>>();
	
	public TetrisBoard(){
		super();
		setLayout(new GridLayout(20, 10));
		setFocusable(true);
		setPreferredSize(new Dimension(240, 480));
		/* Sets border for testing. */
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                this.getBorder()));
		/* For loops that populate the game grid: 10 X 20 grid of gameCells: a 
		 * JComponent that holds its xy coordinates and a boolean of whether or not
		 * it is filled. */
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				add(new GameCell(j, i));
				revalidate();
			}
		}
	}
	
	public boolean fillCells(LinkedHashMap<Coordinate, Color> newCoordinates, Coordinate[]oldCoordinates){
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
	
	
	//This method is called by Tetris when focusPiece can no longer move
	public void getRowsToRemove(Coordinate[] occupiedCells) {
		//arg is passed in from Tetris - taken from focusPiece
		int rowsRemoved = 0;
		int lastYCoord = 0;
		ArrayList<ArrayList<Coordinate>> rowsToRemove = new ArrayList<ArrayList<Coordinate>>();
		TreeSet<Integer> yCoords = new TreeSet<Integer>((y1, y2) -> -1*Integer.compare(y1, y2));
		//sort occupiedCells to start with greatest Y first
		for(Coordinate coord : occupiedCells){
			yCoords.add(coord.getY());
		}
		for (int yCoord : yCoords){
			System.out.println(yCoord);
			ArrayList<Coordinate> currentRow = filledCoordinates.get(yCoord);
			//If its full - add to list to move
			if(currentRow.size() == 10) {
				rowsToRemove.add(currentRow);
				rowsRemoved++;
			} else if (rowsToRemove.size() != 0){
			//Fuck below comment... gravity can happen when I'm feeling bored
			//If its not full - move down all rows above and keep going
				for(ArrayList<Coordinate> row : rowsToRemove) {
					System.out.println(row);
				}
				removeRows(rowsToRemove, filledCoordinates.get(yCoord), rowsRemoved);
				rowsToRemove.clear();
			}
			lastYCoord = yCoord;
		}
		if(rowsToRemove.size() > 0) {
			lastYCoord--;
			removeRows(rowsToRemove, filledCoordinates.get(lastYCoord), rowsRemoved);
			rowsToRemove.clear();
		}
		if(rowsRemoved > 0) {
			System.out.println("Clearing everything above " + lastYCoord);
			ArrayList<Coordinate> oldCoordinates = new ArrayList<Coordinate>();
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			ArrayList<Coordinate> rowToMove;
			while(lastYCoord >= 0) {
				if(filledCoordinates.containsKey(lastYCoord)) {
					rowToMove = filledCoordinates.get(lastYCoord);
					oldCoordinates.addAll(rowToMove);
					for(Coordinate i : rowToMove){
						newCoordinates.put(new Coordinate(i.getX(), i.getY() + rowsRemoved), getComponent(i).getColor());
					}
				}
				lastYCoord--;
			}
			if(oldCoordinates.size() > 0){
				fillCells(newCoordinates, oldCoordinates.toArray(new Coordinate[oldCoordinates.size()]));
			}
		}
		//Clear list for last time - YOU FORGOT TO IMPLEMENT THIS IDIOT
		//Basically - for each row where y < yCoord, move it down by number of rows removed
		
	}
	
	private void removeRows(ArrayList<ArrayList<Coordinate>> rowsToRemove, ArrayList<Coordinate> rowToMove, int rowsRemoved) {
		ArrayList<Coordinate> oldCoordinates = new ArrayList<Coordinate>();
		LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
		for(ArrayList<Coordinate> row : rowsToRemove){
			oldCoordinates.addAll(row);
		}
		oldCoordinates.addAll(rowToMove);
//		System.out.println(oldCoordinates.size());
		rowToMove.replaceAll(c -> new Coordinate(c.getX(), c.getY() + rowsRemoved));
		for(Coordinate coord : rowToMove) {
			newCoordinates.put(coord, getComponent(coord).getColor());
		}
//		System.out.println(newCoordinates.size());
		fillCells(newCoordinates, oldCoordinates.toArray(new Coordinate[oldCoordinates.size()]));
//		for(int i : filledCoordinates.keySet()) {
//			System.out.print(i + " -> ");
//			for(Coordinate c: filledCoordinates.get(i)) {
//				System.out.print(c.getX() + " ");
//			}
//			System.out.print("\n");
//		}
	}
	
	
	public GameCell getComponent(Coordinate i) {
		return (GameCell) super.getComponent(i.getX() + (i.getY()*10));
	}
}
