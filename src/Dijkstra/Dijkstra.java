package Dijkstra;

import java.util.ArrayList;

import Data.Droga;
import Data.Szpital;

public class Dijkstra {
	public ArrayList<GraphNode> nodes;

	public Dijkstra(ArrayList<Szpital> szpitale) {
		this.nodes = new ArrayList<GraphNode>();
		for (int i = 0; i < szpitale.size(); i++) {
			nodes.add(new GraphNode(szpitale.get(i).getId(), null, 0));
		}
	}

	public DoubleV2[] algorithm(ArrayList<GraphNode> nodes, ArrayList<Droga> drogi, int startId) {
		DoubleV2[] d = new DoubleV2[nodes.size() + 1];
		d[0] = new DoubleV2(0);
		for (int i = 1; i < nodes.size() + 1; i++) {
			d[i] = new DoubleV2(Double.MAX_VALUE);
		}
		d[startId].value = 0;
		Kolejka q = new Kolejka();
		for (int i = 0; i < nodes.size(); i++) {
			q.wstaw(new QueueNode(nodes.get(i), d[i + 1]));
		}
		while (q.isNotEmpty()) {
			GraphNode tmp = q.pobierz();
			ArrayList<GraphNode> sasiedzi = new ArrayList<GraphNode>();
			for (Droga droga : drogi) {
				if (droga.getIdSzpitala1() == tmp.getId()) {
					nodes.get(droga.getIdSzpitala2() - 1).setKoszt(droga.getOdlglosc());
					sasiedzi.add(nodes.get(droga.getIdSzpitala2() - 1));
				} else if (droga.getIdSzpitala2() == tmp.getId()) {
					nodes.get(droga.getIdSzpitala1() - 1).setKoszt(droga.getOdlglosc());
					sasiedzi.add(nodes.get(droga.getIdSzpitala1() - 1));
				}
			}
			for (GraphNode sasiad : sasiedzi) {
				if (d[sasiad.getId()].value > (d[tmp.getId()].value + sasiad.getKoszt())) {

					d[sasiad.getId()].value = d[tmp.getId()].value + sasiad.getKoszt();
					nodes.get(sasiad.getId() - 1).setPoprzednik(tmp);
				}
			}

		}
		GraphNode tmp = nodes.get(5);

		while (tmp.getPoprzednik() != null) {
			System.out.println("droga: " + tmp.getPoprzednik().getId());
			tmp = tmp.getPoprzednik();
		}

		return d;
	}
}
