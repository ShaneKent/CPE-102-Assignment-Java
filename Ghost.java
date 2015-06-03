import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.LongConsumer;

import processing.core.PImage;


public class Ghost extends Mover {
	   private boolean truth = false;
	   
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
		            Quake quake = Actions.createQuake(world, tiles[0], current_ticks, i_store);
		            world.addEntity(quake);
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
