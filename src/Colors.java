import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Colors {
	
	private static final Color[] colors = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.ORANGE, 
			Color.PINK, Color.RED, Color.YELLOW};
	private static Random myRandom = new Random();
	private static int lastInt = myRandom.nextInt();
	
	public static Color getNextColor(){
		int index = myRandom.nextInt(7);
		while(index == lastInt) {
			index = myRandom.nextInt(7);
		}
		return colors[index];
	}
	
}
