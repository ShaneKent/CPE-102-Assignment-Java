import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class NodeGrid{
   private int width;
   private int height;
   private Node [][] nodes;
   private WorldModel world;

   public NodeGrid(WorldModel world, int width, int height){
      this.world = world;
      this.width = width;
      this.height = height;
      this.nodes = new Node[this.width][this.height];
      
      for(int i = 0; i < this.width; i++){
         for (int j = 0; j < this.height; j++){
            this.nodes[i][j] = new Node(new Point(i, j));
         }
      }
   }
   
   public void setNode(Point p, Node node){
      this.nodes[p.getX()][p.getY()] = node;
   }

   public Node getNode(Point p){
      return this.nodes[p.getX()][p.getY()];
   }

   public List<Node> getNeighbors(Class calling, Class cl, Node current){
      List<Node> returned_nodes = new ArrayList<Node>();

      Point right = new Point(current.pt.getX() + 1, current.pt.getY());
      Point up = new Point(current.pt.getX(), current.pt.getY() - 1);
      Point left = new Point(current.pt.getX() - 1, current.pt.getY());
      Point down = new Point(current.pt.getX(), current.pt.getY() + 1);
            
      if (calling == OreBlob.class){
         if (current.pt.getY() + 1 < this.world.getNumRows() && (!this.world.isOccupied(down) || this.world.getTileOccupant(down).getClass() == cl || this.world.getTileOccupant(down).getClass() == Ore.class)){
            returned_nodes.add(getNode(down));
         }

         if (current.pt.getX() + 1 < this.world.getNumCols() && (!this.world.isOccupied(right) || this.world.getTileOccupant(right).getClass() == cl || this.world.getTileOccupant(right).getClass() == Ore.class)){
            returned_nodes.add(getNode(right));
         }

    
         if (current.pt.getY() - 1 >= 0 && (!this.world.isOccupied(up) || this.world.getTileOccupant(up).getClass() == cl || this.world.getTileOccupant(up).getClass() == Ore.class)){
            returned_nodes.add(getNode(up));
         }

         if (current.pt.getX() - 1 >= 0 && (!this.world.isOccupied(left) || this.world.getTileOccupant(left).getClass() == cl || this.world.getTileOccupant(left).getClass() == Ore.class)){
            returned_nodes.add(getNode(left));
         }
      }
      else{
         if (current.pt.getY() + 1 < this.world.getNumRows() && (!this.world.isOccupied(down) || this.world.getTileOccupant(down).getClass() == cl)){
            returned_nodes.add(getNode(down));
         }

         if (current.pt.getX() + 1 < this.world.getNumCols() && (!this.world.isOccupied(right) || this.world.getTileOccupant(right).getClass() == cl)){
            returned_nodes.add(getNode(right));
         }

    
         if (current.pt.getY() - 1 >= 0 && (!this.world.isOccupied(up) || this.world.getTileOccupant(up).getClass() == cl)){
            returned_nodes.add(getNode(up));
         }

         if (current.pt.getX() - 1 >= 0 && (!this.world.isOccupied(left) || this.world.getTileOccupant(left).getClass() == cl)){
            returned_nodes.add(getNode(left));
         }
      }
      
      return returned_nodes;
   }
}
