import java.util.Random;

public enum TetrominoType {
	I, J, L, O, S, T, Z;
	
	private final static TetrominoType[] types = new TetrominoType[]{TetrominoType.I, TetrominoType.J,
			TetrominoType.L, TetrominoType.O, TetrominoType.S, TetrominoType.T, TetrominoType.Z};
	private static Random myRandom = new Random();
	private static int lastInt;
	
	public static TetrominoType getNextType(){
		int index = myRandom.nextInt(7);
		return types[index];
	}
}


