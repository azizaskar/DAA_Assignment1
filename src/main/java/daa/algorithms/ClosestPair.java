package daa.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {

    public static double findClosestPair(Point2D[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least 2 points are required");
        }
        Point2D[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(Point2D::x));
        return closestPairRec(pointsByX, 0, pointsByX.length - 1);
    }

    private static double closestPairRec(Point2D[] pts, int left, int right) {
        int n = right - left + 1;
        if (n <= 3) {
            return bruteForce(pts, left, right);
        }

        int mid = left + (right - left) / 2;
        Point2D midPoint = pts[mid];

        double deltaLeft = closestPairRec(pts, left, mid);
        double deltaRight = closestPairRec(pts, mid + 1, right);
        double delta = Math.min(deltaLeft, deltaRight);

        // Орталық сызықтың 2*delta жолағындағы нүктелерді жинау
        List<Point2D> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(pts[i].x() - midPoint.x()) < delta) {
                strip.add(pts[i]);
            }
        }

        // Жолақты y координатасы бойынша сұрыптау
        strip.sort(Comparator.comparingDouble(Point2D::y));

        // Әр нүкте үшін келесі 7 нүктені ғана тексеру
        for (int i = 0; i < strip.size(); i++) {
            Point2D p1 = strip.get(i);
            for (int j = i + 1; j < strip.size() && (strip.get(j).y() - p1.y()) < delta && (j - i) <= 7; j++) {
                double dist = p1.distanceTo(strip.get(j));
                if (dist < delta) {
                    delta = dist;
                }
            }
        }

        return delta;
    }

    public static double bruteForce(Point2D[] pts) {
        return bruteForce(pts, 0, pts.length - 1);
    }

    public static double bruteForce(Point2D[] pts, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = pts[i].distanceTo(pts[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }
}