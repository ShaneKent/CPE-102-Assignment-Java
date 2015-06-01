import processing.core.*;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

public class OreBlob extends Mover
{

   private boolean truth = false;
   
   public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animation_rate){
      super(name, imgs, position, rate, animation_rate);
   }
   
   public Point[] blobToVein(WorldModel world, Vein v){
      Point e_pt = getPosition();
      Point [] pt = new Point[1];
      if (v == null){
         pt[0] = e_pt;
         truth = false;
         return pt;
      }

      Point v_pt = v.getPosition();
      if (e_pt.adjacent(v_pt)){
         Actions.removeEntity(world, v);
         pt[0] = v_pt;
         truth = true;
         return pt;
      }
      else{
         Point new_p = blobNextPosition(world, v_pt);
         Occupant old_e = (Occupant) world.getTileOccupant(new_p);
         if (old_e instanceof Ore){
            Actions.removeEntity(world, old_e);
         }
         truth = false;
         return world.moveEntity(this, new_p);
      }
   }
   
   public Point[] blobToVein(WorldModel world, SuperVein v){
      Point e_pt = getPosition();
      Point [] pt = new Point[1];
      if (v == null){
         pt[0] = e_pt;
         truth = false;
         return pt;
      }

      Point v_pt = v.getPosition();
      if (e_pt.adjacent(v_pt)){
         Actions.removeEntity(world, v);
         pt[0] = v_pt;
         truth = true;
         return pt;
      }
      else{
         Point new_p = blobNextPosition(world, v_pt);
         Occupant old_e = (Occupant) world.getTileOccupant(new_p);
         if (old_e instanceof Ore){
            Actions.removeEntity(world, old_e);
         }
         truth = false;
         return world.moveEntity(this, new_p);
      }
   }

   public Point blobNextPosition(WorldModel world, Point dest_pt){
      Point start_pt = this.getPosition();
      List<Node> path = AStar(world, world.getTileOccupant(dest_pt).getClass(), start_pt, dest_pt);
      if (!path.isEmpty())
	      return path.get(1).pt;
      else 
	      return start_pt;
   }
   
   public LongConsumer createOreBlobAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store){
      LongConsumer[] action = { null };
      action[0] = (long current_ticks) -> {
         removePendingAction(action[0]);
         
         Point entity_pt = getPosition();
         Vein vein = (Vein) world.findNearest(entity_pt, Vein.class);
         SuperVein svein = (SuperVein) world.findNearest(entity_pt, SuperVein.class);
         
         
         Point[] tiles;
         
         if (vein != null && svein != null){
            if (entity_pt.distanceSq(vein.getPosition()) < entity_pt.distanceSq(svein.getPosition())){
               tiles = blobToVein(world, vein);
            }
            else{
               tiles = blobToVein(world, svein);
            }
         }
         else if (vein != null && svein == null){
            tiles = blobToVein(world, vein);
         }
         else{
            tiles = blobToVein(world, svein);
         }
         
         
         long next_time = current_ticks + getRate();
         
         if (truth){//tiles.length == 2){
            Quake quake = Actions.createQuake(world, tiles[0], current_ticks, i_store);
            world.addEntity(quake);
            next_time = current_ticks + getRate() * 2;
         }
         
         Actions.scheduleAction(world, this, createOreBlobAction(world, i_store), next_time);
      };
      return action[0];
   }

   public void scheduleBlob(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store){
      Actions.scheduleAction(world, this, createOreBlobAction(world, i_store),
                              ticks + getRate());
      Actions.scheduleAnimation(world, this);
   }

}

