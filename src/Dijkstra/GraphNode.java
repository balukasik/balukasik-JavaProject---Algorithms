package Dijkstra;

public class GraphNode {
	private int id;
	private int x;
	private int y;
	private GraphNode poprzednik;

	public GraphNode(int id, int x, int y, GraphNode poprzednik) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.poprzednik = poprzednik;
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

	public GraphNode getPoprzednik() {
		return poprzednik;
	}

	public void setPoprzednik(GraphNode poprzednik) {
		this.poprzednik = poprzednik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
