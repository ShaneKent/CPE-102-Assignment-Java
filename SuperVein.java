import processing.core.*;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.LinkedHashMap;

public class SuperVein extends Occupant{
   private int rate;
   private int resource_distance;

   public SuperVein(String name, List<PImage> imgs, Point position, int rate){
      super(name, imgs, position);
      this.rate = rate;
      this.resource_distance = 1;
   }

   public SuperVein(String name, List<PImage> imgs, Point position, int rate, int resource_distance){
      super(name, imgs, position);
      this.rate = rate;
      this.resource_distance = resource_distance;
   }

   public int getRate(){
      return this.rate;
   }

   public int getResourceDistance(){
      return this.resource_distance;
   }

   public String entityString(){
      return "vein " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() +
         " " + this.getRate() + " " + this.getResourceDistance();
   }
   
   public LongConsumer createSuperVeinAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
     {
         LongConsumer[] action = { null };
         action[0] = (long current_ticks) -> {
            removePendingAction(action[0]);
            
            Point open_pt = world.findOpenAround(getPosition(), getResourceDistance());
            if (open_pt != null){
               Ore ore = Actions.createOre(world, "ore - " + getName() + " - " + current_ticks, open_pt, current_ticks, i_store);
               world.addEntity(ore);
            }
            Actions.scheduleAction(world, this, createSuperVeinAction(world, i_store), current_ticks + getRate());
         };

         return action[0];
     }
   
   public void scheduleSuperVein(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createSuperVeinAction(world, i_store), ticks + getRate());
   }
}
