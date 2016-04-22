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
			case Z:
				return new ZType(x, y, board);
			case S:
				return new SType(x, y, board);
			case O:
				return new OType(x, y, board);
			case T:
				return new TType(x, y, board);
			case J:
				return new JType(x, y, board);
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
		protected int pivotIndex;
		protected TetrominoType type;
		protected Color color;
		protected TetrisBoard board;
		protected Coordinate[] occupiedCells;
		
		public Tetromino(int x, int y, TetrisBoard board){
			this.x = x;
			this.y = y;
			this.color = Colors.getNextColor();
			this.board = board;
			setFocusable(true);
			setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white),
	                this.getBorder()));
		}
		
		public boolean move(){
			Coordinate oldCoordinate;
			Coordinate[] newCoordinates = new Coordinate[4];
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates[i] = new Coordinate(oldCoordinate.getX(), oldCoordinate.getY()+1);
			}
			if(this.board.fillCells(newCoordinates, occupiedCells, color)){
				occupiedCells = newCoordinates;
				return true;
			} else {
				return false;
			}
		}
		
		public boolean moveLeft(){
			Coordinate oldCoordinate;
			Coordinate[] newCoordinates = new Coordinate[4];
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates[i] = new Coordinate(oldCoordinate.getX()-1, oldCoordinate.getY());
			}
			if(this.board.fillCells(newCoordinates, occupiedCells, color)){
				occupiedCells = newCoordinates;
				return true;
			} else {
				return false;
			}
		}
		
		public boolean moveRight(){
			Coordinate oldCoordinate;
			Coordinate[] newCoordinates = new Coordinate[4];
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates[i] = new Coordinate(oldCoordinate.getX()+1, oldCoordinate.getY());
			}
			if(this.board.fillCells(newCoordinates, occupiedCells, color)){
				occupiedCells = newCoordinates;
				return true;
			} else {
				return false;
			}
		}
		
		public boolean rotate(){
			Coordinate pivot;
			Coordinate oldCoordinate;
			Coordinate[] newCoordinates = new Coordinate[4];
			pivot = occupiedCells[pivotIndex];
			int newX;
			int newY;
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newX = pivot.getX() + -1*(oldCoordinate.getY() - pivot.getY());
				newY = pivot.getY() + (oldCoordinate.getX() - pivot.getX());
				newCoordinates[i] = new Coordinate(newX, newY);
//				System.out.println("(" + newX + ", " + newY + ")");
			}
			if(this.board.fillCells(newCoordinates, occupiedCells, color)){
				occupiedCells = newCoordinates;
				return true;
			} else {
				return false;
			}
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
	}
	
	public class IType extends Tetromino{
		
		public IType(int x, int y, TetrisBoard board){
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+2, y), new Coordinate(x+3, y)};
			this.pivotIndex = 2;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an I-tetronimo!";
		}
	}
	
	public class ZType extends Tetromino{
		
		public ZType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+1, y+1), new Coordinate(x+2, y+1)};
			this.pivotIndex = 2;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is a Z-tetronimo!";
		}
		
	}
	
	public class SType extends Tetromino{
			
		public SType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x-1, y), new Coordinate(x-1, y+1), new Coordinate(x-2, y+1)};
			this.pivotIndex = 2;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an S-tetronimo!";
		}
		
	}
	
	public class OType extends Tetromino{
		
		public OType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x, y+1), new Coordinate(x+1, y+1)};
			this.pivotIndex = 2;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		//This one doesn't actually rotate
		@Override
		public boolean rotate(){
			return true;
		}
		
		public String toString() {
			return "This is an O-tetronimo!";
		}
		
	}
	
	public class TType extends Tetromino{
		
		public TType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+1, y+1), new Coordinate(x+2, y)};
			this.pivotIndex = 1;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is a T-tetronimo!";
		}
		
	}
	
	public class LType extends Tetromino{
		
		public LType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x, y+1), new Coordinate(x+1, y), new Coordinate(x+2, y)};
			this.pivotIndex = 2;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an L-tetronimo!";
		}
		
	}
	
	public class JType extends Tetromino{
		
		public JType (int x, int y, TetrisBoard board) {
			super(x, y, board);
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+2, y), new Coordinate(x+2, y+1)};
			this.pivotIndex = 1;
			if(!board.fillCells(occupiedCells, null, color)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is a J-tetronimo!";
		}
		
	}
	
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//	}
	
//	public String toString() {
//		return "This is just a regular Tetronimo.";
//	}
	
}
