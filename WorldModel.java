import java.util.ArrayList;
import java.util.List;

public class WorldModel{
   private Grid background;
   private int num_rows;
   private int num_cols;
   private Grid occupancy;
   private List<Entity> entities;
   
   public WorldModel(int num_rows, int num_cols, Entity background){
      this.background = new Grid(num_cols, num_rows, background);
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      this.occupancy = new Grid(num_cols, num_rows, null);
      this.entities = new ArrayList<Entity>();
   }

   public boolean withinBounds(Point p){
      return (p.getX() >= 0 && p.getX() < this.num_cols && p.getY() >= 0 && p.getY() < this.num_rows);
   }
   
   public boolean isOccupied(Point p){
      return (this.withinBounds(p) && this.occupancy.getCell(p) != null);
   }
   
   private int nearestEntity(List<Double> distance){
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
         if (e instanceof GridItem && e.getClass() == cl){
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
            //old_e.clear_pending_actions();
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
         tiles[1] = old_p;
         e.setPosition(pt);
      }

      return tiles;
   }
}
