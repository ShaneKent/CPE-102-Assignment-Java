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
   
   public void printFScores(){
      for (int x = 0; x < this.width; x++){
         for (int y = 0; y < this.height; y++){
            System.out.print(getNode(new Point(x, y)).f + " ");
         }
      System.out.println("");
      }
   }
   
   public void setNode(Point p, Node node){
      this.nodes[p.getX()][p.getY()] = node;
   }

   public Node getNode(Point p){
      return this.nodes[p.getX()][p.getY()];
   }

   public List<Node> getNeighbors(Class cl, Node current){
      List<Node> returned_nodes = new ArrayList<Node>();

      Point left = new Point(current.pt.getX() - 1, current.pt.getY());
      Point right = new Point(current.pt.getX() + 1, current.pt.getY());
      Point up = new Point(current.pt.getX(), current.pt.getY() - 1);
      Point down = new Point(current.pt.getX(), current.pt.getY() + 1);
      
      if (current.pt.getX() - 1 >= 0 && (!this.world.isOccupied(left) || this.world.getTileOccupant(left).getClass() == cl)){
         //if (!nodes[left.getX()][left.getY()].marked){
            returned_nodes.add(getNode(left));
         //}
         //System.out.println("Left" + left.getX() + " " + left.getY());
      }
      if (current.pt.getX() + 1 < this.world.getNumCols() && (!this.world.isOccupied(right) || this.world.getTileOccupant(right).getClass() == cl)){
         //if (!nodes[right.getX()][right.getY()].marked){
            returned_nodes.add(getNode(right));
         //}
         //System.out.println("Right" + right.getX() + " " + left.getY());
      }
      if (current.pt.getY() - 1 >= 0 && (!this.world.isOccupied(up) || this.world.getTileOccupant(up).getClass() == cl)){
         //if (!nodes[up.getX()][up.getY()].marked){
            returned_nodes.add(getNode(up));
         //}
         //System.out.println("Up" + up.getX() + " " + left.getY());
      }
      if (current.pt.getY() + 1 < this.world.getNumRows() && (!this.world.isOccupied(down) || this.world.getTileOccupant(down).getClass() == cl)){
         //if (!nodes[down.getX()][down.getY()].marked){
            returned_nodes.add(getNode(down));
         //}
         //System.out.println("Down" + down.getX() + " " + left.getY());
      }
      return returned_nodes;
   }
}
