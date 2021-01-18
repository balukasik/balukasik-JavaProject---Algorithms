package Jarvis;

import Data.Dane;
import Data.Pacjent;
import Data.Szpital;

import java.util.ArrayList;
import java.util.List;

public class Jarvis {


    public static List<Szpital> convexHull() {
        List<Szpital> points = new ArrayList<>(Dane.szpitale);
        List<Szpital> result = new ArrayList<>();
        Szpital extreme = Utility.findExtreme(points);
        result.add(extreme);
        Szpital p = extreme;
        Szpital q = extreme;


        while (true) {
            Szpital r;
            for (Szpital point : points) {
                if ((point.getX() == p.getX()) && (point.getY() == p.getY())) {
                    continue;
                }
                r = point;
                int turn = Utility.orientation(p, q, r);
                double distance = Utility.compare(Utility.distance(p, r), Utility.distance(p, q));
                if (turn == -1 || turn == 0 && distance == 1) {
                    q = r;
                }
            }
            if ((q.getX() == result.get(0).getX()) && (q.getY() == result.get(0).getY())) {
                break;
            }
            result.add(q);
            p = q;
        }
        return result;
    }

    public static Szpital findNearest(Pacjent pacjent) {

        List<Szpital> szpitale = new ArrayList<>(Dane.szpitale);
        Szpital nearest = szpitale.get(0);
        for (int i = 1; i < szpitale.size(); i++) {
            if (Utility.distance(szpitale.get(i), pacjent) < Utility.distance(nearest, pacjent) && szpitale.get(i).getLozka() > 0) {
                nearest = szpitale.get(i);
            }
        }
        return nearest;


    }


}
