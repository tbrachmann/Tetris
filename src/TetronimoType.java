import java.util.Random;

public enum TetronimoType {
	I, J, L, O, S, T, Z;
	
	private final static TetronimoType[] types = new TetronimoType[]{TetronimoType.I, TetronimoType.J,
			TetronimoType.L, TetronimoType.O, TetronimoType.S, TetronimoType.T, TetronimoType.Z};
	private static Random myRandom = new Random();
	private static int lastInt;
	
	public static TetronimoType getNextType(){
		int index = myRandom.nextInt();
		while(index != lastInt) {
			index = myRandom.nextInt(7);
		}
		return types[index];
	}
}


