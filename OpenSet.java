import java.util.ArrayList;
import java.util.List;
import java.util.function.LongConsumer;

public class OpenSet
{
   private List<OpenSetItem> list;

   public OpenSet(){
      this.list = new ArrayList<OpenSetItem>();
   }

   public void insert(Node node, int f_value)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size && this.list.get(idx).getFValue() < f_value)
      {
         idx += 1;
      }
      this.list.add(idx, new OpenSetItem(node, f_value));
   }

   public void remove(Node node)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size && !this.list.get(idx).getNode().equals(node))
      { 
         idx += 1;
      }

      if (idx < size){
         this.list.remove(idx);
      }
   }
   
   public boolean hasNode(Node node)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size){
         if (this.list.get(idx).getNode().equals(node)){
            return true;
         }
         idx += 1;
      }
      return false;
   }

   public OpenSetItem head()
   {
      if (!this.list.isEmpty()){
         return this.list.get(0);
      }
      return null;
   }

   public OpenSetItem pop()
   {
      if (!this.list.isEmpty())
      {
         return this.list.remove(0);
      }
      return null;
   }

   public int size(){
      return this.list.size();
   }
}





