import java.util.ArrayList;
import java.util.List;
import java.util.function.LongConsumer;

public class OrderedList
{
   private List<ListItem> list;

   public OrderedList(){
      this.list = new ArrayList<ListItem>();
   }

   public void insert(LongConsumer item, long ord)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size && this.list.get(idx).getOrd() < ord)
      {
         idx += 1;
      }
      this.list.add(idx, new ListItem(item, ord));
   }

   public void remove(ListItem item)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size && !this.list.get(idx).getItem().equals(item))
      { 
         idx += 1;
      }

      if (idx < size){
         this.list.remove(idx);
      }
   }

   public ListItem head()
   {
      if (!this.list.isEmpty()){
         return this.list.get(0);
      }
      return null;
   }

   public ListItem pop()
   {
      if (!this.list.isEmpty())
      {
         return this.list.remove(0);
      }
      return null;
   }
}





