import processing.core.*;
import java.util.List;

public class Mover extends AnimatedActor
{
   private int rate;
   private int resource_limit;
   private int resource_count;

    public Mover(String name, List<PImage> imgs, Point position, int rate,
                  int animation_rate){
      super(name, imgs, position, animation_rate);

      this.rate = rate;
   }

   public int getRate(){
      return this.rate;
   }
}