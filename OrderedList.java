import java.util.ArrayList;
import java.util.List;

public class OrderedList
{
   private List<ListItem> list;

   public OrderedList():
      this.list = new ArrayList<ListItem>();


   public void insert(Object item, int ord)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size and this.list.get(idx).getOrd() < ord)
      {
         idx += 1;
      }
      this.list.add(idx, new ListItem(item, ord));
   }

   public void remove(ListItem item)
   {
      int size = this.list.size();
      int idx = 0;
      while (idx < size and !this.list.get(idx).getItem().equals(item)):
       { 
         idx += 1;
       }

      if (idx < size)
         this.list.remove(idx);
   }

   public ListItem head()
   {
       if (!this.list.isEmpty())
         return this.list.get(0);
      else
         return null;
      // CHECK
      //return self.list[0] if self.list else None;
   }

   public ListItem pop()
   {  // what's this if statement for?
      // CHECK
       if (!this.list.isEmpty())
      {
         return this.list.remove(0);
      }
   }
}





