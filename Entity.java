/* *
 *
 * We can leave the imgs variable as an Array of Objects for now. They are not very descript but
 * that will be fixed once we properly implement imgs.
 * 
 * */

public class Entity{
   private String name;   
   private Object [] imgs;
   private int current_img;

   public Entity(String name, Object[] imgs){
      this.name = name;
      this.imgs = imgs;
      this.current_img = 0;
   }

   public String getName(){
      return this.name;
   }

   public Object[] getImages(){
      return this.imgs;
   }

   public Object getImage(){
      return this.imgs[this.current_img];
   }
   
   public void nextImage(){
      this.current_img = (this.current_img + 1) % this.imgs.length;
   }

   public String entity_string(){
      return "unknown";
   }

}
