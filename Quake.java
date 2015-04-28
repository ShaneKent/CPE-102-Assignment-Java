public class Quake extends Occupant{
   private int animation_rate;

   public Quake(String name, Object[] imgs, Point position, int animation_rate){
      super(name, imgs, position);
      this.animation_rate = animation_rate;
   }

   public int getAnimationRate(){
      return this.animation_rate;
   }
}
