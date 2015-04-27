public class Obstacle extends GridItem{
   
   public Obstacle(String name, Object[] imgs, Point position){
      super(name, imgs, position);
   }
   
   public String entityString(){
      Point p = getPosition();
      return "obstacle " + getName() + " " + p.getX() + " " + p.getY();
   }

}
