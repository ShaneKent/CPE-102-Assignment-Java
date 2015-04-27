public class Grid{
   final int EMPTY = 0;
   final int GATHERER = 1;
   final int GENERATOR = 2;
   final int RESOURCE = 3;
   
   private int width;
   private int height;
   private Entity [][] cells;

   public Grid(int width, int height, Entity e){
      this.width = width;
      this.height = height;
      this.cells = new Entity[this.height][this.width];

      for (int i = 0; i < this.height; i++){
         for (int j = 0; j < this.width; j++){
            this.cells[i][j] = e;
         }
      }
   }

   public void setCell(Point p, Entity e){
      this.cells[p.getY()][p.getX()] = e;
   }

   public Entity getCell(Point p){
      return this.cells[p.getY()][p.getX()];
   }
}
