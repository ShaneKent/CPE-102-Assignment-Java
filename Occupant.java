import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.function.LongConsumer;

public class Occupant extends GridItem{
   
   private List<LongConsumer> pendingActions;
   
   public Occupant(String name, List<PImage> imgs, Point position){
      super(name, imgs, position);
      pendingActions = new ArrayList<LongConsumer>();
   }

   public void removePendingAction(LongConsumer action){
      pendingActions.remove(action);
   }
   
   public void addPendingAction(LongConsumer action){
      pendingActions.add(action);
   }
   
   public List<LongConsumer> getPendingActions(){
      return pendingActions;
   }
   
   public void clearPendingActions(){
      pendingActions = new ArrayList<LongConsumer>();
   }
}
