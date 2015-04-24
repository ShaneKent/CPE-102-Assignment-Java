public class Obstacle extends GridItem{
   
   public Obstacle(String name, Object[] imgs, Point position){
      super(name, imgs, position);
   }
   
   public String entity_string(){
      Point p = getPosition();
      return "obstacle " + getName() + " " + p.getX() + " " + p.getY();
   }

}
