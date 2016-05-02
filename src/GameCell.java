import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class GameCell extends JComponent{
	private int x,y;
	private boolean filled = false;
	public static int count = 0;
	private Color color;
	
	public GameCell() {}
	
	public GameCell(int x, int y) {
		this.x = x;
		this.y = y;
		count++;
		setOpaque(true);
//		setPreferredSize(new Dimension(10, 10));
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                this.getBorder()));
	}
	
	public int getXCoord(){
		return x;
	}
	
	public int getYCoord(){
		return y;
	}
	
	public Color getColor() {
		return color;
	}
	
//	public int getX(){
//		return x;
//	}
//	
//	public int getY() {
//		return y;
//	}
	
	public void empty(){
		filled = false;
	}
	
	public void fill(Color newColor){
		color = newColor;
		filled = true;
	}
	
	public boolean getFilled() {
		return filled;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(filled) {
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
