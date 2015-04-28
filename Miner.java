public class Miner extends Occupant{
   private int rate;
   private int resource_limit;
   private int animation_rate;
   private int resource_count;

   public Miner(String name, Object[] imgs, Point position, int rate, int resource_limit, int animation_rate){
      super(name, imgs, position);

      this.rate = rate;
      this.resource_limit = resource_limit;
      this.animation_rate = animation_rate;
      this.resource_count = 0;
   }

   public int getRate(){
      return this.rate;
   }

   public void setResourceCount(int num){
      this.resource_count = num;
   }

   public int getResourceCount(){
      return this.resource_count;
   }

   public int getResourceLimit(){
      return this.resource_limit;
   }

   public int getAnimationRate(){
      return this.animation_rate;
   }
      
   public Point nextPosition(WorldModel world, Point dest_pt){
      Point e_pt = this.getPosition();
      int horiz = Point.sign(dest_pt.getX() - e_pt.getX());
      Point new_pt = new Point(e_pt.getX() + horiz, e_pt.getY());

      if (horiz == 0 || world.isOccupied(new_pt)){
         int vert = Point.sign(dest_pt.getY() - e_pt.getY());
         new_pt = new Point(e_pt.getX(), e_pt.getY() + vert);

         if (vert == 0 || world.isOccupied(new_pt)){
            new_pt = new Point(e_pt.getX(), e_pt.getY());
         }
      }

      return new_pt;
   }
}
