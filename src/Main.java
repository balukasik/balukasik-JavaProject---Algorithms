
import Data.*;
import Dijkstra.Dijkstra;
import Dijkstra.DoubleV2;
import IsInside.IsInside;
import Jarvis.Jarvis;

import java.util.*;

public class Main {

	
	public static void main(String[] args) {
		Dane data = new Dane();
		data.read("data/daneTestowe.txt");
	
		data.skrzyzowania();


		Dijkstra dijkstra = new Dijkstra(data.szpitale);
		DoubleV2 d[] = dijkstra.algorithm(dijkstra.nodes, data.drogi, 1);

		List<Szpital> result = new ArrayList<Szpital>();

		Jarvis jarvis=new Jarvis();
		result=jarvis.convexHull();

		System.out.println("jarvis wyniki");
		int i=0;
		for (Szpital obiekt : result){
			System.out.println(obiekt.getNazwa());
		}

		Pacjent szpital=new Pacjent(0,9,0);
		IsInside isInside=new IsInside();
		System.out.println(isInside.isInside(result,szpital));
		

	}

}