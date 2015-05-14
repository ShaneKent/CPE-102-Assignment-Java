import processing.core.*;

public class WorldView {

  public static final MOUSE_HOVER_ALPHA = 120;
  public static final MOUSE_HOVER_EMPTY_COLOR = Color(0, 255, 0);
  public static final MOUSE_HOVER_OCC_COLOR = Color(255, 0, 0);

   private Rectangle viewport;
   private WorldModel world;
   private int tile_width;
   private int tile_height;
   private int num_rows;
   private int num_cols;

   public WorldView(int view_cols, int view_rows, WorldModel world, int tile_width, int tile_height) {
      this.viewport = new Rectangle(0, 0, view_cols, view_rows);
      this.world = world;
      this.tile_width = tile_width;
      this.tile_height = tile_height;
      this.num_rows = world.getNumRows();
      this.num_cols = world.getNumCols();
   }


   public void drawBackground() {
      for (int y = 0; y < viewport.getHeight(); y++){
         for (int x = 0; x < viewport.getWidth(); x++){
            Point w_pt = viewportToWorld(viewport, new Point(x, y));
        }
      }
    }

    public void draw_entities()
     {
        for (Entity entity : world.entities) {
          if (viewport.collidepoint(entity.position.x, entity.position.y)) {
             v_pt = world_to_viewport(viewport, entity.position);
             screen.blit(entity.get_image(),
                (v_pt.x * tile_width, v_pt.y * tile_height));
           }
        } 
     }

   public void draw_viewport()
   {
      this.draw_background();
      this.draw_entities();
    }

   public void update_view(view_delta=(0,0), mouse_img=None) {
      viewport = create_shifted_viewport(viewport, view_delta,
         num_rows, num_cols);
      mouse_img = mouse_img;
      draw_viewport();
      pygame.display.update();
      mouse_move(mouse_pt);
    }

   public void update_view_tiles(tiles) {
      rects = [];
      for tile in tiles {
         if viewport.collidepoint(tile.x, tile.y) {
            v_pt = world_to_viewport(viewport, tile);
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

   public Rectangle update_tile(view_tile_pt, surface) {
      int abs_x = view_tile_pt.x * tile_width;
      int abs_y = view_tile_pt.y * tile_height;

      screen.blit(surface, (abs_x, abs_y));

      return Rectangle(abs_x, abs_y, tile_width, tile_height);
    }

   public TYPE get_tile_image(view_tile_pt) {
      pt = viewport_to_world(viewport, view_tile_pt);
      bgnd = world.get_background_image(pt);
      occupant = world.get_tile_occupant(pt);
      if (occupant) {
         img = pygame.Surface((tile_width, tile_height));
         img.blit(bgnd, (0, 0));
         img.blit(occupant.get_image(), (0,0));
         return img;
      }
      else
         return bgnd;
     }

   public TYPE create_mouse_surface(occupied) {
      surface = pygame.Surface((tile_width, tile_height));
      surface.set_alpha(MOUSE_HOVER_ALPHA);
      color = MOUSE_HOVER_EMPTY_COLOR;
      if (occupied) 
         color = MOUSE_HOVER_OCC_COLOR;
      surface.fill(color);
      if (mouse_img) 
         surface.blit(mouse_img, (0, 0));

      return surface;
    }

   public TYPE update_mouse_cursor() {
      return update_tile(self.mouse_pt,
         create_mouse_surface(world.is_occupied(viewport_to_world(viewport, mouse_pt))));
     }

   public void mouse_move(new_mouse_pt) {
      rects = [];

      rects.append(update_tile(mouse_pt,
         get_tile_image(mouse_pt)));

      if viewport.collidepoint(new_mouse_pt.x + viewport.left,
         new_mouse_pt.y + viewport.top) 
         mouse_pt = new_mouse_pt;

      rects.append(update_mouse_cursor());

      pygame.display.update(rects);
    }

   public Point viewport_to_world(viewport, pt) {
      return Point(pt.x + viewport.left, pt.y + viewport.top);
    }

   public Point world_to_viewport(viewport, pt) {
      return Point(pt.x - viewport.left, pt.y - viewport.top);
    }

   public int clamp(v, low, high) {
     return min(high, max(v, low));
    }

    public Rectangle create_shifted_viewport(viewport, delta, num_rows, num_cols) {
      new_x = clamp(viewport.left + delta[0], 0, num_cols - viewport.width);
      new_y = clamp(viewport.top + delta[1], 0, num_rows - viewport.height);

      return Rectangle(new_x, new_y, viewport.width, viewport.height);
    }


}
