import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.lang.Math;

public class TestCases{
   private static final double DELTA = 0.00001;

   @Test
   public void testEntity(){
      String[] imgs = {"img1", "img2"};
      Entity e = new Entity("entity", imgs); 
      assertTrue("entity".equals(e.getName()));
      assertTrue("img1".equals(e.getImage()));

      e.nextImage();
      assertTrue("img2".equals(e.getImage()));
      assertTrue("unknown".equals(e.entityString()));
   }

   @Test
   public void testBackground(){
      String[] imgs = {"img1", "img2"};
      Background b = new Background("background", imgs);
      assertTrue("background".equals(b.getName()));
      assertTrue("img1".equals(b.getImage()));

      b.nextImage();
      assertTrue("img2".equals(b.getImage()));
      assertTrue("unknown".equals(b.entityString()));
   }

   @Test
   public void testPoint(){
      Point p = new Point(3, 4);
      Point toPoint = new Point(0,0);
      assertTrue(p.getX() == 3);
      assertTrue(p.getY() == 4);
      assertTrue(p.distanceSq(toPoint) == 25.0);
      Point neighbor = new Point(4,4);
      Point notNeighbor = new Point(2, 1);
      assertTrue(p.adjacent(neighbor));
      assertTrue(!p.adjacent(notNeighbor));

      assertTrue(p.sign(-1) == -1);
      assertTrue(p.sign(0) == 0);
      assertTrue(p.sign(1) == 1);
   }

   @Test
       public void testGrid(){
       String[] imgs = {"img1", "img2"};
       Entity e1 = new Entity("testEntity1", imgs);
       Entity e2 = new Entity("testEntity2", imgs);
       Point p = new Point(0,0);

       Grid g = new Grid(2, 2, e1);
       assertTrue(g.getCell(p)==e1);
       g.setCell(p,  e2);
       assertTrue(g.getCell(p)==e2);

   }

  @Test
      public void testGridItem(){
      String[] imgs = {"img1", "img2"};
      Point p = new Point(0,0);
      Point newP = new Point(1,0);
      GridItem g = new GridItem("gridItem", imgs, p);
      assertTrue(g.getPosition() == p);
      g.setPosition(newP);
      assertTrue(g.getPosition() == newP);

   }

  @Test
      public void testObstacle(){
      String[] imgs = {"img1", "img2"};
      Point p = new Point(0,0);
      Obstacle o = new Obstacle("test", imgs, p);
      assertTrue("obstacle test 0 0".equals(o.entityString()));
  }
 
  @Test
      public void testOccupant(){
      String[] imgs = {"img1", "img2"};
      Point p = new Point(0,0);
      Point newP = new Point(1,0);
      Occupant o = new Occupant("Occupant", imgs, p);
      assertTrue(o.getPosition() == p);
      o.setPosition(newP);
      assertTrue(o.getPosition() == newP);
  }

 @Test
     public void testOre(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Ore o1 = new Ore("Ore1", imgs, p);
     Ore o2 = new Ore("Ore2", imgs, p, 1000);
     assertTrue(o1.getRate() == 5000);
     assertTrue("ore Ore1 0 0 5000".equals(o1.entityString()));
     assertTrue(o2.getRate() == 1000);
     assertTrue("ore Ore2 0 0 1000".equals(o2.entityString()));

 }

 @Test
     public void testVein(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Vein v1 = new Vein("test1", imgs, p, 1000);
     Vein v2 = new Vein("test2", imgs, p, 1000, 4);
     assertTrue(v1.getRate() == 1000);
     assertTrue(v1.getResourceDistance() == 1);
     assertTrue("vein test1 0 0 1000 1".equals(v1.entityString()));
     assertTrue(v2.getRate() == 1000);
     assertTrue(v2.getResourceDistance() == 4);
     assertTrue("vein test2 0 0 1000 4".equals(v2.entityString()));

 }

 @Test
     public void testQuake(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Quake q = new Quake("test1", imgs, p, 1000);
     assertTrue(q.getAnimationRate() == 1000);
 }

 @Test
     public void testOreBlob(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     OreBlob o = new OreBlob("test", imgs, p, 1000, 5000);
     assertTrue(o.getRate() == 1000);
     assertTrue(o.getAnimationRate() == 5000);
 }


 @Test
     public void testMiner(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Miner m = new Miner("test", imgs, p, 1000, 10, 5000);
     assertTrue(m.getRate() == 1000);
     assertTrue(m.getResourceCount() == 0);
     assertTrue(m.getResourceLimit() == 10);
     assertTrue(m.getAnimationRate() == 5000);
     m.setResourceCount(2);
     assertTrue(m.getResourceCount() == 2);
    
 }

 @Test
     public void testMinerNotFull(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Miner m = new MinerNotFull("test", imgs, p, 1000, 10, 5000);
     assertTrue("miner test 0 0 10 1000 5000".equals(m.entityString()));
 }

 @Test
     public void testMinerFull(){
     String[] imgs = {"img1", "img2"};
     Point p = new Point(0,0);
     Miner m = new MinerFull("test", imgs, p, 1000, 10, 5000);
     
 }


}
