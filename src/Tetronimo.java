import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class Tetronimo extends JComponent{
	int x, y;
	TetronimoType type;
	JPanel board;
	
	public Tetronimo(){
		super();
	}
	
	public Tetronimo newTetronimo(int x, int y, TetronimoType type, JPanel board) {
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
	
	class IType extends Tetronimo{
		private int x,y;
		private Color color;
		
		public IType(int x, int y, JPanel board){
			setOpaque(true);
//			setPreferredSize(new Dimension(10, 10));
			setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.white),
	                this.getBorder()));
			x = x;
			y = y;
			color = Colors.getNextColor();
			((gameCell) board.getComponent(Tetronimo.coordConverter(x, y))).fill(color);
			((gameCell) board.getComponent(Tetronimo.coordConverter(x+1, y))).fill(color);
			((gameCell) board.getComponent(Tetronimo.coordConverter(x+2, y))).fill(color);
			((gameCell) board.getComponent(Tetronimo.coordConverter(x+3, y))).fill(color);
		};
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
//		public String toString() {
//			return "This is an I-tetronimo!";
//		}
		
	}
	
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//	}
	
//	public String toString() {
//		return "This is just a regular Tetronimo.";
//	}
	
}
