import processing.core.*;
import static java.lang.Math.*;

public class WorldView {
   
   //MOST IF NOT ALL FUNCTIONS IN HERE SHOULD ALREADY BE COMPLETED.
   //IGNORE ALL THE MOUSE OVER FUNCTIONS.
   //WILL FIX THE updateViewTile FUNCTION AFTER TALKING WITH KEEN.

   //These don't need to be added, we are not supposed to do the mouse over functions.
   //public static final MOUSE_HOVER_ALPHA = 120;
   //public static final MOUSE_HOVER_EMPTY_COLOR = new Color(0, 255, 0);
   //public static final MOUSE_HOVER_OCC_COLOR = new Color(255, 0, 0);

   private Rectangle viewport;
   private WorldModel world;
   private PApplet screen;
   private int tile_width;
   private int tile_height;
   private int num_rows;
   private int num_cols;

   public WorldView(int view_cols, int view_rows, PApplet screen, WorldModel world, int tile_width, int tile_height) {
      this.viewport = new Rectangle(0, 0, view_cols, view_rows);
      this.world = world;
      this.screen = screen;
      this.tile_width = tile_width;
      this.tile_height = tile_height;
      this.num_rows = world.getNumRows();
      this.num_cols = world.getNumCols();
   }


   public void drawBackground() {
      for (int y = 0; y < viewport.getHeight(); y++){
         for (int x = 0; x < viewport.getWidth(); x++){
            Point w_pt = viewportToWorld(viewport, new Point(x, y));
            PImage img = world.getBackgroundImage(w_pt);
            screen.image(img, x * tile_width, y * tile_height);
        }
      }
    }

    public void drawEntities()
     {
        for (Entity entity : world.getEntities()) {
           if (viewport.collidePoint(((GridItem)entity).getPosition().getX(), ((GridItem)entity).getPosition().getY())) {
             Point v_pt = worldToViewport(viewport, ((GridItem)entity).getPosition());
             screen.image(entity.getImage(), v_pt.getX() * tile_width, v_pt.getY() * tile_height);
           }
        } 
     }

   public void drawViewport()
   {
      this.drawBackground();
      this.drawEntities();
    }

   public void updateView(int[] view_delta) {
      viewport = createShiftedViewport(viewport, view_delta, num_rows, num_cols);
      drawViewport();
      //pygame.display.update();
    }
   
   /*
    * Need to create the updateOnTime function inside the WorldModel class.
   //NOT CORRECTED
   public void updateViewTiles(tiles) {
      List<Rectangle> rects = new ArrayList<Rectangle>();
      for tile in tiles {
         if viewport.collidepoint(tile.x, tile.y) {
            Point v_pt = world_to_viewport(viewport, tile);
            img = get_tile_image(v_pt);
            rects.append(update_tile(v_pt, img));
            if (mouse_pt.x == v_pt.x and mouse_pt.y == v_pt.y)
             { 
               rects.append(update_mouse_cursor());
             }
          }
       }
      pygame.display.update(rects);
   }
   */
   
   public Rectangle updateTile(Point view_tile_pt, PImage surface) {
      int abs_x = view_tile_pt.getX() * tile_width;
      int abs_y = view_tile_pt.getY() * tile_height;

      screen.image(surface, abs_x, abs_y);

      return new Rectangle(abs_x, abs_y, tile_width, tile_height);
   }

   public PImage getTileImage(Point view_tile_pt) {
      Point pt = viewportToWorld(viewport, view_tile_pt);
      PImage bgnd = world.getBackgroundImage(pt);
      Entity occupant = world.getTileOccupant(pt);
      if (occupant != null) {
         PImage img = new PImage(tile_width, tile_height);
         img.set(0, 0, bgnd);
         img.set(0, 0, occupant.getImage());
         return img;
      }
      else{
         return bgnd;
      }
   }
   /*
    * I don't believe we need to do these because they correspond to mouse over animations.
    *
   public Surface create_mouse_surface(boolean occupied) {
      surface = pygame.Surface((tile_width, tile_height));
      surface.set_alpha(MOUSE_HOVER_ALPHA);
      Color color = MOUSE_HOVER_EMPTY_COLOR;
      if (occupied) 
         color = MOUSE_HOVER_OCC_COLOR;
      surface.fill(color);
      if (mouse_img) 
         surface.blit(mouse_img, (0, 0));

      return surface;
    }
   
   public Rectangle update_mouse_cursor() {
      return update_tile(mouse_pt,
         create_mouse_surface(world.is_occupied(viewport_to_world(viewport, mouse_pt))));
     }

   public void mouse_move(Point new_mouse_pt) {
      rects = [];

      rects.append(update_tile(mouse_pt,
         get_tile_image(mouse_pt)));

      if (viewport.collidepoint(new_mouse_pt.getX() + viewport.left,
         new_mouse_pt.getX() + viewport.top)) 
         Point mouse_pt = new_mouse_pt;

      rects.append(update_mouse_cursor());

      pygame.display.update(rects);
    }
   */
   public static Point viewportToWorld(Rectangle viewport, Point pt) {
      return new Point(pt.getX() + viewport.getLeft(), pt.getY() + viewport.getTop());
    }

   public static Point worldToViewport(Rectangle viewport, Point pt) {
      return new Point(pt.getX() - viewport.getLeft(), pt.getY() - viewport.getTop());
    }

   public static int clamp(int v, int low, int high) {
     return min(high, max(v, low));
    }

   public static Rectangle createShiftedViewport(Rectangle viewport, int[] delta, int num_rows, int num_cols) {
      int new_x = clamp(viewport.getLeft() + delta[0], 0, num_cols - viewport.getWidth());
      int new_y = clamp(viewport.getTop() + delta[1], 0, num_rows - viewport.getHeight());

      return new Rectangle(new_x, new_y, viewport.getWidth(), viewport.getHeight());
    }


}
