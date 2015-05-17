import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;

public class MinerNotFull extends Miner{
   
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
         return pt;
      }

      Point o_pt = ore.getPosition();
      if (e_pt.adjacent(o_pt)){
         this.setResourceCount(1 + this.getResourceCount());
         //actions.remove_entity(world, ore);
         pt[0] = o_pt;
         return pt;
      }else{
         Point new_pt = this.nextPosition(world, o_pt);
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

   public static LongConsumer createMinerAction(WorldModel world, ImageStore i_store)
     {
         LongConsumer[] action = { null };
         action[0] = (long current_ticks) -> {
            this.removePendingAction(action);

            Point entity_pt = this.getPosition();
            Ore ore = world.findNearest(entity_pt, Ore);
            Point[] tiles = this.minerToOre(world, ore);
            
            Entity new_entity = this;
            if (tiles.length == 2)
            {
               new_entity = actions.tryTransformMiner(world, this,
                  this.tryTransformMiner);
            } 

            actions.scheduleAction(world, new_entity,
               new_entity.createMinerAction(world, i_store),
               current_ticks + new_entity.getRate());
            return tiles;
         };

         return action[0];
     }
    
   public void scheduleMiner(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAnimation(world, this);
   }
}
