public class OpenSetItem
{
   private Node node;
   private int f_value; 

   public OpenSetItem(Node node,int f_value){
      this.node = node;
      this.f_value = f_value;
   }

   public Node getNode()
   {
      return this.node;
   }

   public long getFValue()
   {
      return this.f_value;
   }

   public boolean equals(Object that)
   {
      if (this == that){
         return true;
      }
      if (!(that instanceof ListItem)){
         return false;
      }
      if (that == null || (that.getClass() != this.getClass())){
         return false;
      }
      
      return this.getNode() == ((OpenSetItem)that).getNode() && this.getFValue() == ((OpenSetItem)that).getFValue();
   }
}
