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

    public Mover(String name, List<PImage> imgs, Point position, int rate,
                  int animation_rate){
      super(name, imgs, position, animation_rate);

      this.rate = rate;
   }

   public int getRate(){
      return this.rate;
   }
   
   private int h_cost(Node n1, Node n2){
      return java.lang.Math.abs(n1.pt.getX() - n2.pt.getX()) + java.lang.Math.abs(n1.pt.getY() - n2.pt.getY());
   }

   public List<Node> AStar(WorldModel world, Class cl, Point start_pt, Point end_pt){
      NodeGrid closedset = new NodeGrid(world, world.getNumCols(), world.getNumRows());
      OpenSet openset = new OpenSet();
      LinkedList<Node> came_from = new LinkedList<Node>();
            
      Node start_node = new Node(start_pt);
      Node end_node = new Node(end_pt);
      
      start_node.g = 0;
      start_node.f = start_node.g + h_cost(start_node, end_node);
      
      openset.insert(start_node, start_node.f);
      closedset.setNode(start_node.pt, start_node);
      closedset.setNode(end_node.pt, end_node);
            
      while (openset.size() != 0){
         
         Node current = openset.head().getNode();
         
         if (current.equals(end_node)){
            //System.out.println("Return");
            return reconstruct(came_from, end_node);
         }

         openset.remove(current);
         current.marked = true;
         closedset.setNode(current.pt, current);
                  
         for (Node neighbor : closedset.getNeighbors(cl, current)){
            if (closedset.getNode(neighbor.pt).marked){
               continue;
            }
               
            int tentativeG = current.g + 1;

            if (!openset.hasNode(neighbor) || tentativeG < neighbor.g){
               
               neighbor.came_from = current;
               came_from.add(0, neighbor);
               neighbor.g = current.g + 1;
               neighbor.f = neighbor.g + h_cost(neighbor, end_node);
                              
               if (!openset.hasNode(neighbor)){
                  openset.insert(neighbor, neighbor.f);
               }
            }
         }
      }
      return new ArrayList<Node>();
   }
   
   public List<Node> reconstruct(LinkedList<Node> came_from, Node current){
      List<Node> total = new ArrayList<Node>();
      
      while (came_from.contains(current)){
         Node pulled = came_from.pollLast().came_from;
         if (!total.contains(pulled)){
            total.add(pulled);
            //System.out.println(pulled.pt.getX() + " " + pulled.pt.getY());
         }
      }

      return total;
   }
}
