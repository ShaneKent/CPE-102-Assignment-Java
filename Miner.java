import java.util.List;
import processing.core.*;
import java.util.function.LongConsumer;
import java.util.LinkedHashMap;

public abstract class Miner extends Mover 
{
   private int resource_limit;
   private int resource_count;

    public Miner(String name, List<PImage> imgs, Point position, int rate, int resource_limit,
                  int animation_rate, int resource_count){
      super(name, imgs, position, rate, animation_rate);

      this.resource_limit = resource_limit;
      this.resource_count = resource_count;
   }

   public void setResourceCount(int num){
      this.resource_count = num;
   }

   public int getResourceCount(){
      return this.resource_count;
   }

   public int getResourceLimit(){
      return this.resource_limit;
   }

   public Point nextPosition(WorldModel world, Point dest_pt){
      Point start_pt = this.getPosition();
      List<Node> path = AStar(world, world.getTileOccupant(dest_pt).getClass(), start_pt, dest_pt);
      
      if (!path.isEmpty()){
	      return path.get(1).pt;
      }else{
	      return start_pt;
	   }
   }

   protected abstract LongConsumer createMinerAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store);

}
