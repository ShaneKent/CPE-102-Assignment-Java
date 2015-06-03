import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class MonsterSpawner extends AnimatedActor 
{   
   public MonsterSpawner(String name, List<PImage> imgs, Point position, int animation_rate){
      super(name, imgs, position, animation_rate);
   }
   
   
   public LongConsumer createSpawnerAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
   {
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         Point open_pt = world.findOpenAround(getPosition(), 2);
         if (open_pt != null){
            Monster monster = Actions.createMonster(world, "ore - " + getName() + " - " + current_ticks, open_pt, 200, current_ticks, i_store);
            world.addEntity(monster);
         }
         Actions.scheduleAction(world, this, createSpawnerAction(world, i_store), current_ticks + 150000);
      };

      return action[0];
   }
   

   public void scheduleSpawner(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store) {
      Actions.scheduleAnimation(world, this);
      Actions.scheduleAction(world, this, createSpawnerAction(world, i_store), ticks + Actions.SPAWNER_ANIMATION_RATE);
   }
}
