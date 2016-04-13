import java.awt.*;
import javax.swing.*;

public class Tetris {
	
	public static void main(String args[]) {
		/* Create the main frame - basically boilerplate */
		JFrame mainFrame = new JFrame("This is the main frame.");
		mainFrame.setPreferredSize(new Dimension(400, 480));
		
		/* Create mainContentPane: a JPanel with BoxLayout aligned along the X-Axis */
		JPanel mainContentPane = new JPanel() {
			/* Re-sizable components test. */
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.setColor(Color.BLUE);
//				g.fillRect(0, 0, this.getWidth(), this.getHeight());
//			}
		};
		mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.X_AXIS));

		/* Set mainContentPane as the content-pane for top-level container mainFrame */
		mainFrame.setContentPane(mainContentPane);
		mainContentPane.setPreferredSize(new Dimension(400, 400));
			
		/* Create and add the game JPanel to the main content pane. 
		 * This panel will have a preferred size of 60% of the main frame. 
		 * This panel will have a Grid Layout because it will deal with tetrominoes
		 * in a basic X, Y graph. */
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(20, 10));
		mainFrame.add(gamePanel);
		gamePanel.setPreferredSize(new Dimension(240, 480));
		gamePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                gamePanel.getBorder()));
		
		/* Put 10 pixels in between the two columns. */
		mainFrame.add(Box.createRigidArea(new Dimension(10,0)));
		
		/* Create and add the info JPanel to the main content pane.
		 * This panel will have a preferred size of 40% of the main frame.
		 * This panel will have a Box Layout with a single column to hold 
		 * two more JPanels: next piece and score. */
		JPanel infoPanel = new JPanel() {
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				System.out.println("Painted first.");
//				g.setColor(Color.RED);
//				g.fillRect(0, 0, this.getWidth(),this.getHeight());
//			}
		};
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(160, 400));
		infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.blue),
                infoPanel.getBorder()));
		
		/* Create nextPanel: a simple panel that will hold an image of the next tetromino. */
		JPanel nextPanel = new JPanel();
		nextPanel.setPreferredSize(new Dimension(160, 240));
		nextPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.green),
                nextPanel.getBorder()));
		
		/* Create scorePanel: a simple panel that will hold the player's score. */
		JPanel scorePanel = new JPanel();
		scorePanel.setPreferredSize(new Dimension(160, 160));
		scorePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.orange),
                scorePanel.getBorder()));
		infoPanel.add(nextPanel);
		infoPanel.add(Box.createRigidArea(new Dimension(0,10)));
		infoPanel.add(scorePanel);
		mainFrame.add(infoPanel);
		
		
		/* Pack should always come last. - I think. At least it makes sense that you 
		 * pack things into the main frame after you've added them. */
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

		
		/*Game logic begins here. */
	
//		gamePanel.add(tetronimoes.newTetronimo(3, 0, TetronimoType.I));

		/* For loops that populate the game grid: 10 X 20 grid of gameCells: a 
		 * JComponent that holds its xy coordinates and a boolean of whether or not
		 * it is filled. */
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
//				gamePanel.add(tetronimoes.newTetronimo(i, j, TetronimoType.I));
				Component focusPiece = gamePanel.add(new gameCell(i, j));
				System.out.println(((gameCell) focusPiece).count);
				gamePanel.revalidate();
			}
		}
		
		Tetronimo tetronimoes = new Tetronimo();
		Component focusPiece = tetronimoes.newTetronimo(3, 0, TetronimoType.I, gamePanel);
		
		
		System.out.println(gamePanel.getComponentCount());
		System.out.println(gamePanel.getComponent(1).toString());
//		System.out.println(focusPiece.count);

//		JFrame myFrame = new JFrame("This is my frame.");
//		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		JPanel myPanel = new JPanel(new BorderLayout()) {
//			protected void paintComponent(Graphics g) {
//				g.setColor(Color.BLACK);
//				g.fillRect(0, 0, 400, 400);
//			}
//		};
//		myPanel.setPreferredSize(new Dimension(400, 400));
//		myPanel.setOpaque(true);
//		myPanel.add(new Shape(0, 0), BorderLayout.LINE_START);
//		myPanel.add(new Shape(100, 100), BorderLayout.LINE_END);
//		myFrame.setContentPane(myPanel);
//		myFrame.pack();
//		myFrame.setVisible(true);
//		System.out.println(myPanel.getComponentCount());
//		System.out.println(myFrame.getComponentCount());
//		System.out.println(myPanel.getNewX());
//		System.out.println(myPanel.getNewY());
//		myFrame.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e){
//				if(myFrame.contains(e.getPoint())){
//					System.exit(0);
//				}
//			}
//		});
//		final Timer myTimer = new Timer(10, null);
//		myTimer.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(myFrame.x == myFrame.getWidth()) {
//					((Timer)e.getSource()).stop();
//					return;
//				}
//				myFrame.x++;
//				myFrame.y++;
//				myFrame.repaint();
//				System.out.println(myFrame.x);
//			}
//		});
//		myTimer.start();
	}
}
