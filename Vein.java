import processing.core.*;
import java.util.List;

public class Vein extends Occupant{
   private int rate;
   private int resource_distance;

   public Vein(String name, List<PImage> imgs, Point position, int rate){
      super(name, imgs, position);
      this.rate = rate;
      this.resource_distance = 1;
   }

   public Vein(String name, List<PImage> imgs, Point position, int rate, int resource_distance){
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
   /*
   public LongConsumer createVeinAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
     {
         LongConsumer[] action = { null };
         action[0] = (long current_ticks) -> {
            removePendingAction(action[0]);

            
         };

         return action[0];
     }
   */
   public void scheduleVein(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createVeinAction(world, i_store), ticks + getRate());
   }
}
