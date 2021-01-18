package IsInside;

import Data.Pacjent;
import Data.Szpital;


public class IsInside {

    private static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= findMax(p.x, r.x) && q.x >= findMin(p.x, r.x) && q.y <= findMax(p.y, r.y) && q.y >= findMin(p.y, r.y)) {
            return true;
        }
        return false;
    }

    private static int orientation(Point p, Point q, Point r) {
        return compare((((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y))), 0);
    }

    private static int compare(double a, double b) {
        if (a > b)
            return 1;
        else if (a < b)
            return 2;
        return 0;
    }

    private static double findMax(double a, double b) {
        if (a > b)
            return a;
        else if (a < b)
            return b;
        return a;
    }

    private static double findMin(double a, double b) {
        if (a > b)
            return b;
        else if (a < b)
            return a;
        return b;
    }

    private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

        int hullHullPacj = orientation(p1, q1, p2);
        int hullHullExtr = orientation(p1, q1, q2);
        int pacjExtrHull1 = orientation(p2, q2, p1);
        int pacjExtrHull2 = orientation(p2, q2, q1);

        if (hullHullPacj != hullHullExtr && pacjExtrHull1 != pacjExtrHull2)
            return true;

        if (hullHullPacj == 0 && onSegment(p1, p2, q1))
            return true;

        if (hullHullExtr == 0 && onSegment(p1, q2, q1))
            return true;

        if (pacjExtrHull1 == 0 && onSegment(p2, p1, q2))
            return true;

        if (pacjExtrHull2 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    public static boolean isInside(Szpital[] points, Pacjent p) {
        int n = points.length;
        Point[] convexHull = new Point[n];
        int j = 0;
        for (Szpital obiekt : points) {
            convexHull[j] = new Point(obiekt.getX(), obiekt.getY());
            j++;
        }
        Point pacjent = new Point();
        pacjent.x = p.getX();
        pacjent.y = p.getY();

        if (n < 3)
            return false;

        Point extreme = new Point(Integer.MAX_VALUE, pacjent.y);

        int count = 0;
        int i = 0;
        while (true) {
            int next = (i + 1) % n;
            if (doIntersect(convexHull[i], convexHull[next], pacjent, extreme)) {
                if (orientation(convexHull[i], pacjent, convexHull[next]) == 0) {
                    return onSegment(convexHull[i], pacjent, convexHull[next]);
                }
                count++;
            }
            i = next;
            if (i == 0) {
                break;
            }
        }

        return count % 2 == 1;
    }

}
