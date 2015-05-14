import processing.core.*;
import java.util.List;

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

   public Miner tryTransformMiner(WorldModel world){
      Miner m = new MinerNotFull(this.getName(), this.getImages(), this.getPosition(), this.getRate(), this.getResourceLimit(), this.getAnimationRate());
      return m;
   }
}
