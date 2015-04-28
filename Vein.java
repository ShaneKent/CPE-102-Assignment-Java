public class Vein extends Occupant{
   private int rate;
   private int resource_distance;

   public Vein(String name, Object[] imgs, Point position, int rate){
      super(name, imgs, position);
      this.rate = rate;
      this.resource_distance = 1;
   }

   public Vein(String name, Object[] imgs, Point position, int rate, int resource_distance){
      super(name, imgs, position);
      this.rate = rate;
      this.resource_distance = resource_distance;
   }

   public int getRate(){
      return this.rate;
   }

   public int getResourceDistance(){
      return this.resource_distance;
   }

   public String entityString(){
      return "vein " + this.getName() + " " + this.getPosition().getX() + " " + this.getPosition().getY() +
         " " + this.getRate() + " " + this.getResourceDistance();
   }
}
