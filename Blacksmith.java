import processing.core.*;
import java.util.List;

public class Blacksmith extends Occupant{
   private int resource_limit;
   private int resource_count;
   private int rate;
   private int resource_distance;

   public Blacksmith(String name, List<PImage> imgs, Point position, int resource_limit, int rate){
      super(name, imgs, position);
      this.resource_limit = resource_limit;
      this.resource_count = 0;
      this.rate = rate;
      this.resource_distance = 1;
   }

   public Blacksmith(String name, List<PImage> imgs, Point position, int resource_limit, int rate, int resource_distance){
      super(name, imgs, position);
      this.resource_limit = resource_limit;
      this.resource_count = 0;
      this.rate = rate;
      this.resource_distance = resource_distance;
   }

   public int getRate(){
      return this.rate;
   }

   public void setResourceCount(int n){
      this.resource_count = n;
   }

   public int getResourceCount(){
      return this.resource_count;
   }

   public int getResourceLimit(){
      return this.resource_limit;
   }

   public int getResourceDistance(){
      return this.resource_distance;
   }

   public String entityString(){
      return "blacksmith " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() + 
         " " + this.getResourceLimit() + " " + this.getRate() + " " + this.getResourceDistance();
   }
}

