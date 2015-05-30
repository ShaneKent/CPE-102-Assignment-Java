import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class Mover extends AnimatedActor
{   
   private int rate;
   private int resource_limit;
   private int resource_count;
   
   private NodeGrid closedset;
   private Node startPath;

   public Mover(String name, List<PImage> imgs, Point position, int rate,
                  int animation_rate){
      super(name, imgs, position, animation_rate);

      this.rate = rate;
   }

   public int getRate(){
      return this.rate;
   }
   
   public NodeGrid getChecked(){
      return closedset;
   }
   
   public Node getStartPath(){
      return startPath;
   }
   
   private int h_cost(Node n1, Node n2){
      return java.lang.Math.abs(n1.pt.getX() - n2.pt.getX()) + java.lang.Math.abs(n1.pt.getY() - n2.pt.getY());
   }

   public List<Node> AStar(WorldModel world, Class cl, Point start_pt, Point end_pt){
      
      closedset = new NodeGrid(world, world.getNumCols(), world.getNumRows());
      OpenSet openset = new OpenSet();
            
      Node start_node = new Node(start_pt);
      Node end_node = new Node(end_pt);
      
      start_node.g = 0;
      start_node.h = h_cost(start_node, end_node);
      start_node.f = start_node.g + start_node.h;
      
      openset.insert(start_node, start_node.f);
      closedset.setNode(start_node.pt, start_node);
      closedset.setNode(end_node.pt, end_node);
            
      while (openset.size() != 0){
         
         Node current = openset.head().getNode();
         
         if (current.equals(end_node)){
            startPath = end_node;
            return reconstruct(startPath);
         }

         openset.remove(current);
         current.marked = true;
         closedset.setNode(current.pt, current);
                  
         for (Node neighbor : closedset.getNeighbors(this.getClass(), cl, current)){
            if (closedset.getNode(neighbor.pt).marked){
               continue;
            }
               
            int tentativeG = current.g + 1;

            if (!openset.hasNode(neighbor) || tentativeG < neighbor.g){
               
               neighbor.came_from = current;
               neighbor.g = current.g + 1;
               neighbor.h = h_cost(neighbor, end_node);
               neighbor.f = neighbor.g + neighbor.h;
                              
               if (!openset.hasNode(neighbor)){
                  openset.insert(neighbor, neighbor.f);
               }
            }
         }
      }
      return new ArrayList<Node>();
   }
   
   public List<Node> reconstruct(Node current){
      List<Node> total = new ArrayList<Node>();
      
      total.add(0, current);
      
      while (current.came_from != null){
         current = current.came_from;
         total.add(0, current);
      }

      return total;
   }
}
