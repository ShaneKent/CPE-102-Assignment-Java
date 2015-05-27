public class Node{
   public Point pt;
   public boolean marked = false;
   public int f = 0;
   public int g = 0;
   public Node came_from = null;
   
   public Node(Point pt){
      this.pt = new Point(pt.getX(), pt.getY());
   }

   public boolean equals(Object node){
      if (this == node){
         return true;
      }
      if (!(node instanceof Node)){
         return false;
      }
      if (node == null || (node.getClass() != this.getClass())){
         return false;
      }
      
      return (this.pt.getX() == ((Node)node).pt.getX()) && (this.pt.getY() == ((Node)node).pt.getY()) && (this.marked == ((Node)node).marked) && (this.f == ((Node)node).f) && (this.g == ((Node)node).g);
   }
}
