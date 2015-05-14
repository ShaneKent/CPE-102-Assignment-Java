import processing.core.*;
import java.util.List;

public class Ore extends Occupant{

   private int rate;

   public Ore(String name, List<PImage> imgs, Point position){
      super(name, imgs, position);
      this.rate = 5000;
   }

   public Ore(String name, List<PImage> imgs, Point position, int rate){
      super(name, imgs, position);
      this.rate = rate;
   }

   public int getRate(){
      return this.rate;
   }

   public String entityString(){
      return "ore " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() + " " + this.getRate();
   }
}

