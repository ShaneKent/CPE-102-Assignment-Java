import processing.core.*;
import java.util.List;

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

   public Miner tryTransformMiner(WorldModel world){
      if (this.getResourceCount() < this.getResourceLimit()){
         return this;
      }
      else{
         Miner m = new MinerFull(this.getName(), this.getImages(), this.getPosition(),
              this.getRate(), this.getResourceLimit(), this.getAnimationRate());
         return m;
      }
   }
}
