import processing.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.LongConsumer;

public class WorldModel{
   private Grid background;
   private int num_rows;
   private int num_cols;
   private Grid occupancy;
   private List<Entity> entities;
   private OrderedList actionQueue;

   public WorldModel(int num_rows, int num_cols, Entity background){
      this.background = new Grid(num_cols, num_rows, background);
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      this.occupancy = new Grid(num_cols, num_rows, null);
      this.entities = new ArrayList<Entity>();
      this.actionQueue = new OrderedList();
   }
   
   public int getNumRows(){
      return num_rows;
   }

   public int getNumCols(){
      return num_cols;
   }

   public boolean withinBounds(Point p){
      return (p.getX() >= 0 && p.getX() < this.num_cols && p.getY() >= 0 && p.getY() < this.num_rows);
   }
   
   public boolean isOccupied(Point p){
      return (this.withinBounds(p) && this.occupancy.getCell(p) != null);
   }
   
   public int nearestEntity(List<Double> distance){
      int mindex = 0;
      
      for (int i = 0; i < distance.size(); i++){
         if (distance.get(i) < distance.get(mindex)){
            mindex = i;
         }
      }

      return mindex;
   }

   public Entity findNearest(Point p, Class cl){
      List<Double> distance = new ArrayList<Double>();
      List<Entity> ent = new ArrayList<Entity>();

      for (Entity e : this.entities){
         if (e instanceof GridItem && e.getClass() == cl && (!this.getBackground(((GridItem)e).getPosition()).getTouching() || e instanceof OreBlob || e instanceof Monster)){
            distance.add(p.distanceSq( ((GridItem) e).getPosition() ));
            ent.add(e);
         }
      }

      if (distance.size() != 0){
         return ent.get(nearestEntity(distance));
      }
      else{
         return null;
      }
   }

   public void addEntity(GridItem e){
      Point pt = e.getPosition();
      if (withinBounds(pt)){
         Entity old_e = this.occupancy.getCell(pt);
         if (old_e != null){
            ((Occupant)old_e).clearPendingActions();
         }
         this.occupancy.setCell(pt, e);
         this.entities.add(e);
      }
   }

   public Point[] moveEntity(GridItem e, Point pt){
      Point [] tiles = new Point[2];
      
      if (withinBounds(pt)){
         Point old_p = e.getPosition();
         this.occupancy.setCell(old_p, null);
         tiles[0] = old_p;
         this.occupancy.setCell(pt, e);
         tiles[1] = pt;
         e.setPosition(pt);
      }

      return tiles;
   }

   public void removeEntity(GridItem e){
      removeEntityAt(e.getPosition());
   }

   public void removeEntityAt(Point pt){
      if (withinBounds(pt) && this.occupancy.getCell(pt) != null){
         Entity e = this.occupancy.getCell(pt);
         ((GridItem) e).setPosition(new Point(-1, -1));
         this.entities.remove(e);
         this.occupancy.setCell(pt, null);
      }
   }

   public void scheduleAction(LongConsumer action, long time){
      actionQueue.insert(action, time);
   }
   
   public void unscheduleAction(LongConsumer action){
      actionQueue.remove(action);
   }

   
   public void updateOnTime(long time){
      List<LongConsumer> tiles = new ArrayList<LongConsumer>();
      ListItem next = actionQueue.head();
      while (next != null && next.getOrd() < time){
         actionQueue.pop();
         LongConsumer action = next.getItem();
         action.accept(time);
         next = actionQueue.head();
      }
   }

   public PImage getBackgroundImage(Point pt){
      if (withinBounds(pt)){
         return this.background.getCell(pt).getImage();
      }
      return null;
   }

   public Background getBackground(Point pt){
      if (withinBounds(pt)){
         return (Background) this.background.getCell(pt);
      }
      return null;
   }

   public void setBackground(Point pt, Background bgnd){
      if (withinBounds(pt)){
         this.background.setCell(pt, bgnd);
      }
   }

   public Entity getTileOccupant(Point pt){
      if (withinBounds(pt)){
         return this.occupancy.getCell(pt);
      }
      return null;
   }

   public List<Entity> getEntities(){
      return this.entities;
   }

   public Point findOpenAround(Point pt, int distance){
      for (int dy = (-1 * distance); dy <= distance; dy++){
         for (int dx = (-1 * distance); dx <= distance; dx++){
            Point new_p = new Point(pt.getX() + dx, pt.getY() + dy);

            if (withinBounds(new_p) && !isOccupied(new_p)){
               return new_p;
            }
         }
      }

      return null;
   }

   public Point findRandomAround(Point pt, int distance){
	         List<Point> pts = new ArrayList<Point>();
	         for (int dy = (-1 * distance); dy <= distance; dy++){
	            for (int dx = (-1 * distance); dx <= distance; dx++){
	               Point new_p = new Point(pt.getX() + dx, pt.getY() + dy);
	    
	               if (withinBounds(new_p) && !isOccupied(new_p)){
	                  pts.add(new_p);
	               }
	            }
	         }
	         if (pts.size() == 0){
	            return null;
	         }else{
	            Random r = new Random();
	            int index = r.nextInt(pts.size());
	           return pts.get(index);
	         }
	      }
}
