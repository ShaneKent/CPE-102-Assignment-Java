import processing.core.*;

public class WorldView {
   private Rectangle viewport;
   private WorldModel world;
   private int tile_width;
   private int tile_height;
   private int num_rows;
   private int num_cols;

   public WorldView(int view_cols, int view_rows, WorldModel world, int tile_width, int tile_height){
      this.viewport = new Rectangle(0, 0, view_cols, view_rows);
      this.world = world;
      this.tile_width = tile_width;
      this.tile_height = tile_height;
      this.num_rows = world.getNumRows();
      this.num_cols = world.getNumCols();
   }
/*
   public void drawBackground(){
      for (int y = 0; y < this.viewport.height; y++){
         for (int x = 0; x < this.viewport.width; x++){
            Point w_pt = viewportToWorld(this.viewport, new Point(x, y));
*/
}
