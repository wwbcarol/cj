package carol.cj2015.r1A;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class CalHull {
    public ArrayList<Point> solve(ArrayList<Point> ins) {
        ArrayList<Point> ch = new ArrayList<Point>();

        if (ins.size() <= 3) {
            ch.addAll(ins);
        } else {
            // int minInx = -1, maxInx = -1;
            Point minP = ins.get(0);
            Point maxP = ins.get(0);
            for (Point p : ins) {
                if (p.x < minP.x) {
                    minP = p;
                }

                if (p.x > maxP.x) {
                    maxP = p;
                }
            }

            ch.add(minP);
            ch.add(maxP);
            ins.remove(minP);
            ins.remove(maxP);

            ArrayList<Point> l = new ArrayList<Point>();
            ArrayList<Point> r = new ArrayList<Point>();

            for (Point p : ins) {
                if (calLocation(minP, maxP, p) < 0) {
                    l.add(p);
                } else if (calLocation(minP, maxP, p) > 0) {
                    r.add(p);
                }
            }

            hullSet(minP, maxP, r, ch);
            hullSet(maxP, minP, l, ch);
        }

        return ch;
    }

    public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
        int insertPosition = hull.indexOf(B);
        if (set.size() == 0) {
            return;
        }
        if (set.size() == 1) {
            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        int dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++) {
            Point p = set.get(i);

            int ABx = B.x - A.x;
            int ABy = B.y - A.y;
            int distance = ABx * (A.y - p.y) - ABy * (A.x - p.x);
            if (distance < 0) {
                distance = -distance;
            }

            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);

        ArrayList<Point> leftSetAP = new ArrayList<Point>();
        for (Point M : set) {
            if (calLocation(A, P, M) > 0) {
                leftSetAP.add(M);
            }
        }

        ArrayList<Point> leftSetPB = new ArrayList<Point>();
        for (Point M : set) {
            if (calLocation(P, B, M) > 0) {
                leftSetPB.add(M);
            }
        }
        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);

    }

    public static int calLocation(Point A, Point B, Point P) {
        return (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
    }

    public static void main(String args[]) {
        System.out.println("Quick Hull Test");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of points");
        int N = sc.nextInt();

        ArrayList<Point> points = new ArrayList<Point>();
        System.out.println("Enter the coordinates of each points: <x> <y>");
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            Point e = new Point(x, y);
            points.add(i, e);
        }

        CalHull qh = new CalHull();
        ArrayList<Point> p = qh.solve(points);
        System.out.println("The points in the Convex hull using Quick Hull are: ");
        ArrayList<Point> edges = new ArrayList<Point>();
        for (int i = 0; i < p.size(); i++) {
            System.out.println("(" + p.get(i).x + ", " + p.get(i).y + ")");

            for (Point point : points) {
                if (i != 0) {
                    // && (point.x != p.get(i).x && point.y != p.get(i).y)
                    // && (point.x != p.get(i - 1).x && point.y != p.get(i - 1).y)) {
                    if (Line2D.ptSegDist(p.get(i).x, p.get(i).y, p.get(i - 1).x, p.get(i - 1).y, point.x, point.y) == 0) {
                        edges.add(point);
                        // System.out.println("[1 " + p.get(i).x + ", " + p.get(i).y + "]");
                        // System.out.println("[2 " + p.get(i - 1).x + ", " + p.get(i - 1).y + "]");
                        // System.out.println("[P " + point.x + ", " + point.y + "]");
                    }
                }
            }
        }

        for (Point pP : edges) {
            System.out.println("[" + pP.x + ", " + pP.y + "]");
        }
        sc.close();
    }
}
