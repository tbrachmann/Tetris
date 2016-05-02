public class Coordinate<XCoord, YCoord> {
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public boolean equals(Object i){
		if(((Coordinate) i).getX() == x && ((Coordinate) i).getY() == y){
			return true;
		} else {
			return false;
		}
	}
	
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
	
}
