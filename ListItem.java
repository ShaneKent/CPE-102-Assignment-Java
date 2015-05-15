public class ListItem
{
   private Object item;
   private int ord; 

   public ListItem(Object item, int ord):
      this.item = item;
      this.ord = ord;

   public Object getItem()
   {
      return this.item;
   }

    public int getOrd()
   {
      return this.ord;
   }

   public boolean equals(Object b)
   {
      return this.getItem() == b.getItem() && this.getOrd() == b.getOrd();
   }
}