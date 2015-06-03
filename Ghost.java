import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.function.LongConsumer;

import processing.core.PImage;


public class Ghost extends Mover {
	   private boolean truth = false;
	   public static final int ORE_CORRUPT_MIN = 20000;
	   public static final int ORE_CORRUPT_MAX = 30000;
	   
	   public Ghost(String name, List<PImage> imgs, Point position, int rate, int animation_rate){
	      super(name, imgs, position, rate, animation_rate);
	   }
	   
	   public Point[] ghostToMonster(WorldModel world, Monster m){
		      Point e_pt = getPosition();
		      Point [] pt = new Point[1];
		      if (m == null){
		         pt[0] = e_pt;
		         truth = false;
		         return pt;
		      }

		      Point m_pt = m.getPosition();
		      if (e_pt.adjacent(m_pt)){
		         Actions.removeEntity(world, m);
		     
		         int m_x = m_pt.getX();
		         int m_y = m_pt.getY();
		         
		         for (int x = -1; x <= 1; x++){
		             for (int y = -1; y <= 1; y++){
		                Point mpt = new Point(m_x + x, m_y + y);
		                if (world.withinBounds(mpt)){
		                   world.getBackground(mpt).setTouching(false);
		                }
		             }
		          } 
		         
		         pt[0] = m_pt;
		         truth = true;
		         return pt;
		      }
		      else{
		         Point new_p = ghostNextPosition(world, m_pt);
		         Occupant old_e = (Occupant) world.getTileOccupant(new_p);
		         //if (old_e instanceof )
		         truth = false;
		         return world.moveEntity(this, new_p);
		      }
		   }
		  
		   public Point ghostNextPosition(WorldModel world, Point dest_pt){
		      Point start_pt = this.getPosition();
		      List<Node> path = AStar(world, world.getTileOccupant(dest_pt).getClass(), start_pt, dest_pt);
		      if (!path.isEmpty())
			      return path.get(1).pt;
		      else 
			      return start_pt;
		   }
		   
		   public LongConsumer createGhostAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store){
		      LongConsumer[] action = { null };
		      action[0] = (long current_ticks) -> {
		         removePendingAction(action[0]);
		         
		         Point entity_pt = getPosition();
		         Monster mst = (Monster) world.findNearest(entity_pt, Monster.class);
		         		         
		         
		         Point[] tiles;
		         
		        // if (mst != null){
		         //   tiles = ghostToMonster(world, mst);
		        // }
		         //else {
		            tiles = ghostToMonster(world, mst);
		         //}
		         
		         
		         long next_time = current_ticks + getRate();
		         
		         if (truth){//tiles.length == 2){
		        	 Random r = new Random();
		             int randNum = r.nextInt((ORE_CORRUPT_MAX - ORE_CORRUPT_MIN) + 1) + ORE_CORRUPT_MIN;
		        	 OreBlob blob = Actions.createBlob(world, getName() + " -- blob", tiles[0], 
                             randNum / Actions.BLOB_RATE_SCALE, 
                             current_ticks, i_store);
		        	 world.addEntity(blob);
		            //Quake quake = Actions.createQuake(world, tiles[0], current_ticks, i_store);
		            //world.addEntity(quake);
		            next_time = current_ticks + getRate() * 2;
		         }
		         
		         Actions.scheduleAction(world, this, createGhostAction(world, i_store), next_time);
		      };
		      return action[0];
		   }

		   public void scheduleGhost(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
		      Actions.scheduleAction(world, this, createGhostAction(world, i_store),
		                              ticks + getRate());
		      Actions.scheduleAnimation(world, this);
		   }
}
