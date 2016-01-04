
public class SwapPair {
	
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	
	public SwapPair(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean equals(SwapPair otherSwap) {
		return otherSwap != null && x1 == otherSwap.x1 && y1 == otherSwap.y1 && x2 == otherSwap.x2 && y2 == otherSwap.y2;
	}
	
	public String toString() {
		return String.format("%d%d%d%d", x1, y1, x2, y2);
	}
}
