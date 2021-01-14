
import Data.*;
import Dijkstra.Dijkstra;
import Dijkstra.DoubleV2;
import IsInside.IsInside;
import Jarvis.Jarvis;
import javafx.scene.chart.PieChart.Data;

import java.util.*;

public class Main {

	
	public static void main(String[] args) {

		Dane.read("data/daneTestowe.txt");
		Dane.readPacjent("data/daneTestowePacjenci.txt");



		List<Szpital> result = new ArrayList<Szpital>();
		Jarvis jarvis=new Jarvis();
		result=jarvis.convexHull();


		System.out.println("jarvis wyniki");
		int i=0;
		for (Szpital obiekt : result){
			System.out.println(obiekt.getNazwa());
		}

		Dane.clearObjects();
		Dane.skrzyzowania();
		Dijkstra dijkstra = new Dijkstra(Dane.szpitale);
		DoubleV2 d[] = dijkstra.algorithm(dijkstra.nodes, Dane.drogi, 1);
		System.out.println("\n koniec");
		for (Szpital s : Dane.szpitale) {
			System.out.println(s.getNazwa());
		}
		for (Szpital szpital : Dane.szpitale) {
			System.out.println(szpital.getNazwa() + " odleg³oœæ: " + d[szpital.getId()].value);
		}
		
		Pacjent szpital=new Pacjent(0,9,0);
		IsInside isInside=new IsInside();
		System.out.println(isInside.isInside(result,szpital));
		

	}

}