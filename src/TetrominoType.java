import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public enum TetrominoType {
	I(Color.cyan), 
	J(Color.blue), 
	L(Color.orange), 
	O(Color.yellow), 
	S(Color.green), 
	T(Color.magenta), 
	Z(Color.red);
	
	final Color color;
	TetrominoType(Color color){
		this.color = color;
	}
	
	private static TetrominoType[] types = new TetrominoType[]{TetrominoType.I, TetrominoType.J,
				TetrominoType.L, TetrominoType.O, TetrominoType.S, TetrominoType.T, TetrominoType.Z};
	private static TetrominoType[] currentArray;
	private static Iterator<TetrominoType> typeIter;
	
	private static void generateNewTypes() {
		currentArray = new TetrominoType[7];
		Random myRandom = new Random();
		for(TetrominoType type : types){
			int nextInt = myRandom.nextInt(7);
			while(currentArray[nextInt] != null){
				nextInt = myRandom.nextInt(7);
			}
			currentArray[nextInt] = type;
		}
	}
	
	public static TetrominoType getNextType(){
		if(typeIter == null){
			generateNewTypes();
			typeIter = Arrays.asList(currentArray).iterator();
		}
		if(typeIter.hasNext()){
			return typeIter.next();
		} else {
			typeIter = null;
			return getNextType();
		}
	}
}


