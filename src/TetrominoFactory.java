import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

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
			case L:
				return new LType(x, y, board);
			default:
				return new IType(x, y, board);
		}
		//sets location in gamePanel - coordConverter does not yet exist
		//gamePanel.coordConverter(x, y);
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
			setFocusable(true);
			setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white),
	                this.getBorder()));
			if(!fillCells()) {
				throw new NullPointerException();
			}
		}
		
		protected boolean fillCells(){
//			final long startTime = System.nanoTime();
			GameCell cell;
			GameCell[] newCells = new GameCell[4];
			int newX = 0;
			int newY = 0;
			for (int i = 0; i < 4; i++){
				newX = getNewX(i);
				newY = getNewY(i);
				if(newX < 0 || newX > 9){
					return false;
				} else if(newY < 0 || newY > 19){
					return false;
				}
				try {
					cell = (GameCell)board.getComponent(newX, newY);
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}
				if(cell.getFilled() && !Arrays.asList(occupiedCells).contains(cell)) {
					return false;
				} else {
					newCells[i] = cell;
				}
			}
			for(GameCell oldCell : occupiedCells){
				if(oldCell != null){
					oldCell.empty();
				}
			}
			occupiedCells = newCells;
			for(GameCell newCell : occupiedCells) {
				newCell.fill(color);
			}
//			final long endTime = System.nanoTime();
//			System.out.println("Execution time: " + (endTime - startTime));
			return true;
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
		
		protected abstract int getNewX(int i);
		protected abstract int getNewY(int i);
		public abstract boolean rotate();
		public abstract boolean move();
		public abstract boolean moveLeft();
		public abstract boolean moveRight();
	}
	
	public class IType extends Tetromino{
		
		public IType(int x, int y, TetrisBoard board){
			super(x, y, board);
		}
		
		public String toString() {
			return "This is an I-tetronimo!";
		}
		
		protected int getNewX(int i){
			switch(i){
				default:
					return x;
				case 1:
					switch(myOrientation){
						default: return x-1;
						case SOUTH: return x-1;
						case EAST: return x;
						case WEST: return x;
					}
				case 2:
					switch(myOrientation){
						default: return x+1;
						case SOUTH: return x+1;
						case EAST: return x;
						case WEST: return x;
					}
				case 3:
					switch(myOrientation){
						default: return x+2;
						case SOUTH: return x+2;
						case EAST: return x;
						case WEST: return x;
					}
			}
		}
		
		protected int getNewY(int i){
			switch(i){
				default:
					return y;
				case 1:
					switch(myOrientation){
						default: return y;
						case SOUTH: return y;
						case EAST: return y-1;
						case WEST: return y-1;
					}
				case 2:
					switch(myOrientation){
						default: return y;
						case SOUTH: return y;
						case EAST: return y+1;
						case WEST: return y+1;
					}
				case 3:
					switch(myOrientation){
						default: return y;
						case SOUTH: return y;
						case EAST: return y+2;
						case WEST: return y+2;
					}
			}
		}
		
		public boolean rotate(){
//			if(x%10 > 7 || x%10 == 0) {
//				return false;
//			}
			Orientation oldOrientation = myOrientation;
			myOrientation = Orientation.getNextOrientation(myOrientation);
			if(!fillCells()){
				myOrientation = oldOrientation;
				return false;
			}
//			System.out.println(myOrientation);
			return true;
		}
		
		public boolean move(){
			y++;
			if(!fillCells()){
				y--;
				return false;
			}
			return true;
		}
		
		public boolean moveLeft(){
//			if(x%10 == 0) {
//				return false;
//			}
			x--;
			if(!fillCells()){
				x++;
				return false;
			}
			return true;
		}
		
		public boolean moveRight(){
//			if(myOrientation == Orientation.NORTH || myOrientation == Orientation.SOUTH) {
//				if((x+3)%10 == 9) return false;
//			} else if(myOrientation == Orientation.EAST || myOrientation == Orientation.WEST) {
//				if(x%10 == 9) return true;
//			}
			x++;
			if(!fillCells()){
				x--;
				return false;
			}
			return true;
		}
		
	}
	
	public class LType extends Tetromino{
		
		public LType (int x, int y, TetrisBoard board) {
			super(x, y, board);
		}
		
		protected int getNewX(int i) {
			switch(i){
				default:
					return x;
				case 1:
					switch(myOrientation){
						default: return x-1;
						case EAST: return x;
						case WEST: return x;
					}
				case 2:
					switch(myOrientation){
						default: return x;
						case EAST: return x-1;
						case WEST: return x-1;
					}
				case 3:
					switch(myOrientation){
						default: return x+1;
						case EAST: return x-1;
						case WEST: return x-1;
					}
			}
		}
		
		protected int getNewY(int i) {
			switch(i){
				default:
					switch(myOrientation){
						default: return y;
						case EAST: return y+1;
						case WEST: return y+1;
					}
				case 1:
					return y;
				case 2:
					return y+1;
				case 3:
					switch(myOrientation){
						default: return y+1;
						case EAST: return y+2;
						case WEST: return y+2;
					}
			}
		}
		
		public boolean move(){
			y++;
			if(!fillCells()){
				y--;
				return false;
			}
			return true;
		}
		
		public boolean rotate(){
//			if((x-1)%10 == 0 || x%10 == 9){
//				return false;
//			}
			Orientation oldOrientation = myOrientation;
			myOrientation = Orientation.getNextOrientation(myOrientation);
			if(!fillCells()){
				myOrientation = oldOrientation;
				return false;
			}
//			System.out.println(myOrientation);
			return true;
		}
		
		public boolean moveLeft(){
//			if((x-1)%10 == 0){
//				return false;
//			}
			x--;
			if(!fillCells()){
				x++;
				return false;
			}
			return true;
		}
		
		public boolean moveRight(){
//			if((x+1)%10 == 9 && (myOrientation == Orientation.NORTH || myOrientation == Orientation.NORTH)){
//				return false;
//			} else if(x%10 == 9){
//				return false;
//			}
			x++;
			if(!fillCells()){
				x--;
				return false;
			}
			return true;
		}
		
	}
	
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//	}
	
//	public String toString() {
//		return "This is just a regular Tetronimo.";
//	}
	
}
