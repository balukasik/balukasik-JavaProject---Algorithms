package Dijkstra;
import java.util.ArrayList;

public class Kolejka {

	private ArrayList<QueueNode> lista = new ArrayList<QueueNode>();

	public void wstaw(QueueNode node) {
		lista.add(node);
	}

	public QueueNode pull() {

		if (lista.isEmpty()) {
			return null;
		}
		QueueNode lowest = lista.get(0);
		for (QueueNode node : lista) {
			if (lowest.getPriority() < node.getPriority()) {
				lowest = node;
			}
		}
		lista.remove(lowest);
		return lowest;
	}

	public boolean isEmpty() {
		return lista.isEmpty();
	}

}
