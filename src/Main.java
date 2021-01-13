
import Data.*;
import Dijkstra.Dijkstra;
import Dijkstra.DoubleV2;

public class Main {

	
	public static void main(String[] args) {
		Dane data = new Dane();
		data.read("data/daneTestowe.txt");
	
		data.skrzyzowania();

		Dijkstra dijkstra = new Dijkstra(data.szpitale);
		DoubleV2 d[] = dijkstra.algorithm(dijkstra.nodes, data.drogi, 1);
		

	}

}