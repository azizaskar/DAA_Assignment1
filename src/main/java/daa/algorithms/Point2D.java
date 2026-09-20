package daa.algorithms;

public record Point2D(double x, double y) {
    public double distanceTo(Point2D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.hypot(dx, dy);
    }
}