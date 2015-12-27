
public class SwapPair {

	public Box box1;
	public Box box2;
	
	public SwapPair(Box box1, Box box2) {
		this.box1 = box1;
		this.box2 = box2;
	}
	
	public String toString() {
		return String.format("%1$d%2$d%3$d%4$d", box1.x, box1.y, box2.x, box2.y);
	}
}
