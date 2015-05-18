import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class Ore extends Occupant{

   private int rate;

   public Ore(String name, List<PImage> imgs, Point position){
      super(name, imgs, position);
      this.rate = 5000;
   }

   public Ore(String name, List<PImage> imgs, Point position, int rate){
      super(name, imgs, position);
      this.rate = rate;
   }

   public int getRate(){
      return this.rate;
   }

   public String entityString(){
      return "ore " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() + " " + this.getRate();
   }
   
   public LongConsumer createOreTransformAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         OreBlob blob = Actions.createBlob(world, getName() + " -- blob", getPosition(), 
                                          getRate() / Actions.BLOB_RATE_SCALE, 
                                          current_ticks, i_store);
         Actions.removeEntity(world, this);
         world.addEntity(blob);
      };
      return action[0];
   }

   public void scheduleOre(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createOreTransformAction(world, i_store),
                              ticks + getRate());
   }
}

