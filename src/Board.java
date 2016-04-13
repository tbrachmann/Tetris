import javax.swing.JPanel;

public class Board {

//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			System.out.println("Painted first.");
//			g.setColor(Color.BLUE);
//			g.fillRect(0, 0, this.getWidth(),this.getHeight());
//		}
	public int coordConverter(int x, int y) {
		return x + (y*10);
	}
}
