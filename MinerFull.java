import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class MinerFull extends Miner{
   
   public MinerFull(String name, List<PImage> imgs, Point position, int rate, int resource_limit, int animation_rate){
       super(name, imgs, position, rate, resource_limit, animation_rate, resource_limit);

   }
   
   public Point[] minerToSmith(WorldModel world, Blacksmith smith){
      Point e_pt = this.getPosition();
      Point[] pt = new Point[1];
      
      if (smith == null){
         pt[0] = e_pt;
         return pt;
      }

      Point s_pt = smith.getPosition();
      if (e_pt.adjacent(s_pt)){
         smith.setResourceCount(smith.getResourceCount() + this.getResourceCount());
         this.setResourceCount(0);
         return pt;
      }
      else{
         Point new_pt = this.nextPosition(world, s_pt);
         return world.moveEntity(this, new_pt);
      }
   }


   public LongConsumer createMinerAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
     {
         LongConsumer[] action = { null };
         action[0] = (long current_ticks) -> {
            removePendingAction(action[0]);

            Point entity_pt = this.getPosition();
            Smith smith = (Smith) world.findNearest(entity_pt, Blacksmith.class);
            Point[] tiles = this.minerToSmith(world, smith);
            
            MinerFull new_entity = this;
            if (tiles.length == 2)
            {
               new_entity = (MinerNotFull) Actions.tryTransformMiner(world, this,
                  this::tryTransformMinerNotFull);
            } 

            Actions.scheduleAction(world, new_entity,
               new_entity.createMinerAction(world, i_store),
               current_ticks + new_entity.getRate());
         };

         return action[0];
     }

   public Miner tryTransformMinerFull(WorldModel world){
         Miner m = new MinerNotFull(this.getName(), this.getImages(), this.getPosition(), this.getRate(), this.getResourceLimit(), this.getAnimationRate());
         return m;
   }
}
