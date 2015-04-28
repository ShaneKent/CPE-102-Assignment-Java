public class OreBlob extends Occupant{
   private int rate;
   private int animation_rate;

   public OreBlob(String name, Object[] imgs, Point position, int rate, int animation_rate){
      super(name, imgs, position);
      this.rate = rate;
      this.animation_rate = animation_rate;
   }

   public int getRate(){
      return this.rate;
   }

   public int getAnimationRate(){
      return this.animation_rate;
   }

   public Point[] blobToVein(WorldModel world, Vein v){
      Point e_pt = getPosition();
      Point [] pt = new Point[1];
      if (v == null){
         pt[0] = e_pt;
         return pt;
      }

      Point v_pt = v.getPosition();
      if (e_pt.adjacent(v_pt)){
         //actions.remove_entity(world, vein);
         pt[0] = v_pt;
         return pt;
      }
      else{
         Point new_p = blobNextPosition(world, v_pt);
         Entity old_e = world.getTileOccupant(new_p);
         if (old_e instanceof Ore){
            //actions.remove_entity(world, old_e);
         }
         return world.moveEntity(this, new_p);
      }
   }

   public Point blobNextPosition(WorldModel world, Point dest_pt){
      Point e_pt = getPosition();
      int horiz = Point.sign(dest_pt.getX() - e_pt.getX());
      Point new_pt = new Point(e_pt.getX() + horiz, e_pt.getY());

      if (horiz == 0 || (world.isOccupied(new_pt) && !(world.getTileOccupant(new_pt) instanceof Ore))){
         int vert = Point.sign(dest_pt.getY() - e_pt.getY());
         new_pt = new Point(e_pt.getX(), e_pt.getY() + vert);

         if (vert == 0 || (world.isOccupied(new_pt) && !(world.getTileOccupant(new_pt) instanceof Ore))){
            new_pt = new Point(e_pt.getX(), e_pt.getY());
         }
      }

      return new_pt;
   }
}

