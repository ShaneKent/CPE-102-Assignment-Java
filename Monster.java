import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class Monster extends Mover
{
   private boolean truth = false;
   
   public Monster(String name, List<PImage> imgs, Point position, int rate, int animation_rate){
      super(name, imgs, position, rate, animation_rate);
   }
   
   public Point[] monsterToOreBlob(WorldModel world, OreBlob ob){
      Point e_pt = getPosition();
      Point [] pt = new Point[1];
      if (ob == null){
         pt[0] = e_pt;
         truth = false;
         return pt;
      }

      Point ob_pt = ob.getPosition();
      if (e_pt.adjacent(ob_pt)){
         Actions.removeEntity(world, ob);
         pt[0] = ob_pt;
         truth = true;
         return pt;
      }
      else{
         Point new_p = monsterNextPosition(world, ob_pt);
         Occupant old_e = (Occupant) world.getTileOccupant(new_p);
         if (old_e instanceof OreBlob){
            Actions.removeEntity(world, old_e);
         }
         truth = false;
         return world.moveEntity(this, new_p);
      }
   }

   public Point monsterNextPosition(WorldModel world, Point dest_pt){
      Point start_pt = this.getPosition();
      List<Node> path = AStar(world, world.getTileOccupant(dest_pt).getClass(), start_pt, dest_pt);
      if (!path.isEmpty())
	      return path.get(1).pt;
      else 
	      return start_pt;
   }
   
   public LongConsumer createMonsterAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         Point entity_pt = getPosition();
         OreBlob ob = (OreBlob) world.findNearest(entity_pt, OreBlob.class);
         Point[] tiles = monsterToOreBlob(world, ob);
         long next_time = current_ticks + getRate();
         
         if (truth){//tiles.length == 2){
            Quake quake = Actions.createQuake(world, tiles[0], current_ticks, i_store);
            world.addEntity(quake);
            next_time = current_ticks + getRate() * 2;
         }
         
         Actions.scheduleAction(world, this, createMonsterAction(world, i_store), next_time);
      };
      return action[0];
   }

   public void scheduleMonster(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createMonsterAction(world, i_store),
                              ticks + getRate());
      Actions.scheduleAnimation(world, this);
   }
}