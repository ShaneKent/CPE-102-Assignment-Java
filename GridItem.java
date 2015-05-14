import java.util.List;
import processing.core.*;

public class GridItem extends Entity{
   private Point position;

   public GridItem(String name, List<PImage> imgs, Point position){
      super(name, imgs);
      this.position = position;
   }

   public void setPosition(Point pt){
      this.position = pt;
   }

   public Point getPosition(){
      return this.position;
   }
}
