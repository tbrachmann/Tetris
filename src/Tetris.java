import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Tetris implements ActionListener, KeyListener {
	
	public static JFrame mainFrame = new JFrame("This is the main frame.");
	private static JButton playButton = new JButton("Play");

	private TetrominoFactory.Tetromino focusPiece;
	private TetrominoFactory.Tetromino nextPiece;
	private TetrominoFactory.Tetromino holdPiece;
	private TetrisBoard gamePanel;
	private TetrisBoard nextBoard;
	private TetrisBoard holdBoard;
	private Timer timer = new Timer(500, this);
	private TetrominoFactory tetrominoFactory = new TetrominoFactory();
	//why did I need this?
	private boolean switched = false;

	public void actionPerformed(ActionEvent e) {
		//Action command is the name of the button
		if(e.getActionCommand() == "Play"){
			createAndShowGameScreen();
		}
		//This if statement fires at initialization.
		if(focusPiece == null){
			focusPiece = tetrominoFactory.newTetromino(TetrominoType.getNextType(), gamePanel);
			nextPiece = tetrominoFactory.newTetromino(TetrominoType.getNextType(), nextBoard);
		}
		//This else if statement is for adding new piece after last piece can no longer move.
		else if(!focusPiece.move()){
			//Hack-y code to check if filling cells is impossible.
			gamePanel.getRowsToRemove(focusPiece.getCells());
			try {
				focusPiece = tetrominoFactory.addToBoard(nextPiece, gamePanel);
				//Throws a null pointer if it can't be added? Where though?!
			} catch (NullPointerException a) {
				/* Game over is here */
				System.exit(0);
			}
			//Removes next piece from its holding spot in nextBoard
			nextPiece.remove();
			//Adds the next piece 
			nextPiece = tetrominoFactory.newTetromino(TetrominoType.getNextType(), nextBoard);
			switched = false;
		}
		gamePanel.repaint();
		nextBoard.repaint();
		holdBoard.repaint();
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
			case KeyEvent.VK_SPACE:
				if(timer.isRunning()){
					timer.stop();
				} else {
					timer.start();
				}
			case KeyEvent.VK_SHIFT:
				/* if there's no piece already being held. */
				if(holdPiece == null){
					TetrominoFactory.Tetromino intermediatePiece;
					focusPiece.remove();
					nextPiece.remove();
					intermediatePiece = tetrominoFactory.addToBoard(focusPiece, holdBoard);
					try {
						focusPiece = tetrominoFactory.addToBoard(nextPiece, gamePanel);
					} catch (NullPointerException f) {
						/* Game over is here. */
						System.exit(0);
					}
					switched = true;
					holdPiece = intermediatePiece;
					nextPiece = tetrominoFactory.newTetromino(TetrominoType.getNextType(), nextBoard);
				/* if the piece has NOT already been switched (you're only allowed one switch per piece) */	
				} else if (!switched){
					TetrominoFactory.Tetromino intermediatePiece;
					focusPiece.remove();
					holdPiece.remove();
					intermediatePiece = focusPiece;
					try {
						focusPiece = tetrominoFactory.addToBoard(holdPiece, gamePanel);
						//Throws null if the hold piece cannot be added to board and still results in a loss
					} catch (NullPointerException g) {
						/* Game over is here */
						System.exit(0);
					}
					holdPiece = tetrominoFactory.addToBoard(intermediatePiece, holdBoard);
					switched = true;
				}
		}
		gamePanel.repaint();
		nextBoard.repaint();
		holdBoard.repaint();
	}
	
	/* abstract methods - do nothing. */
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	/* This is an attempt to add a menu.*/
	/* Where do I create and show the game screen?*/
	private void createAndShowTitleScreen(){
		/* Set content pane and layout. */
		JPanel titleScreen = new JPanel();
		titleScreen.setPreferredSize(new Dimension(400, 400));
		titleScreen.setLayout(new BoxLayout(titleScreen, BoxLayout.X_AXIS));
		
		/* Set titleScreen as mainContentPane. */
		mainFrame.setContentPane(titleScreen);
		
		/* Make button. */
		/* Its made at the top... */
		
		/* Add button to titleScreen. */
		titleScreen.add(playButton);
		
		/* Where to put listeners? */
		playButton.addActionListener(this);
		
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	public void createAndShowGameScreen(){
		
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
		this.gamePanel = new TetrisBoard(20, 10);
		mainFrame.add(gamePanel);
		
		/* Put 10 pixels in between the two columns. */
//		mainFrame.add(Box.createRigidArea(new Dimension(10,0)));
		
		/*Create horizPanel: an intermediate panel that just helps out
		 * with layout - making sure that next, hold, and score maintain
		 * the correct width. 160x480 with 2 rigid areas - 32x480.
		 */
		JPanel horizPanel = new JPanel();
		horizPanel.setLayout(new BoxLayout(horizPanel, BoxLayout.X_AXIS));
		horizPanel.setPreferredSize(new Dimension(160, 480));
		horizPanel.add(Box.createRigidArea(new Dimension(32, 480)));
		horizPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.blue),
                horizPanel.getBorder()));
		
		/* Create and add the info JPanel to the main content pane.
		 * This panel will have a preferred size of 40% of the main frame.
		 * This panel will have a Box Layout with a single column to hold 
		 * three more JPanels: next piece, hold piece, and score. */
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(160, 480));
		
		/* Create nextPanel: a panel with a vertical BoxLayout that will hold a label saying 
		 * "NEXT", a TetrisBoard nextBoard that will hold an image of the next Tetromino, and
		 * some padding beneath the board representation. width: all 120 with padding of 20 on both sides height: 20/80/20. */
		JPanel nextPanel = new JPanel();
		nextPanel.setLayout(new BoxLayout(nextPanel, BoxLayout.Y_AXIS));
		nextPanel.setPreferredSize(new Dimension(96, 160));
		JLabel nextLabel = new JLabel("Next", JLabel.CENTER);
		nextLabel.setPreferredSize(new Dimension(96, 56));
		nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.nextBoard = new TetrisBoard(2, 4);
		nextBoard.setPreferredSize(new Dimension(96, 48));
		nextPanel.add(nextLabel);
		nextPanel.add(nextBoard);
		nextPanel.add(Box.createRigidArea(new Dimension(96, 39)));
		nextPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                nextPanel.getBorder()));

				
		/* Create holdPanel: a panel with the same layout as nextPanel, which will hold the 
		 * currently held piece. Switch it to the main game board with the shift key. */
		JPanel holdPanel = new JPanel();
		holdPanel.setLayout(new BoxLayout(holdPanel, BoxLayout.Y_AXIS));
		holdPanel.setPreferredSize(new Dimension(96, 160));
		JLabel holdLabel = new JLabel("Hold", JLabel.CENTER);
		holdLabel.setPreferredSize(new Dimension(96, 56));
		holdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.holdBoard = new TetrisBoard(2, 4);
		holdBoard.setPreferredSize(new Dimension(96, 48));
		holdPanel.add(holdLabel);
		holdPanel.add(holdBoard);
		holdPanel.add(Box.createRigidArea(new Dimension(96, 39)));
		holdPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                holdPanel.getBorder()));
		
		/* Create scorePanel: a simple panel that will hold the player's score. */
		JPanel scorePanel = new JPanel();
		scorePanel.setPreferredSize(new Dimension(96, 160));
		scorePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                scorePanel.getBorder()));
		
		/* Add nextPanel, holdPanel, and scorePanel to infoPanel, 
		 * add infoPanel to horizPanel, and then add horizPanel to 
		 * the mainFrame. */
		infoPanel.add(nextPanel);
		infoPanel.add(holdPanel);
		infoPanel.add(scorePanel);
		horizPanel.add(infoPanel);
		horizPanel.add(Box.createRigidArea(new Dimension(32, 480)));
		mainFrame.add(horizPanel);
				
		/* Pack should always come last. - I think. At least it makes sense that you 
		 * pack things into the main frame after you've added them. */
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
//		System.out.println(nextLabel.getWidth());
//		System.out.println(nextLabel.getHeight());
		
		gamePanel.addKeyListener(this);
		gamePanel.grabFocus();
		timer.start();
	}
	
	public static void main(String[] args) {
		/* Configure the main frame */
		mainFrame.setPreferredSize(new Dimension(400, 480));
		
		/* Create a tetris and then call tetris.createAndShowTitleScreen */
		Tetris tetris = new Tetris();
		/* This sets the title screen as the main content pane. */
		tetris.createAndShowTitleScreen();	
		
		/* Game over? createAndShowGameOverScreen */
	}
	
	
}
