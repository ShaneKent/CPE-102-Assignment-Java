import processing.core.*;
import java.util.List;
public class Background extends Entity{
   
   private boolean monsterTouching = false;
   
   public Background(String name, List<PImage> imgs){
      super(name, imgs);
   }
   
   public void setTouching(boolean touch){
      this.monsterTouching = touch;
   }
   
   public boolean getTouching(){
      return monsterTouching;
   }

}
