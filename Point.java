import java.lang.Math;

public class Point{
   private int x;
   private int y;

   public Point(int x, int y){
      this.x = x;
      this.y = y;
   }
   
   public int getX(){
      return x;
   }

   public int getY(){
      return y;
   }

   public double distanceSq(Point pt){
      return Math.pow(this.getX() - pt.getX(), 2) + Math.pow(this.getY() - pt.getY(), 2);
   }

   public boolean adjacent(Point pt){
      return ((this.getX() == pt.getX() && Math.abs(this.getY() - pt.getY()) == 1) ||
              (this.getY() == pt.getY() && Math.abs(this.getX() - pt.getX()) == 1));

   }
}
