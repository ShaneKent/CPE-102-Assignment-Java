public class ListItem
{
   private Object item;
   private int ord; 

   public ListItem(Object item, int ord){
      this.item = item;
      this.ord = ord;
   }

   public Object getItem()
   {
      return this.item;
   }

    public int getOrd()
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
