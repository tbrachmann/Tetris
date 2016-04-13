import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class gameCell extends JComponent{
	int x,y;
	boolean filled = false;
	public static int count = 0;
	private Color color;
	
	public gameCell(){
		super();
		count++;
	}
	
	public gameCell(int x, int y) {
		x = x;
		y = y;
		count++;
		setOpaque(true);
//		setPreferredSize(new Dimension(10, 10));
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                this.getBorder()));
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
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(filled) {
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
