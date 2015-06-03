import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class Kill extends AnimatedActor 
{
   public Kill(String name, List<PImage> imgs, Point position, int animation_rate){
      super(name, imgs, position, animation_rate);
   }

   public LongConsumer createKillAction(WorldModel world){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         Actions.removeEntity(world, this);
      };
      return action[0];
   }

   public void scheduleKill(WorldModel world, long ticks) {
      Actions.scheduleAnimation(world, this, Actions.KILL_STEPS);
      Actions.scheduleAction(world, this, createKillAction(world), ticks + Actions.KILL_DURATION);
   }
}
