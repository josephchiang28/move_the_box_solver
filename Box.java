
public class Box {

	public int x;
	public int y;
	public char type;
	
	public Box(int x, int y, char type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public boolean isEmpty() {
		return type == '0';
	}
}
