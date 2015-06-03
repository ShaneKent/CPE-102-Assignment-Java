import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class MonsterBomb extends AnimatedActor 
{
   private List<PImage> corruptgrass;
   
   public MonsterBomb(String name, List<PImage> imgs, Point position, int animation_rate, List<PImage> corruptgrass){
      super(name, imgs, position, animation_rate);
      this.corruptgrass = corruptgrass;
   }

   public LongConsumer createExplosionAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         int ent_x = this.getPosition().getX();
         int ent_y = this.getPosition().getY();
         
         for (int x = -2; x <= 2; x++){
            for (int y = -2; y <= 2; y++){
               Point pt = new Point(ent_x + x, ent_y + y);
               world.setBackground(pt, new Background("corrupt_grass", corruptgrass));
               
               Entity tile = world.getTileOccupant(pt);
               if (tile != null && tile.getClass() == Vein.class){
                  Actions.removeEntity(world, (Occupant) tile);
                  SuperVein sv = Actions.createSuperVein(world, "supervein - " + current_ticks, pt, current_ticks, i_store, 1);
                  world.addEntity(sv);
               }
               else if (tile != null && tile instanceof Miner){
                  Point tile_pt = ((Occupant)tile).getPosition();
                  Actions.removeEntity(world, (Occupant) tile);
                  Kill kill = Actions.createKill(world, tile_pt, System.currentTimeMillis(), i_store);
                  world.addEntity(kill);
               }
            }
         }
         
         MonsterSpawner ms = Actions.createMonsterSpawner(world, "spawner - " + System.currentTimeMillis(), this.getPosition(), System.currentTimeMillis(), i_store);
         Actions.removeEntity(world, this);
         world.addEntity(ms);
      };
      return action[0];
   }

   public void scheduleBomb(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store) {
      Actions.scheduleAnimation(world, this, Actions.BOMB_STEPS);
      Actions.scheduleAction(world, this, createExplosionAction(world, i_store), ticks + Actions.BOMB_DURATION);
   }
}
