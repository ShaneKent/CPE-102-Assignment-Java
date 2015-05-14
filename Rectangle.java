public class Rectangle {
   final int topLeftX;
   final int topLeftY;
   final int width;
   final int height;
   
   public Rectangle(int topLeftX, int topLeftY, int width, int height){
      this.topLeftX = topLeftX;
      this.topLeftY = topLeftY;
      this.width = width;
      this.height = height;
   }

   public int getTopLeftX()
   {
     return this.topLeftX;
   }

   public int getTopLeftY()
   {
     return this.topLeftY;
   }

   public int getWidth()
   {
     return this.width;
   }

   public int getHeight() 
   {
     return this.height;
   }
}
