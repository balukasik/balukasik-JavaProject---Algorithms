package Dijkstra;


public class Sasiad extends GraphNode {
	
	private double koszt;

	public Sasiad(int id, GraphNode poprzednik,int koszt) {
		super(id, poprzednik);
		this.setKoszt(koszt);
	}

	public double getKoszt() {
		return koszt;
	}

	public void setKoszt(double koszt) {
		this.koszt = koszt;
	}

}
