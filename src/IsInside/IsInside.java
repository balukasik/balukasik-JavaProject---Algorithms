package IsInside;

import Data.Pacjent;
import Data.Szpital;


import java.util.List;



public class IsInside {

    public static boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }

    public static int orientation(Point p, Point q, Point r)
    {
        return compare((((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y))),0);
    }

    static int compare(double a, double b)
    {
        if(a>b)
            return 1;
        else if(a<b)
            return -1;
        return 0;
    }

    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    public static boolean isInside(List<Szpital> points, Pacjent p)
    {
        int n=points.size();
        Point convexHull[]=new Point[n];
        int j=0;
        for(Szpital obiekt : points){
            convexHull[j]=new Point(obiekt.getX(),obiekt.getY());
            j++;
        }
        Point pacjent=new Point();
        pacjent.x=p.getX();
        pacjent.y=p.getY();

        if (n < 3)
            return false;

        Point extreme = new Point(Integer.MAX_VALUE, pacjent.y);

        int count = 0;
        int i = 0;
        while(true) {
            int next = (i + 1) % n;
            if (doIntersect(convexHull[i], convexHull[next], pacjent, extreme))
            {
                if (orientation(convexHull[i], pacjent, convexHull[next]) == 0)
                    return onSegment(convexHull[i], pacjent, convexHull[next]);

                count++;
            }
            i = next;
            if(i == 0){
                break;
            }
        }

        return count%2 == 1;
    }

}
