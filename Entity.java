import java.util.List;
import processing.core.*;

public class Entity{
   private String name;   
   private List<PImage> imgs;
   private int current_img;

   public Entity(String name, List<PImage> imgs){
      this.name = name;
      this.imgs = imgs;
      this.current_img = 0;
   }

   public String getName(){
      return this.name;
   }

   public List<PImage> getImages(){
      return this.imgs;
   }

   public PImage getImage(){
      return this.imgs.get(this.current_img);
   }
   
   public void nextImage(){
      this.current_img = (this.current_img + 1) % this.imgs.size();
   }

   public String entityString(){
      return "unknown";
   }

}
