import processing.core.*;
import java.util.List;

public class Obstacle extends GridItem{
   
   public Obstacle(String name, List<PImage> imgs, Point position){
      super(name, imgs, position);
   }
   
   public String entityString(){
      Point p = getPosition();
      return "obstacle " + getName() + " " + p.getX() + " " + p.getY();
   }

}
