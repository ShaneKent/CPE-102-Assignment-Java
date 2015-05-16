public class Color 
{
  private int red;
  private int green;
  private int blue;
  private int alpha;

  public Color(int red, int green, int blue, int alpha) 
  {
    this.red = red;
    this.green = green;
    this.blue = blue; 
    this.alpha = alpha;
  }

  public int getRed()
  {
     return this.red;
   }

   public int getGreen()
  {
     return this.green;
   }  

   public int getBlue()
  {
     return this.blue;
   }

   public int getAlpha(){
      return this.alpha;
   }
}
