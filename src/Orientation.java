public enum Orientation {
	NORTH, SOUTH, EAST, WEST;
	
	public static Orientation getNextOrientation(Orientation o) {
		switch (o){
			case NORTH:
				return EAST;
			case SOUTH:
				return WEST;
			case EAST:
				return SOUTH;
			case WEST:
				return NORTH;
			default:
				return NORTH;
		}
	}
	
}
