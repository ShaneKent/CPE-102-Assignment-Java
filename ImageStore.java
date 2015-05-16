import java.util.List;
import java.util.ArrayList;
import processing.core.*;

public class ImageStore{
   public static String DEFAULT_IMAGE_NAME = "background_default";
   public static Color DEFAULT_IMAGE_COLOR = new Color(128, 128, 128, 0);

   public static PImage createDefaultImage(PApplet applet, int tile_width, int tile_height){
      PImage img = applet.createImage(tile_width, tile_height, PImage.RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++){
         img.pixels[i] = applet.color(DEFAULT_IMAGE_COLOR.getRed(), DEFAULT_IMAGE_COLOR.getBlue(), DEFAULT_IMAGE_COLOR.getGreen(), DEFAULT_IMAGE_COLOR.getAlpha());
      }
      return img;
   }  

   public static List<PImage> loadImages(String filename, int tile_width, int tile_height){
      List<PImage> images = new ArrayList<PImage>();
      return images;
   }
   
   public static void getImagesInternal(){

   }

   public static List<PImage> getImages(List<PImage> images, String key){
      return images;
   }
}
