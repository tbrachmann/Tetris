import javax.swing.*;
import java.awt.*;

public class TetrisBoard extends JPanel{

//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			System.out.println("Painted first.");
//			g.setColor(Color.BLUE);
//			g.fillRect(0, 0, this.getWidth(),this.getHeight());
//		}
	public TetrisBoard(){
		super();
		setLayout(new GridLayout(20, 10));
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
//				gamePanel.add(tetronimoes.newTetronimo(i, j, TetronimoType.I));
				Component focusPiece = add(new GameCell(j, i));
//				System.out.println(((gameCell) focusPiece).count);
				revalidate();
			}
		}
	}
	
	private int coordConverter(int x, int y) {
		return x + (y*10);
	}
	
	public Component getComponent(int x, int y) {
		return super.getComponent(coordConverter(x, y));
	}
}
