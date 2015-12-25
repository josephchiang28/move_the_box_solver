
public class Box {

	public int x;
	public int y;
	public int type;
	
	public Box(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public boolean isEmpty() {
		return type == 0;
	}
}
