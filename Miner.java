import java.util.List;
import processing.core.*;

public class Miner extends Mover 
{
   private int resource_limit;
   private int resource_count;

    public Miner(String name, List<PImage> imgs, Point position, int rate, int resource_limit,
                  int animation_rate, int resource_count){
      super(name, imgs, position, rate, animation_rate);

      this.resource_limit = resource_limit;
      this.resource_count = resource_count;
   }

   public void setResourceCount(int num){
      this.resource_count = num;
   }

   public int getResourceCount(){
      return this.resource_count;
   }

   public int getResourceLimit(){
      return this.resource_limit;
   }

     
   public Point nextPosition(WorldModel world, Point dest_pt){
      Point e_pt = this.getPosition();
      int horiz = Point.sign(dest_pt.getX() - e_pt.getX());
      Point new_pt = new Point(e_pt.getX() + horiz, e_pt.getY());

      if (horiz == 0 || world.isOccupied(new_pt)){
         int vert = Point.sign(dest_pt.getY() - e_pt.getY());
         new_pt = new Point(e_pt.getX(), e_pt.getY() + vert);

         if (vert == 0 || world.isOccupied(new_pt)){
            new_pt = new Point(e_pt.getX(), e_pt.getY());
         }
      }

      return new_pt;
   }
}
