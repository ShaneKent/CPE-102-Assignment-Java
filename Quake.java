import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;

public class Quake extends AnimatedActor 
{
   public Quake(String name, List<PImage> imgs, Point position, int animation_rate){
      super(name, imgs, position, animation_rate);
   }

   public void scheduleQuake(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store) {
      Actions.scheduleAnimation(world, this);
   }
}
