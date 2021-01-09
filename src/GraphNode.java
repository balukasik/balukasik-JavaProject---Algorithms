
public class GraphNode {

	private int x;
	private int y;
	private int poprzednik;
	
	public GraphNode(int x, int y, int koszt, int prekursor) {
		this.x = x;
		this.y = y;
		this.poprzednik = prekursor;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPoprzednik() {
		return poprzednik;
	}

	public void setPoprzednik(int poprzednik) {
		this.poprzednik = poprzednik;
	}
	
	
}
