import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class Quake extends AnimatedActor 
{
   public Quake(String name, List<PImage> imgs, Point position, int animation_rate){
      super(name, imgs, position, animation_rate);
   }

   public LongConsumer createDeathAction(WorldModel world){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         Point pt = getPosition();
         Actions.removeEntity(world, this);
      };
      return action[0];
   }

   public void scheduleQuake(WorldModel world, long ticks) {
      Actions.scheduleAnimation(world, this, Actions.QUAKE_STEPS);
      Actions.scheduleAction(world, this, createDeathAction(world), ticks + Actions.QUAKE_DURATION);
   }
}
