
import Data.*;
import Dijkstra.Dijkstra;

public class Main {

	
	public static void main(String[] args) {
		Dane data = new Dane();
		data.read("data/daneTestowe.txt");
		for (Szpital s : data.szpitale) {
			System.out.println(s.getId() +" ("+ s.getX()+", "+ s.getY()+ ") nazwa: "+s.getNazwa());
		}
		for (Droga d : data.drogi) {
			System.out.println(d.getId() + " Szpital1: " + d.getIdSzpitala1() + " ("
					+ data.szpitale.get(d.getIdSzpitala1()-1).getX() + " ," + data.szpitale.get(d.getIdSzpitala1()-1).getY()
					+ ") Szpital2: " + d.getIdSzpitala2() + " (" + data.szpitale.get(d.getIdSzpitala2()-1).getX() + " ,"
					+ data.szpitale.get(d.getIdSzpitala2()-1).getY() + ") odleg³oœæ: " + d.getOdlglosc());

		}
		System.out.println("hello world");
		data.skrzyzowania();

		for (Droga d : data.drogi) {
			System.out.println(d.getId() + " Szpital1: " + d.getIdSzpitala1() + " ("
					+ data.szpitale.get(d.getIdSzpitala1()-1).getX() + " ," + data.szpitale.get(d.getIdSzpitala1()-1).getY()
					+ ") Szpital2: " + d.getIdSzpitala2() + " (" + data.szpitale.get(d.getIdSzpitala2()-1).getX() + " ,"
					+ data.szpitale.get(d.getIdSzpitala2()-1).getY() + ") odleg³oœæ: " + d.getOdlglosc());
		}
		for (Szpital s : data.szpitale) {
			System.out.println(s.getId() +" ("+ s.getX()+", "+ s.getY()+ ") nazwa: "+s.getNazwa());
		}
		Dijkstra dijkstra = new Dijkstra(data.szpitale);
		double d[] = dijkstra.algorithm(dijkstra.nodes, data.drogi, 0);
		for(int i=0; i < d.length;i++) {
			System.out.println(d[i]);
		}

	}

}