import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import processing.core.*;

public class Load{
   final static int PROPERTY_KEY = 0;

   final static String BGND_KEY = "background";
   final static int BGND_NUM_PROPERTIES = 4;
   final static int BGND_NAME = 1;
   final static int BGND_COL = 2;
   final static int BGND_ROW = 3;

   final static String MINER_KEY = "miner";
   final static int MINER_NUM_PROPERTIES = 7;
   final static int MINER_NAME = 1;
   final static int MINER_LIMIT = 4;
   final static int MINER_COL = 2;
   final static int MINER_ROW = 3;
   final static int MINER_RATE = 5;
   final static int MINER_ANIMATION_RATE = 6;

   final static String OBSTACLE_KEY = "obstacle";
   final static int OBSTACLE_NUM_PROPERTIES = 4;
   final static int OBSTACLE_NAME = 1;
   final static int OBSTACLE_COL = 2;
   final static int OBSTACLE_ROW = 3;

   final static String ORE_KEY = "ore";
   final static int ORE_NUM_PROPERTIES = 5;
   final static int ORE_NAME = 1;
   final static int ORE_COL = 2;
   final static int ORE_ROW = 3;
   final static int ORE_RATE = 4;

   final static String SMITH_KEY = "blacksmith";
   final static int SMITH_NUM_PROPERTIES = 7;
   final static int SMITH_NAME = 1;
   final static int SMITH_COL = 2;
   final static int SMITH_ROW = 3;
   final static int SMITH_LIMIT = 4;
   final static int SMITH_RATE = 5;
   final static int SMITH_REACH = 6;

   final static String VEIN_KEY = "vein";
   final static int VEIN_NUM_PROPERTIES = 6;
   final static int VEIN_NAME = 1; 
   final static int VEIN_RATE = 4;
   final static int VEIN_COL = 2;
   final static int VEIN_ROW = 3;
   final static int VEIN_REACH = 5;

   public static void loadWorld(WorldModel world, LinkedHashMap<String, List<PImage>> images, Scanner file, boolean run){
      while(file.hasNextLine()){
         String[] properties = file.nextLine().split("\\s");
         if (properties.length != 1){
            if (properties[PROPERTY_KEY].equals(BGND_KEY)){
               addBackground(world, properties, images);
            }
            else{
               addEntity(world, properties, images, run);
            }
         }
      }
   }
  
   public static void addBackground(WorldModel world, String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length >= BGND_NUM_PROPERTIES){
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
         String name = properties[BGND_NAME];
         world.setBackground(pt, new Background(name, ImageStore.getImages(i_store, name)));
      }
   }

   public static void addEntity(WorldModel world, String[] properties, LinkedHashMap<String, List<PImage>> i_store, boolean run){
      GridItem new_entity = createFromProperties(properties, i_store);
      if (new_entity != null){
         world.addEntity(new_entity);
         if (run){
            //scheduleEntity(world, new_entity, i_store);
         }
      }
   }

   public static GridItem createFromProperties(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      String key = properties[PROPERTY_KEY];
      if (properties.length > 1){
         if (key.equals(MINER_KEY)){
            return createMiner(properties, i_store);
         }
         else if (key.equals(VEIN_KEY)){
            return createVein(properties, i_store);
         }
         else if (key.equals(ORE_KEY)){
            return createOre(properties, i_store);
         }
         else if (key.equals(SMITH_KEY)){
            return createBlacksmith(properties, i_store);
         }
         else if (key.equals(OBSTACLE_KEY)){
            return createObstacle(properties, i_store);
         }
      }
      return null;
   }

   public static Miner createMiner(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length == MINER_NUM_PROPERTIES){
         Miner miner = new MinerNotFull(properties[MINER_NAME], ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[MINER_COL]), Integer.parseInt(properties[MINER_ROW])),
               Integer.parseInt(properties[MINER_RATE]), Integer.parseInt(properties[MINER_LIMIT]),
               Integer.parseInt(properties[MINER_ANIMATION_RATE]));
         return miner;
      }
      return null;
   }

   public static Vein createVein(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length == VEIN_NUM_PROPERTIES){
         Vein vein = new Vein(properties[VEIN_NAME], ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW])),
               Integer.parseInt(properties[VEIN_RATE]), Integer.parseInt(properties[VEIN_REACH]));
         return vein;
      }
      else if (properties.length == VEIN_NUM_PROPERTIES - 1){
         Vein vein = new Vein(properties[VEIN_NAME], ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW])),
               Integer.parseInt(properties[VEIN_RATE]));
         return vein;
      }
      return null;
   }

   public static Ore createOre(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length == ORE_NUM_PROPERTIES){
         Ore ore = new Ore(properties[ORE_NAME],
               ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW])),
               Integer.parseInt(properties[ORE_RATE]));
         return ore;
      }
      else if (properties.length == ORE_NUM_PROPERTIES - 1){
         Ore ore = new Ore(properties[ORE_NAME],
               ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW])));
         return ore;
      }
      return null;
   }

   public static Blacksmith createBlacksmith(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length == SMITH_NUM_PROPERTIES){
         Blacksmith smith = new Blacksmith(properties[SMITH_NAME], ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW])),
               Integer.parseInt(properties[SMITH_LIMIT]), Integer.parseInt(properties[SMITH_RATE]), 
               Integer.parseInt(properties[SMITH_REACH]));
         return smith;
      }
      else if (properties.length == SMITH_NUM_PROPERTIES - 1){
         Blacksmith smith = new Blacksmith(properties[SMITH_NAME], ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW])), 
               Integer.parseInt(properties[SMITH_LIMIT]), Integer.parseInt(properties[SMITH_RATE]));
         return smith;
      }
      return null;
   }

   public static Obstacle createObstacle(String[] properties, LinkedHashMap<String, List<PImage>> i_store){
      if (properties.length == OBSTACLE_NUM_PROPERTIES){
         Obstacle obstacle = new Obstacle(properties[OBSTACLE_NAME],
               ImageStore.getImages(i_store, properties[PROPERTY_KEY]),
               new Point(Integer.parseInt(properties[OBSTACLE_COL]), Integer.parseInt(properties[OBSTACLE_ROW])));
         if (Integer.parseInt(properties[OBSTACLE_ROW]) > 15){
            System.out.println(properties[OBSTACLE_ROW]);
         }
         return obstacle;
      }
      return null;
   }

}
