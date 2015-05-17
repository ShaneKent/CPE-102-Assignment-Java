public class Rectangle {
   final int left;
   final int top;
   final int width;
   final int height;
   
   public Rectangle(int left, int top, int width, int height){
      this.left = left;
      this.top = top;
      this.width = width;
      this.height = height;
   }

   public int getLeft()
   {
     return this.left;
   }

   public int getTop()
   {
     return this.top;
   }

   public int getWidth()
   {
     return this.width;
   }

   public int getHeight() 
   {
     return this.height;
   }
   
   protected int getRight(){
      return this.left + this.width;
   }
   protected int getBottom(){
      return this.top - this.height;
   }
   
   //Format copied from Pygame documentation.
   public boolean collidePoint(int x, int y){
      return (x >= getLeft() && x < getRight()) && (y > getBottom() && y <= 2 * getHeight());
   }
}
