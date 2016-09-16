import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TetrominoFactory extends JComponent{
	int x, y;
	TetrisBoard board;
	
	public TetrominoFactory(){
		super();
	}
	
	public Tetromino newTetromino(TetrominoType type, TetrisBoard board) {
		x = board.getColumns()/3;
		y = 0;
		board = board;
		Color color = type.color;
		switch (type) {
			case I:
				return new IType(x, y, color, board);
			case Z:
				return new ZType(x, y, color, board);
			case S:
				return new SType(x, y, color, board);
			case O:
				return new OType(x, y, color, board);
			case T:
				return new TType(x, y, color, board);
			case J:
				return new JType(x, y, color, board);
			case L:
				return new LType(x, y, color, board);
			default:
				return new IType(x, y, color, board);
		}
	}
	
	public Tetromino addToBoard(Tetromino t, TetrisBoard board) {
		return this.newTetromino(t.getType(), board);
	}
	
	public abstract class Tetromino extends JComponent {
		protected int x,y;
		protected int pivotIndex;
		protected TetrominoType type;
		protected Color color;
		protected TetrisBoard board;
		protected Coordinate[] occupiedCells;
		
		public Tetromino(int x, int y, Color color, TetrisBoard board){
			this.x = x;
			this.y = y;
			this.board = board;
			this.color = color;
			setFocusable(true);
			setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white),
	                this.getBorder()));
		}
		
		public void remove() {
			board.emptyCells(new ArrayList<Coordinate>(Arrays.asList(occupiedCells)));
		}
		
		public boolean move(){
			Coordinate oldCoordinate;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates.put(new Coordinate(oldCoordinate.getX(), oldCoordinate.getY()+1), color);
			}
			if(this.board.fillCells(newCoordinates, occupiedCells)){
				occupiedCells = newCoordinates.keySet().toArray(occupiedCells);
				return true;
			} else {
				return false;
			}
		}
		
		public boolean moveLeft(){
			Coordinate oldCoordinate;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates.put(new Coordinate(oldCoordinate.getX()-1, oldCoordinate.getY()), color);
			}
			if(this.board.fillCells(newCoordinates, occupiedCells)){
				occupiedCells = newCoordinates.keySet().toArray(occupiedCells);
				return true;
			} else {
				return false;
			}
		}
		
		public boolean moveRight(){
			Coordinate oldCoordinate;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newCoordinates.put(new Coordinate(oldCoordinate.getX()+1, oldCoordinate.getY()), color);
			}
			if(this.board.fillCells(newCoordinates, occupiedCells)){
				occupiedCells = newCoordinates.keySet().toArray(occupiedCells);
				return true;
			} else {
				return false;
			}
		}
		
		public boolean rotate(){
			Coordinate pivot;
			Coordinate oldCoordinate;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			pivot = occupiedCells[pivotIndex];
			int newX;
			int newY;
			for(int i = 0; i < 4; i++){
				oldCoordinate = occupiedCells[i];
				newX = pivot.getX() + -1*(oldCoordinate.getY() - pivot.getY());
				newY = pivot.getY() + (oldCoordinate.getX() - pivot.getX());
				newCoordinates.put(new Coordinate(newX, newY), color);
//				System.out.println("(" + newX + ", " + newY + ")");
			}
			if(this.board.fillCells(newCoordinates, occupiedCells)){
				occupiedCells = newCoordinates.keySet().toArray(occupiedCells);
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
		
		public Coordinate[] getCells(){
			return occupiedCells;
		}
		
		public Coordinate getPivot(){
			return occupiedCells[pivotIndex];
		}
		
	}
	
	public class IType extends Tetromino{
		
		public IType(int x, int y, Color color, TetrisBoard board){
			super(x, y, color, board);
			this.type = TetrominoType.I;
			this.occupiedCells = new Coordinate[]{new Coordinate(x-1, y), new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+2, y)};
			this.pivotIndex = 2;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			//exception is here
			if(!board.fillCells(newCoordinates, null)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an I-tetronimo!";
		}
	}
	
	public class ZType extends Tetromino{
		
		public ZType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.Z;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+1, y+1), new Coordinate(x+2, y+1)};
			this.pivotIndex = 2;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is a Z-tetronimo!";
		}
		
	}
	
	public class SType extends Tetromino{
			
		public SType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.S;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y+1), new Coordinate(x+1, y+1), new Coordinate(x+1, y), new Coordinate(x+2, y)};
			this.pivotIndex = 2;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an S-tetronimo!";
		}
		
	}
	
	public class OType extends Tetromino{
		
		public OType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.O;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x, y+1), new Coordinate(x+1, y+1)};
			this.pivotIndex = 2;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
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
		
		public TType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.T;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+1, y+1), new Coordinate(x+2, y)};
			this.pivotIndex = 1;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is a T-tetronimo!";
		}
		
	}
	
	public class LType extends Tetromino{
		
		public LType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.L;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x, y+1), new Coordinate(x+1, y), new Coordinate(x+2, y)};
			this.pivotIndex = 2;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
				throw new NullPointerException();
			}
		}
		
		public String toString() {
			return "This is an L-tetronimo!";
		}
		
	}
	
	public class JType extends Tetromino{
		
		public JType (int x, int y, Color color, TetrisBoard board) {
			super(x, y, color, board);
			this.type = TetrominoType.J;
			this.occupiedCells = new Coordinate[]{new Coordinate(x, y), new Coordinate(x+1, y), new Coordinate(x+2, y), new Coordinate(x+2, y+1)};
			this.pivotIndex = 1;
			LinkedHashMap<Coordinate, Color> newCoordinates = new LinkedHashMap<Coordinate, Color>();
			for(Coordinate coord : occupiedCells) {
				newCoordinates.put(coord, color);
			}
			if(!board.fillCells(newCoordinates, null)) {
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
