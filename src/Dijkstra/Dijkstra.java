package Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;

import Data.Dane;
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

	private DoubleV2[] algorithm(int startId) {
		DoubleV2[] d = new DoubleV2[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			d[i] = new DoubleV2(Double.MAX_VALUE);
			d[i].id = i + 1;
		}
		d[startId - 1].value = 0;
		Kolejka q = new Kolejka();
		for (int i = 0; i < nodes.size(); i++) {
			q.wstaw(new QueueNode(nodes.get(i), d[i]));
		}
		while (q.isNotEmpty()) {
			GraphNode tmp = q.pobierz();
			ArrayList<GraphNode> sasiedzi = new ArrayList<GraphNode>();
			for (Droga droga : Dane.drogi) {
				if (droga.getIdSzpitala1() == tmp.getId()) {
					nodes.get(droga.getIdSzpitala2() - 1).setKoszt(droga.getOdlglosc());
					sasiedzi.add(nodes.get(droga.getIdSzpitala2() - 1));
				} else if (droga.getIdSzpitala2() == tmp.getId()) {
					nodes.get(droga.getIdSzpitala1() - 1).setKoszt(droga.getOdlglosc());
					sasiedzi.add(nodes.get(droga.getIdSzpitala1() - 1));
				}
			}
			for (GraphNode sasiad : sasiedzi) {
				if (d[sasiad.getId() - 1].value > (d[tmp.getId() - 1].value + sasiad.getKoszt())) {

					d[sasiad.getId() - 1].value = d[tmp.getId() - 1].value + sasiad.getKoszt();
					nodes.get(sasiad.getId() - 1).setPoprzednik(tmp);
				}
			}

		}

		return d;
	}

	public int[] drogaPacjenta(int startId) {
		if (Dane.szpitale.get(startId - 1).getWolne_lozka() > 0) {
			return new int[] { startId };
		}
		int[] cel = new int[Dane.szpitaleSize()];
		cel[0] = startId;
		ArrayList<Integer> trasa = new ArrayList<>();
		trasa.add(startId);
		int next = startId;
		for (int i = 1; i < Dane.szpitaleSize(); i++) {
			Dijkstra dijkstra = new Dijkstra(Dane.szpitale);
			DoubleV2 d[] = dijkstra.algorithm(next);
			Arrays.sort(d);
			for (int j = 0; j < d.length; j++) {
				if (notContains(cel, d[j].id) && Dane.szpitale.get(d[j].id - 1).getLozka() > 0) {
					cel[i] = d[j].id;
					znajdywanieTrasy(trasa, d[j].id, next);
					next = d[j].id;
					break;
				}
			}
			if (Dane.szpitale.get(cel[i] - 1).getWolne_lozka() > 0) {
				int[] result = new int[trasa.size()];
				for (int z = 0; z < result.length; z++) {
					result[z] = trasa.get(z);
				}
				return result;
			}
		}

		return cel;
	}

	private void znajdywanieTrasy(ArrayList<Integer> trasa, int cel, int start) {
		ArrayList<Integer> trasaOdwrotna = new ArrayList<>();
		trasaOdwrotna.add(cel);
		GraphNode tmp = this.nodes.get(cel - 1).getPoprzednik();
		while (tmp != null) {
			if (tmp.getId() != start) {
				trasaOdwrotna.add(tmp.getId());
			}
			tmp = tmp.getPoprzednik();
		}
		for (int i = trasaOdwrotna.size() - 1; i >= 0; i--) {
			trasa.add(trasaOdwrotna.get(i));
		}

	}

	private static boolean notContains(int[] ls, int a) {
		for (int i : ls) {
			if (a == i) {
				return false;
			}
		}
		return true;
	}
}
