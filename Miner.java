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
   /*
   This function will be added when the world model class is done.
   public Point next_position(...){

   }
   */
}
