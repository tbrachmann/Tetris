import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class TetrisBoard extends JPanel{

	private HashMap<Integer, ArrayList<Integer>> filledCoordinates = new HashMap<Integer, ArrayList<Integer>>();
	
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
	
	public boolean fillCells(Coordinate[] newCoordinates, Coordinate[]oldCoordinates, Color newColor){
		int x;
		int y;
		for(Coordinate i : newCoordinates){
			x = i.getX();
			y = i.getY();
			//If Coordinate is already contained by Tetromino, then it can move there no problem.
			if(oldCoordinates != null && Arrays.asList(oldCoordinates).contains(i)){
				continue;
			} else if(x < 0 || x > 9){
				return false;
			} else if(y < 0 || y > 19){
				return false;
			} else if(filledCoordinates.containsKey(x) && filledCoordinates.get(x).contains(y)){
				return false;
			}
		}
		if(oldCoordinates != null) {
			for(Coordinate i : oldCoordinates){
				getComponent(i).empty();
				filledCoordinates.get(i.getX()).remove(filledCoordinates.get(i.getX()).indexOf(i.getY()));
			}
		}
		for(Coordinate i : newCoordinates){
			x = i.getX();
			y = i.getY();
			getComponent(i).fill(newColor);
			if(!filledCoordinates.containsKey(x)){
				filledCoordinates.put(x, new ArrayList(Arrays.asList(y)));
			} else {
				filledCoordinates.get(x).add(y);
			}
		}
		return true;
	}
	
	public GameCell getComponent(Coordinate i) {
		return (GameCell) super.getComponent(i.getX() + (i.getY()*10));
	}
}
