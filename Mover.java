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

   public Point AStar(WorldModel world, Class cl, Point start_pt, Point end_pt){
      NodeGrid closedset = new NodeGrid(world, world.getNumCols(), world.getNumRows());
      OpenSet openset = new OpenSet();
      List<Node> came_from = new LinkedList<Node>();

      Map<Node, Integer> g_score = new HashMap<Node, Integer>();
      Map<Node, Integer> f_score = new HashMap<Node, Integer>();
            
      Node start_node = new Node(start_pt);
      Node end_node = new Node(end_pt);

      g_score.put(start_node, 0);
      f_score.put(start_node, g_score.get(start_node) + h_cost(start_node, end_node));
      
      openset.insert(start_node, f_score.get(start_node));
      closedset.setNode(start_node.pt, start_node);
      
      while (openset.size() != 0){
         Node current = openset.head().getNode();
         
         if (current.pt == end_node.pt){
            System.out.println("RETURN NEEDED");
         }

         openset.remove(current);
         closedset.setNode(current.pt, current);
         current.marked = true;
         
         for (Node neighbor : closedset.getNeighbors(cl, current)){
            if (neighbor.marked){
               break;
            }
            int tentativeG = g_score.get(current) + 1;
            if (!openset.hasNode(neighbor) || tentativeG < g_score.get(neighbor)){
               came_from.add(0, neighbor);
               g_score.put(neighbor, tentativeG);
               f_score.put(neighbor, tentativeG + h_cost(neighbor, end_node));
               if (!openset.hasNode(neighbor)){
                  openset.insert(neighbor, f_score.get(neighbor));
               }
            }
         }
      }
      return new Point(1, 1);
   }
}
