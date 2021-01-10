
import Data.Dane;
import Dijkstra.Dijkstra;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello world");
		Dane data = new Dane();
		data.read("data/daneTestowe.txt");
		data.skrzyzowania();
		Dijkstra dijkstra = new Dijkstra(data.szpitale);
		double d[] = dijkstra.algorithm(dijkstra.nodes, data.drogi, 0);
		System.out.println(d.toString());

	}

}