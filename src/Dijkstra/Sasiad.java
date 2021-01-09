package Dijkstra;


public class Sasiad extends GraphNode {
	
	private double koszt;

	public Sasiad(int id, int x, int y, GraphNode poprzednik,int koszt) {
		super(id, x, y, poprzednik);
		this.setKoszt(koszt);
	}

	public double getKoszt() {
		return koszt;
	}

	public void setKoszt(double koszt) {
		this.koszt = koszt;
	}

}
