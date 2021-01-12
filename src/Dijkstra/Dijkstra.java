package Dijkstra;

import java.util.ArrayList;

import Data.Droga;
import Data.Szpital;

public class Dijkstra {
	public GraphNode[] nodes;
	public Dijkstra(ArrayList<Szpital> szpitale) {
		GraphNode[] nodes = new GraphNode[szpitale.size()];
		for (int i =0; i< szpitale.size();i++) {
			nodes[i]= new GraphNode(szpitale.get(i).getId(),null,0);
		}
		this.nodes = nodes;
	}
	public double[] algorithm(GraphNode[] nodes, ArrayList<Droga> drogi, int startId) {
		double[] d = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			d[i] = Double.MAX_VALUE;
		}
		d[startId] = 0;
		Kolejka q = new Kolejka();
		for (int i = 0; i < nodes.length; i++) {
			q.wstaw(new QueueNode(nodes[i], d[i]));
		}
		while (q.isNotEmpty()) {
			GraphNode tmp = q.pobierz();
			ArrayList<GraphNode> sasiedzi = new ArrayList<GraphNode>();
			for (Droga droga : drogi) {
				if (droga.getIdSzpitala1() == tmp.getId()) {
					sasiedzi.add( nodes[droga.getIdSzpitala2()-1]);
				} else if (droga.getIdSzpitala2() == tmp.getId()) {
					sasiedzi.add( nodes[droga.getIdSzpitala1()-1]);
				}
			}
			for (GraphNode sasiad : sasiedzi) {
				if (d[sasiad.getId()-1] > (d[startId] + sasiad.getKoszt())) {
					d[sasiad.getId()-1] = d[startId] + sasiad.getKoszt();
					nodes[sasiad.getId()-1].setPoprzednik(nodes[startId]);
				}
			}
			
		}
		return d;
	}
}
