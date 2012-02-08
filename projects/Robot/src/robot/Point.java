package robot;
import static java.lang.Math.*;

class Point {
  private final double x, y;
  public Point(double x, double y) { this.x = x; this.y = y; }

  public double getX() { return x; }
  public double getY() { return y; }

  public double getDistance(Point o) {
    return sqrt( pow(this.x - o.x, 2) +  pow(this.y - o.y, 2));
  }
}

