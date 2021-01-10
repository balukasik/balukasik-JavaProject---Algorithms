package Dijkstra;

public class GraphNode {
	private int id;
	private GraphNode poprzednik;

	public GraphNode(int id, GraphNode poprzednik) {
		this.id = id;
		this.poprzednik = poprzednik;
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
