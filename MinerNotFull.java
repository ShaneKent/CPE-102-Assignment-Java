import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class MinerNotFull extends Miner{
   
   private boolean truth = false;
   
   public MinerNotFull(String name, List<PImage> imgs, Point position, int rate, int resource_limit, int animation_rate){
       super(name, imgs, position, rate, resource_limit, animation_rate, 0);
   }

   public String entityString(){
      return "miner " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() +
         " " + this.getResourceLimit() + " " + this.getRate() + " " + this.getAnimationRate();
   }

   public Point[] minerToOre(WorldModel world, Ore ore){
      Point e_pt = this.getPosition();
      Point[] pt = new Point[1];
      if (ore == null){
         pt[0] = e_pt;
         truth = false;
         return pt;
      }

      Point o_pt = ore.getPosition();
      if (e_pt.adjacent(o_pt)){
         this.setResourceCount(1 + this.getResourceCount());
         Actions.removeEntity(world, ore);
         pt[0] = o_pt;
         truth = true;
         return pt;
      }else{
         Point new_pt = this.nextPosition(world, o_pt);
         truth = false;
         return world.moveEntity(this, new_pt);
      }
   }

   public Miner tryTransformMinerNotFull(WorldModel world)
    {
         if (this.getResourceCount() < this.getResourceLimit()){
            return this;
         }
         else
         {
            Miner m = new MinerFull(this.getName(), this.getImages(), this.getPosition(),
                 this.getRate(), this.getResourceLimit(), this.getAnimationRate());
            return m;
         }  
    }

   public LongConsumer createMinerAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
     {
         LongConsumer[] action = { null };
         action[0] = (long current_ticks) -> {
            removePendingAction(action[0]);

            Point entity_pt = this.getPosition();
            Ore ore = (Ore) world.findNearest(entity_pt, Ore.class);
            Point[] tiles = this.minerToOre(world, ore);
            
            Miner new_entity = this;
            if (truth)//tiles.length == 2)
            {
               new_entity = (Miner) Actions.tryTransformMiner(world, this,
                  this::tryTransformMinerNotFull);
            } 

            Actions.scheduleAction(world, new_entity,
               new_entity.createMinerAction(world, i_store),
               current_ticks + new_entity.getRate());
         };

         return action[0];
     }
    
   public void scheduleMiner(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createMinerAction(world, i_store), ticks + getRate());
      Actions.scheduleAnimation(world, this);
   }
}
