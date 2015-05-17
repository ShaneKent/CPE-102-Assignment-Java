import java.util.Scanner;
import java.io.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.ArrayList;

import processing.core.*;

public class ImageStore{
   
   public static String DEFAULT_IMAGE_NAME = "background_default";
   public static Color DEFAULT_IMAGE_COLOR = new Color(128, 128, 128, 0);

   private static final int COLOR_MASK = 0xffffff;

   public static PImage createDefaultImage(PApplet applet, int tile_width, int tile_height){
      PImage img = applet.createImage(tile_width, tile_height, PImage.RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++){
         img.pixels[i] = applet.color(DEFAULT_IMAGE_COLOR.getRed(), DEFAULT_IMAGE_COLOR.getBlue(), DEFAULT_IMAGE_COLOR.getGreen(), DEFAULT_IMAGE_COLOR.getAlpha());
      }
      return img;
   }  
   


   public static LinkedHashMap<String, List<PImage>> loadImages(PApplet screen, String filename, int tile_width, int tile_height){
      try{
         LinkedHashMap<String, List<PImage>> images = new LinkedHashMap<String, List<PImage>>();
         Scanner in = new Scanner(new FileInputStream(filename));

         while(in.hasNextLine()){
            processImageLine(screen, images, in.nextLine());
         }      

         if (images.containsKey(DEFAULT_IMAGE_NAME)){
            PImage default_image = createDefaultImage(screen, tile_width, tile_height);
            List<PImage> list = new ArrayList<PImage>();
            list.add(default_image);
            images.put(DEFAULT_IMAGE_NAME, list);
         }
         return images;
      }
      catch (FileNotFoundException e){
         System.err.println(e.getMessage());
         return new LinkedHashMap<String, List<PImage>>();
      }
   }
   
   public static void processImageLine(PApplet screen, LinkedHashMap<String, List<PImage>> images, String line){
      String[] attrs = line.split("\\s");
      if (attrs.length >= 2){
         String key = attrs[0];
         PImage img = screen.loadImage(attrs[1]);
         if (img != null){
            List<PImage> imgs = getImagesInternal(images, key);
            imgs.add(img);
            images.put(key, imgs);
            
            //if to set up the colors.
            if (attrs.length == 6){
               int r = Integer.parseInt(attrs[2]);
               int g = Integer.parseInt(attrs[3]);
               int b = Integer.parseInt(attrs[4]);
               int a = Integer.parseInt(attrs[5]);
               img = setAlpha(img, screen.color(r,g,b), a);
            }
         }
      }
   }

   public static List<PImage> getImagesInternal(LinkedHashMap<String, List<PImage>> images, String key){
      List<PImage> list = images.get(key);
      if (list != null){
         return list;
      }
      return new ArrayList<PImage>();
   }

   public static List<PImage> getImages(LinkedHashMap<String, List<PImage>> images, String key){
      List<PImage> list = images.get(key);
      if (list != null){
         return list;
      }
      return images.get(DEFAULT_IMAGE_NAME);
   }

   public static PImage setAlpha(PImage img, int maskColor, int alpha){
      int alphaValue = alpha << 24;
      int nonAlpha = maskColor & COLOR_MASK;
      img.format = PApplet.ARGB;
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++){
         if ((img.pixels[i] & COLOR_MASK) == nonAlpha){
            img.pixels[i] = alphaValue | nonAlpha;
         }
      }
      img.updatePixels();
      return img;
   }
}
