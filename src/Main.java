
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
	
		for(Droga d : Dane.drogi) {
			System.out.println(d.getIdSzpitala1()+" "+ d.getIdSzpitala2()+" "+ d.getOdlglosc());
		}
		Pacjent szpital=new Pacjent(0,9,0);
		IsInside isInside=new IsInside();
		System.out.println(isInside.isInside(result,szpital));
		
		

	}

}