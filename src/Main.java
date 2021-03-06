
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
			System.out.println(obiekt.getId());
		}
		System.out.println("koniec" );

		Dane.clearObjects();
		Dane.skrzyzowania();
	
		
		Pacjent szpital=new Pacjent(0,400,400);
		IsInside isInside=new IsInside();
		System.out.println(isInside.isInside(result,szpital));
		Szpital nearest = Jarvis.findNearest(szpital);
		System.out.println(nearest.getNazwa());
		
		int[] test = Dijkstra.drogaPacjenta(1);
		for (int t : test) {
			System.out.println(t);
		}
		

	}

}