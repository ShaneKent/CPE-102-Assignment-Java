import java.io.*;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

import java.util.LinkedHashMap;

import processing.core.*;
import processing.event.KeyEvent;

public class Main extends PApplet {
   final static boolean RUN_AFTER_LOAD = true;

   final String IMAGE_LIST_FILE_NAME = "imagelist";
   final String WORLD_FILE = "gaia.sav";
   
   final int WORLD_WIDTH_SCALE = 2;
   final int WORLD_HEIGHT_SCALE = 2;
   
   final int SCREEN_WIDTH = 1280;//640;
   final int SCREEN_HEIGHT = 960;//480;
   final int TILE_WIDTH = 32;
   final int TILE_HEIGHT = 32;

   private long next_time;
   private WorldModel world;
   private WorldView view;
   private LinkedHashMap<String, List<PImage>> i_store;

   public static Background createDefaultBackground(List<PImage> img){
      return new Background(ImageStore.DEFAULT_IMAGE_NAME, img);
   }   

   public static void loadWorld(WorldModel world, LinkedHashMap<String, List<PImage>> i_store, String filename){
      try{
         Scanner in = new Scanner(new FileInputStream(filename));
         Load.loadWorld(world, i_store, in, RUN_AFTER_LOAD);
      }
      catch (FileNotFoundException e){
         System.err.println(e.getMessage());
      }
   }

   public void setup(){
      size(SCREEN_WIDTH, SCREEN_HEIGHT);
      background(color(255, 255, 255));

      i_store = ImageStore.loadImages(this, IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);

      int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
      int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE;
      Background defaultBackground = createDefaultBackground(ImageStore.getImages(i_store, ImageStore.DEFAULT_IMAGE_NAME));

      world = new WorldModel(num_rows, num_cols, defaultBackground);
      view = new WorldView(this, SCREEN_WIDTH/TILE_WIDTH, SCREEN_HEIGHT/TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
      loadWorld(world, i_store, WORLD_FILE);

      view.updateView();
   
      next_time = System.currentTimeMillis();
   }

   public void draw(){
      long time = System.currentTimeMillis();
      if (time >= next_time){
         world.updateOnTime(time);
         next_time = time + 100;
      }
      background(color(255, 255, 255));
      view.updateView();
      drawEntityPath();
   }
   
   public void drawEntityPath(){
      view.drawBackground();
      for (Entity e : world.getEntities()){
      
         Point mouse = WorldView.viewportToWorld(view.getViewport(), new Point(mouseX / 32, mouseY / 32));
         Point ent_pt = ((GridItem)e).getPosition();
         
         if (mouse.equals(ent_pt) && e instanceof Mover){
            NodeGrid checked = ((Mover)e).getChecked();
            Node startPath = ((Mover)e).getStartPath();
            if (checked != null){
               for (int x = 0; x < checked.getWidth(); x++){
                  for (int y = 0; y < checked.getHeight(); y++){
                     if (checked.getNode(new Point(x,y)).f != 0){
                        Point change = WorldView.worldToViewport(view.getViewport(), new Point(x, y));
                        image(ImageStore.getImages(i_store, "black").get(0), change.getX() * 32 + 4, change.getY() * 32 + 4);
                     }
                  }
               }
            }
            if (startPath != null){
               while (startPath.came_from != null){
                  startPath = startPath.came_from;
                  Point change = WorldView.worldToViewport(view.getViewport(), new Point(startPath.pt.getX(), startPath.pt.getY()));
                  image(ImageStore.getImages(i_store, "red").get(0), change.getX() * 32 + 8, change.getY() * 32 + 8);
               }
            }
         }
         
      }
      view.drawEntities();
   }
   
   public void mousePressed(){
      Monster monster = Actions.createMonster(world, "monster - " + System.currentTimeMillis(), WorldView.viewportToWorld(view.getViewport(), new Point(mouseX / 32, mouseY / 32)), 1, System.currentTimeMillis(), i_store);
      world.addEntity(monster);
   }
   
   public void keyPressed(KeyEvent e){
      int keyCode = e.getKeyCode();
      int[] view_delta = new int[2];
      view_delta[0] = 0;
      view_delta[1] = 0;
      switch ( keyCode ){
         case 37:
            view_delta[0] = -1;
            view.updateView(view_delta);
            break;
         case 38:
            view_delta[1] = -1;
            view.updateView(view_delta);
            break;
         case 39:
            view_delta[0] = 1;
            view.updateView(view_delta);
            break;
         case 40:
            view_delta[1] = 1;
            view.updateView(view_delta);
            break;
      }
   }

   public static void main(String[] args){
      PApplet.main("Main");
   }
}
