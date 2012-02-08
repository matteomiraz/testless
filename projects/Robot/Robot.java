package robot;
import static java.lang.Math.*;

public class Robot {

  /** posizione del robot nel piano*/
  private Point pos;

  /** orientamento del robot in gradi (0-360) */
  private double degree = 0;

  /** batteria del robot (0-100) */
  private double battery = 100;

  private Robot(double x, double y) { pos = new Point(x, y); }
  
  public static Robot getR1() { return new Robot( 0,  0); }
  public static Robot getR2() { return new Robot(99, 99); }

  /** Ricarica il robot se è su una stazione di ricarica */
  public boolean recharge() {
    if(pos.getDistance(new Point( 0, 33)) < 1 || pos.getDistance(new Point( 0, 66)) < 1 ||
       pos.getDistance(new Point(33,  0)) < 1 || pos.getDistance(new Point(33, 99)) < 1 ||
       pos.getDistance(new Point(66,  0)) < 1 || pos.getDistance(new Point(66, 99)) < 1 ||
       pos.getDistance(new Point(99, 33)) < 1 || pos.getDistance(new Point(99, 66)) < 1) {

      this.battery = 100; return true;
    }

    return false;
  }

  /** Cambia l'orientamento del robot */
  public void turn(double d) {
    if(d < -360 || d > 360) return;

    degree += d;
    while(degree <   0) degree += 360;
    while(degree > 360) degree -= 360;
  }

  /** Sposta avanti il robot di r unità (secondo l'orientamento corrente) */
  public boolean move(double r) {
    if(r < 0 || battery < 3*r) return false;
    
    Point t = new Point(pos.getX()+r*cos(toRadians(degree)), pos.getY()+r*sin(toRadians(degree)));
    if(t.getX() < 0 || t.getX() > 99 || t.getY() < 0 || t.getY() > 99) return false;
    
    battery -= 3*r; pos=t; return true;
  }

  /** Spara ad un altro robot con la potenza indicata */
  public boolean fire(double power, Robot other) {
    if(power <= 0 || power > battery) return false;

    double distance = pos.getDistance(other.pos);
    if(distance < 0.1) return false;

    if(power < 4*distance) return false;
    battery -= power; return true;
  }

  @Override
  public String toString() {
    return String.format("x:%5.2f y:%5.2f h:%6.2f b:%5.2f", pos.getX(), pos.getY(), degree, battery);
  }
}

class Point {
  private final double x, y;
  public Point(double x, double y) { this.x = x; this.y = y; }

  public double getX() { return x; }
  public double getY() { return y; }

  public double getDistance(Point o) {
    return sqrt( pow(this.x - o.x, 2) +  pow(this.y - o.y, 2));
  }
}

