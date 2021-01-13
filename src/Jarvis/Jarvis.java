package Jarvis;

import java.util.ArrayList;

import Data.Szpital;
import Data.Dane;

import java.util.*;

public class Jarvis {


	public static List<Szpital> convexHull() {
		List<Szpital> points = new ArrayList<>(Dane.szpitale);
		List<Szpital> result = new ArrayList<>();
		Szpital extreme = findExtreme(points);
		result.add(extreme);
		Szpital p = extreme;
		Szpital q = extreme;


		while(true) {
			Szpital r;
			for(int i=0; i<points.size();i++) {
				if((points.get(i).getX() == p.getX()) && (points.get(i).getY() == p.getY())) {
					continue;
				}
				r=points.get(i);
				int turn = orientation(p,q,r);
				double distance = compare(distance(p,r),distance(p,q));
				if(turn == -1 || turn == 0 && distance == 1) {
					q=r;
				}
			}
			if((q.getX() == result.get(0).getX()) && (q.getY() == result.get(0).getY())) {
				break;
			}
			result.add(q);
			p=q;
		}
		return result;
	}

	static Szpital findExtreme(List<Szpital> points) {

		Szpital p = new Szpital(points.get(0));
		for(int i=1;i<points.size();i++) {
			int x = points.get(i).getX();
			int y = points.get(i).getY();
			if(x < p.getX() || (x == p.getX() && y<p.getY())) {
				p=points.get(i);
			}

		}
		return p;
	}

	static int orientation(Szpital p, Szpital q, Szpital r)
	{
		return compare(((q.getX()-p.getX())*(r.getY()-p.getY())) - ((q.getY()-p.getY())*(r.getX()-p.getX())),0);
	}

	static int compare(double a, double b)
	{
		if(a>b)
			return 1;
		else if(a<b)
			return -1;
		return 0;
	}

	static double distance(Szpital p, Szpital q) {
		double dx = q.getX()-p.getX();
		double dy = q.getY()-p.getY();
		return ((dx*dx) + (dy*dy));
	}



}
