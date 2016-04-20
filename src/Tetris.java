import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Tetris implements ActionListener, KeyListener {
	private TetrominoFactory.Tetromino focusPiece;
	private TetrisBoard gamePanel;
	private Timer timer = new Timer(400, this);
	private TetrominoFactory tetrominoFactory = new TetrominoFactory();

	
	public void actionPerformed(ActionEvent e) {
		//This if statement fires at initialization.
		if(focusPiece == null){
			focusPiece = tetrominoFactory.newTetronimo(3, 0, TetrominoType.L, gamePanel);
			gamePanel.repaint();
			return;
		}
		//This if statement is for adding new piece after last piece can no longer move.
		if(!focusPiece.move()){
			if(((GameCell)gamePanel.getComponent(4, 0)).getFilled()){
				System.exit(0);
			}
			//Hack-y code to check if filling cells is impossible.
			try {
				focusPiece = tetrominoFactory.newTetronimo(3, 0, TetrominoType.L, gamePanel);
			} catch (NullPointerException a) {
				System.exit(0);
			}
		}
		gamePanel.repaint();
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				focusPiece.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				focusPiece.moveRight();
				break;
			case KeyEvent.VK_UP:
				focusPiece.rotate();
				break;
			case KeyEvent.VK_DOWN:
				focusPiece.move();
				break;
		}
		gamePanel.repaint();
	}
	
	/* abstract methods - do nothing. */
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	private void createAndShowGUI(){
		/* Create the main frame - basically boilerplate */
		JFrame mainFrame = new JFrame("This is the main frame.");
		mainFrame.setPreferredSize(new Dimension(400, 480));
		
		/* Create mainContentPane: a JPanel with BoxLayout aligned along the X-Axis */
		JPanel mainContentPane = new JPanel();
		mainContentPane.setLayout(new BoxLayout(mainContentPane, BoxLayout.X_AXIS));

		/* Set mainContentPane as the content-pane for top-level container mainFrame */
		mainFrame.setContentPane(mainContentPane);
		mainContentPane.setPreferredSize(new Dimension(400, 400));
			
		/* Create and add the game JPanel to the main content pane. 
		 * This panel will have a preferred size of 60% of the main frame. 
		 * This panel will have a Grid Layout because it will deal with tetrominoes
		 * in a basic X, Y graph. */
		this.gamePanel = new TetrisBoard();
		mainFrame.add(gamePanel);
		
		/* Put 10 pixels in between the two columns. */
		mainFrame.add(Box.createRigidArea(new Dimension(10,0)));
		
		/* Create and add the info JPanel to the main content pane.
		 * This panel will have a preferred size of 40% of the main frame.
		 * This panel will have a Box Layout with a single column to hold 
		 * two more JPanels: next piece and score. */
		JPanel infoPanel = new JPanel();
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
	}
	
	public static void main(String args[]) {
		/* The game instance. */
		Tetris tetris = new Tetris();
		tetris.createAndShowGUI();
	
		/*Game logic begins here. */
//		TetrominoFactory tetrominoFactory = new TetrominoFactory();
//		tetris.focusPiece = tetrominoFactory.newTetronimo(3, 0, TetrominoType.I, tetris.gamePanel);
		tetris.gamePanel.addKeyListener(tetris);
		tetris.gamePanel.grabFocus();
		tetris.timer.start();
	}
}
