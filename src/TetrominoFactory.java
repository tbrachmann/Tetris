import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class TetrominoFactory extends JComponent{
	int x, y;
	TetrominoType type;
	TetrisBoard board;
	
	public TetrominoFactory(){
		super();
	}
	
	public Tetromino newTetronimo(int x, int y, TetrominoType type, TetrisBoard board) {
		x = x;
		y = y;
		type = type;
		board = board;
		switch (type) {
			case I:
				return new IType(x, y, board);
			default:
				return new IType(x, y, board);
		}
		//sets location in gamePanel - coordConverter does not yet exist
		//gamePanel.coordConverter(x, y);
	}
	
	public static int coordConverter(int x, int y) {
		return x + (y*10);
	}
	
	public abstract class Tetromino extends JComponent {
		protected int x,y;
		protected TetrominoType type;
		protected Color color;
		protected TetrisBoard board;
		protected GameCell[] occupiedCells = new GameCell[4];
		protected Orientation myOrientation = Orientation.NORTH;
		
		public Tetromino(int x, int y, TetrisBoard board){
			this.x = x;
			this.y = y;
			this.color = Colors.getNextColor();
			this.board = board;
			setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white),
	                this.getBorder()));
			fillCells();
		}
		
		public Color getColor() {
			return color;
		}
		
		public TetrominoType getType() {
			return type;
		}
		
		public TetrisBoard getBoard() {
			return board;
		}
		
		public Orientation getOrientation() {
			return myOrientation;
		}
		
		public abstract void rotate();
		public abstract void move();
		public abstract void moveLeft();
		public abstract void moveRight();
		protected abstract void fillCells();
	}
	
	public class IType extends Tetromino{
		
		public IType(int x, int y, TetrisBoard board){
			super(x, y, board);
		};
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		public String toString() {
			return "This is an I-tetronimo!";
		}
		
		protected void fillCells(){
			GameCell cell;
			GameCell lastCell = null;
			for (int i = 0; i < 4; i++){
				cell = occupiedCells[i];
				if(cell != null){
					if(cell != lastCell) {
						System.out.println(cell.getXCoord() + " " + cell.getYCoord());
						cell.empty();
					}
				}
				switch(myOrientation){
					case NORTH:
//						System.out.println((x+i) + " " + y);
						cell = (GameCell)board.getComponent(x+i, y);
						break;
					case SOUTH:
//						System.out.println((x+i) + " " + y);
						cell = (GameCell)board.getComponent(x+i, y);
						break;
					case EAST:
//						System.out.println(x + " " + (y+i));
						cell = (GameCell)board.getComponent(x, y+i);
						break;
					case WEST:
//						System.out.println(x + " " + (y+i));
						cell = (GameCell)board.getComponent(x, y+i);
						break;
				}
				lastCell = cell;
				occupiedCells[i] = cell;
				cell.fill(color);
			}
		}
		
		public void rotate(){
			myOrientation = Orientation.getNextOrientation(myOrientation);
			System.out.println(myOrientation);
			fillCells();
		}
		
		public void move(){
			y++;
			fillCells();
		}
		
		public void moveLeft(){
			x--;
			fillCells();
		}
		
		public void moveRight(){
			x++;
			fillCells();
		}
		
	}
	
//	public class LType extends Tetromino{
//		
//		public LType (int x, int y, d)
//		
//	}
	
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//	}
	
//	public String toString() {
//		return "This is just a regular Tetronimo.";
//	}
	
}
