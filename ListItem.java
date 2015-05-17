import java.util.function.LongConsumer;

public class ListItem
{
   private LongConsumer item;
   private long ord; 

   public ListItem(LongConsumer item, long ord){
      this.item = item;
      this.ord = ord;
   }

   public LongConsumer getItem()
   {
      return this.item;
   }

   public long getOrd()
   {
      return this.ord;
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
      
      return this.getItem() == ((ListItem)that).getItem() && this.getOrd() == ((ListItem)that).getOrd();
   }
}
