import processing.core.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import java.lang.Math;

public class TestCases extends PApplet{
   private static final double DELTA = 0.00001;

   @Test
   public void testEntity(){
      List<PImage> imgs = new ArrayList<PImage>();
      imgs.add(loadImage("images/miner1.bmp"));
      imgs.add(loadImage("images/miner2.bmp"));
      Entity e = new Entity("entity", imgs); 
      assertTrue("entity".equals(e.getName()));
      assertTrue(loadImage("images/miner1.bmp") == e.getImage());

      e.nextImage();
      assertTrue(loadImage("images/miner2.bmp") == e.getImage());
      assertTrue("unknown".equals(e.entityString()));
   }

   @Test
   public void testBackground(){
      List<PImage> imgs = new ArrayList<PImage>();
      imgs.add(loadImage("images/miner1.bmp"));
      imgs.add(loadImage("images/miner2.bmp"));

      Background b = new Background("background", imgs);
      assertTrue("background".equals(b.getName()));
      assertTrue(loadImage("images/miner1.bmp") == b.getImage());

      b.nextImage();
      assertTrue(loadImage("images/miner2.bmp") == b.getImage());
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
       List<PImage> imgs = new ArrayList<PImage>();
       imgs.add(loadImage("images/miner1.bmp"));
       imgs.add(loadImage("images/miner2.bmp"));
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
      List<PImage> imgs = new ArrayList<PImage>();
      imgs.add(loadImage("images/miner1.bmp"));
      imgs.add(loadImage("images/miner2.bmp"));

      Point p = new Point(0,0);
      Point newP = new Point(1,0);
      GridItem g = new GridItem("gridItem", imgs, p);
      assertTrue(g.getPosition() == p);
      g.setPosition(newP);
      assertTrue(g.getPosition() == newP);
      
      assertTrue("gridItem".equals(g.getName()));
      assertTrue(loadImage("images/miner1.bmp") == g.getImage());
      g.nextImage();
      assertTrue(loadImage("images/miner2.bmp") == g.getImage());
      assertTrue("unknown".equals(g.entityString()));
   }

  @Test
   public void testObstacle(){
      List<PImage> imgs = new ArrayList<PImage>();
      imgs.add(loadImage("images/miner1.bmp"));
      imgs.add(loadImage("images/miner2.bmp"));
      Point p = new Point(0,0);
      Obstacle o = new Obstacle("test", imgs, p);
      assertTrue("obstacle test 0 0".equals(o.entityString()));

      Point newP = new Point(1,0);
      assertTrue(o.getPosition() == p);
      o.setPosition(newP);
      assertTrue(o.getPosition() == newP);
      
      assertTrue("test".equals(o.getName()));
      assertTrue(loadImage("images/miner1.bmp") == o.getImage());
      o.nextImage();
      assertTrue(loadImage("images/miner2.bmp") == o.getImage());
  }
 
  @Test
   public void testOccupant(){
      List<PImage> imgs = new ArrayList<PImage>();
      imgs.add(loadImage("images/miner1.bmp"));
      imgs.add(loadImage("images/miner2.bmp"));
      Point p = new Point(0,0);
      Point newP = new Point(1,0);
      Occupant o = new Occupant("Occupant", imgs, p);


      assertTrue(o.getPosition() == p);
      o.setPosition(newP);
      assertTrue(o.getPosition() == newP);
      assertTrue("Occupant".equals(o.getName()));
      assertTrue(loadImage("images/miner1.bmp") == o.getImage());
      o.nextImage();
      assertTrue(loadImage("images/miner2.bmp") == o.getImage());
      assertTrue("unknown".equals(o.entityString()));
  }

 @Test
  public void testOre(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));
     Point p = new Point(0,0);
     Ore o1 = new Ore("Ore1", imgs, p);
     Ore o2 = new Ore("Ore2", imgs, p, 1000);
     assertTrue(o1.getRate() == 5000);
     assertTrue("ore Ore1 0 0 5000".equals(o1.entityString()));
     assertTrue(o2.getRate() == 1000);
     assertTrue("ore Ore2 0 0 1000".equals(o2.entityString()));



     Point newP = new Point(1,0);
     assertTrue(o1.getPosition() == p);
     o1.setPosition(newP);
     assertTrue(o1.getPosition() == newP);
     assertTrue("Ore1".equals(o1.getName()));
     assertTrue(loadImage("images/miner1.bmp") == o1.getImage());
     o1.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == o1.getImage());
 }

 @Test
  public void testVein(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));

     Point p = new Point(0,0);
     Vein v1 = new Vein("test1", imgs, p, 1000);
     Vein v2 = new Vein("test2", imgs, p, 1000, 4);
     assertTrue(v1.getRate() == 1000);
     assertTrue(v1.getResourceDistance() == 1);
     assertTrue("vein test1 0 0 1000 1".equals(v1.entityString()));
     assertTrue(v2.getRate() == 1000);
     assertTrue(v2.getResourceDistance() == 4);
     assertTrue("vein test2 0 0 1000 4".equals(v2.entityString()));


     Point newP = new Point(1,0);
     assertTrue(v1.getPosition() == p);
     v1.setPosition(newP);
     assertTrue(v1.getPosition() == newP);
      
     assertTrue("test1".equals(v1.getName()));
     assertTrue(loadImage("images/miner1.bmp") == v1.getImage());
     v1.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == v2.getImage());
  }

 @Test
  public void testQuake(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));
     Point p = new Point(0,0);
     Quake q = new Quake("test1", imgs, p, 1000);
     assertTrue(q.getAnimationRate() == 1000);


     Point newP = new Point(1,0);
     assertTrue(q.getPosition() == p);
     q.setPosition(newP);
     assertTrue(q.getPosition() == newP);
      
     assertTrue("test1".equals(q.getName()));
     assertTrue(loadImage("images/miner1.bmp") == q.getImage());
     q.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == q.getImage());
     assertTrue("unknown".equals(q.entityString()));
 }

 @Test
  public void testOreBlob(){
      List<PImage> imgs = new ArrayList<PImage>();   
     Point p = new Point(0,0);
     OreBlob o = new OreBlob("test", imgs, p, 1000, 5000);
     assertTrue(o.getRate() == 1000);
     assertTrue(o.getAnimationRate() == 5000);


     Point newP = new Point(1,0);
     assertTrue(o.getPosition() == p);
     o.setPosition(newP);
     assertTrue(o.getPosition() == newP);
      
     assertTrue("test".equals(o.getName()));
     assertTrue(loadImage("images/miner1.bmp") == o.getImage());
     o.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == o.getImage());
     assertTrue("unknown".equals(o.entityString()));
 
     Entity b = new Background("background", imgs);
     WorldModel w = new WorldModel(5, 5, b);
     Vein v = null;
     Vein v0 = new Vein("vein0", imgs, new Point(1, 1), 0);
     Vein v1 = new Vein("vein1", imgs, new Point(4, 3), 0);
     Point [] returned = new Point[1];
   
     returned[0] = o.getPosition();
     assertTrue(o.blobToVein(w, null)[0].equals(returned[0])); 
     returned[0] = v0.getPosition();
     assertTrue(o.blobToVein(w, v0)[0].equals(returned[0]));
     
     returned = new Point[2];
     returned[0] = o.getPosition();
     returned[1] = new Point(2, 0);

     Point [] points = new Point[2];
     points = o.blobToVein(w, v1);
     assertTrue(points[0].equals(returned[0]));
     assertTrue(points[1].equals(returned[1]));

     o = new OreBlob("test", imgs, new Point(1, 0), 1000, 5000);
     w = new WorldModel(5, 5, b);
     assertTrue(o.blobNextPosition(w, new Point(3, 0)).equals(new Point(2, 0)));
     assertTrue(o.blobNextPosition(w, new Point(1, 2)).equals(new Point(1, 1)));
     assertTrue(o.blobNextPosition(w, new Point(3, 3)).equals(new Point(2, 0)));      
 }


 @Test
  public void testMiner(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));
     Point p = new Point(0,0);
     Miner m = new Miner("test", imgs, p, 1000, 10, 5000, 0);
     assertTrue(m.getRate() == 1000);
     assertTrue(m.getResourceCount() == 0);
     assertTrue(m.getResourceLimit() == 10);
     assertTrue(m.getAnimationRate() == 5000);
     m.setResourceCount(2);
     assertTrue(m.getResourceCount() == 2);


     Point newP = new Point(1,0);
     assertTrue(m.getPosition() == p);
     m.setPosition(newP);
     assertTrue(m.getPosition() == newP);
     m.setPosition(p);

 
     assertTrue("test".equals(m.getName()));
     assertTrue(loadImage("images/miner1.bmp") == m.getImage());
     m.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == m.getImage());
     assertTrue("unknown".equals(m.entityString()));    

     Point pTest = new Point(0,1);
     Point pTest2 = new Point(1,1);
     GridItem g1 = new GridItem("test1", imgs, p);
     GridItem g2 = new GridItem("test2", imgs, pTest);

     Entity b = new Background("background", imgs);
     WorldModel w1 = new WorldModel(4, 4, b);
     Point mPoint = m.nextPosition(w1, p);
     assertTrue(mPoint.getX()==0 && mPoint.getY()==0);
     Point mPoint2 = m.nextPosition(w1, pTest);
     assertTrue(mPoint2.getX()==0 && mPoint2.getY()==1);
     Point mPoint3 = m.nextPosition(w1, pTest2);
     assertTrue(mPoint3.getX()==1 && mPoint3.getY()==0);
     w1.addEntity(g1);
     w1.addEntity(g2);
     Point mPoint4 = m.nextPosition(w1, p);
     assertTrue(mPoint4.getX()==0 && mPoint4.getY()==0);
     assertTrue(m.getPosition()==p);
     Point mPoint5 = m.nextPosition(w1, pTest);
     assertTrue(mPoint5.getX()==0 && mPoint5.getY()==0);

 }

 @Test
   public void testMinerNotFull(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));
     Point p = new Point(0,0);
     MinerNotFull m = new MinerNotFull("test", imgs, p, 1000, 10, 5000);
     assertTrue("miner test 0 0 10 1000 5000".equals(m.entityString()));


     Point newP = new Point(1,0);
     assertTrue(m.getPosition() == p);
     m.setPosition(newP);
     assertTrue(m.getPosition() == newP);
      
     assertTrue("test".equals(m.getName()));
     assertTrue(loadImage("images/miner1.bmp") == m.getImage());
     m.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == m.getImage());
     
     Entity b = new Background("background", imgs);
     WorldModel w = new WorldModel(5, 5, b);
     Ore o1 = new Ore("ore1", imgs, new Point(4, 4));
     Ore o2 = new Ore("ore2", imgs, new Point(1, 1));
     Ore o3 = null;

     Point [] returned = new Point[1];
     returned[0] = m.getPosition();
     assertTrue(m.minerToOre(w, o3)[0].equals( returned[0] ));
     returned[0] = o2.getPosition();
     assertTrue(m.minerToOre(w, o2)[0].equals( returned[0] ));

     returned = new Point[2];
     returned[0] = m.getPosition();
     returned[1] = m.nextPosition(w, o1.getPosition());
     Point [] points = m.minerToOre(w, o1);
     assertTrue(points[0].equals( returned[0] ));
     assertTrue(points[1].equals( returned[1] ));
     
     MinerNotFull old_m = new MinerNotFull("not_full", imgs, new Point(3, 3), 10, 15, 20);
     old_m.setResourceCount(15);
     MinerFull new_m = (MinerFull)old_m.tryTransformMiner(w);
     assertTrue(new_m.getName().equals("not_full") && new_m.getImages() == imgs && new_m.getPosition().equals(new Point(3,3)) && new_m.getResourceLimit() == 15 && new_m.getAnimationRate() == 20);
     
     MinerNotFull old_m2 = new MinerNotFull("not_full", imgs, new Point(3, 3), 10, 15, 20);
     assertTrue(old_m2 == old_m2.tryTransformMiner(w));

 }

 @Test
   public void testMinerFull(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));
     Point p = new Point(0,0);
     MinerFull m = new MinerFull("test", imgs, p, 1000, 10, 5000);


     Point newP = new Point(1,0);
     assertTrue(m.getPosition() == p);
     m.setPosition(newP);
     assertTrue(m.getPosition() == newP);
      
     assertTrue("test".equals(m.getName()));
     assertTrue(loadImage("images/miner1.bmp") == m.getImage());
     m.nextImage(); 
     assertTrue(loadImage("images/miner2.bmp") == m.getImage());
     assertTrue("unknown".equals(m.entityString()));     

     Entity b = new Background("background", imgs);
     WorldModel w = new WorldModel(5, 5, b);
     Blacksmith bs1 = new Blacksmith("smith1", imgs, new Point(4, 4), 0, 0);
     Blacksmith bs2 = new Blacksmith("smith2", imgs, new Point(1, 1), 0, 0);
     Blacksmith bs3 = null;

     Point [] returned = new Point[1];
     returned[0] = m.getPosition();
     assertTrue(m.minerToSmith(w, bs3)[0].equals( returned[0] ));
     assertTrue(m.minerToSmith(w, bs2).getClass() == Point[].class);
     
     returned = new Point[2];
     returned[1] = m.nextPosition(w, bs1.getPosition());
     returned[0] = m.getPosition();
     Point [] points = m.minerToSmith(w, bs1);
     assertTrue(points[0].equals( returned[0] ));
     assertTrue(points[1].equals( returned[1] ));

     MinerFull old_m = new MinerFull("full", imgs, new Point(3, 3), 10, 15, 20);
     MinerNotFull new_m = (MinerNotFull)old_m.tryTransformMiner(w);
     assertTrue(new_m.getName().equals("full") && new_m.getImages() == imgs && new_m.getPosition().equals(new Point(3,3)) && new_m.getResourceLimit() == 15 && new_m.getAnimationRate() == 20);     
 }


 @Test
   public void testBlacksmith(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));

     Point p = new Point(0,0);
     Blacksmith b1 = new Blacksmith("test", imgs, p, 10, 1000);
     Blacksmith b2 = new Blacksmith("test", imgs, p, 10, 1000, 4);

     assertTrue(b1.getRate()==1000);
     assertTrue(b1.getResourceCount()==0);
     b1.setResourceCount(2);
     assertTrue(b1.getResourceCount()==2);
     assertTrue(b1.getResourceLimit()==10);
     assertTrue(b1.getResourceDistance()==1);
     assertTrue("blacksmith test 0 0 10 1000 1".equals(b1.entityString()));

     assertTrue(b2.getRate()==1000);
     assertTrue(b2.getResourceCount()==0);
     b2.setResourceCount(2);
     assertTrue(b2.getResourceCount()==2);
     assertTrue(b2.getResourceLimit()==10);
     assertTrue(b2.getResourceDistance()==4);
     assertTrue("blacksmith test 0 0 10 1000 4".equals(b2.entityString()));
     

     Point newP = new Point(1,0);
     assertTrue(b1.getPosition() == p);
     b1.setPosition(newP);
     assertTrue(b1.getPosition() == newP);

     assertTrue("test".equals(b1.getName()));
     assertTrue(loadImage("images/miner1.bmp") == b1.getImage());
     b1.nextImage();
     assertTrue(loadImage("images/miner2.bmp") == b1.getImage());
 }

 @Test
   public void testWorldModel(){
     List<PImage> imgs = new ArrayList<PImage>();
     imgs.add(loadImage("images/miner1.bmp"));
     imgs.add(loadImage("images/miner2.bmp"));

     Point p = new Point(0,0);
     Entity b = new Background("background", imgs);
     GridItem g1 = new GridItem("test1", imgs, p);
     WorldModel w = new WorldModel(4, 4, b);

     Point p1 = new Point(4,3);
     Background b2 = new Background("background2", imgs);     
     Point p2 = new Point(0,1);     
 
     assertTrue(w.getBackground(p1) == null);
     assertTrue(w.getBackgroundImage(p1) == null);
     assertTrue(w.getBackground(p) == b);
     assertTrue(w.getBackgroundImage(p) == b.getImage());
     w.setBackground(p, b2);
     assertTrue(w.getBackground(p) == b2);

     assertTrue(!w.withinBounds(p1));
     assertTrue(w.withinBounds(p));
     assertTrue(!w.isOccupied(p));
     assertTrue(!w.isOccupied(p1));
     assertTrue(w.getTileOccupant(p) == null);
     w.addEntity(g1);
     assertTrue(w.getTileOccupant(p) == g1);
     assertTrue(w.isOccupied(p));
     List<Entity> ent = new ArrayList<Entity>();
     ent.add(g1);
     assertTrue(ent.equals(w.getEntities()));
     
     
     w.removeEntityAt(p);
     assertTrue(!w.isOccupied(p));
     GridItem g2 = new GridItem("test2", imgs, p);
     w.addEntity(g2);
     assertTrue(w.getTileOccupant(p) == g2);
     assertTrue(w.isOccupied(p));
     w.removeEntity(g2);
     assertTrue(!w.isOccupied(p));
     

     GridItem g3 = new GridItem("test3", imgs, p);
     w.addEntity(g3);
     Point[] arr1 = new Point[2];
     arr1[0] = null;
     arr1[1] = null;
     Point[] sameTiles = w.moveEntity(g3, p1);
     assertTrue(w.isOccupied(p)); 
     assertTrue(arr1[0]==sameTiles[0] && arr1[1]==sameTiles[1]);
     Point[] newTiles = w.moveEntity(g3, p2);
     assertTrue(!w.isOccupied(p));
     Point[] arr2 = new Point[2];
     arr2[0] = p;
     arr2[1] = p2;
     assertTrue(arr2[0]==newTiles[0] && arr2[1]==newTiles[1]);

     
   
     Point p3 = new Point(3, 2);
     Ore ore1 = new Ore("ore1", imgs, new Point(2, 3));
     Ore ore2 = new Ore("ore2", imgs, new Point(1, 2));
     Miner miner1 = new Miner("miner1", imgs, new Point(0, 0), 0, 0, 0, 0);
     Miner miner2 = new Miner("miner2", imgs, new Point(3, 3), 0, 0, 0, 0);
     w.addEntity(ore1); w.addEntity(ore2); w.addEntity(miner1); w.addEntity(miner2);
     assertTrue(w.findNearest(p3, Ore.class) == ore1);
     assertTrue(w.findNearest(p3, Miner.class) == miner2);
     assertTrue(w.findNearest(p3, OreBlob.class) == null);     

     List<Double> doubles = new ArrayList<Double>();
     doubles.add(15.87); doubles.add(23.4); doubles.add(-1.3); doubles.add(0.53); doubles.add(3.51); doubles.add(-41.4); doubles.add(2.4); doubles.add(10.5);
     assertTrue(w.nearestEntity(doubles) == 5);
           
     Point open_pt = new Point(2, 1);
     assertTrue(open_pt.equals(w.findOpenAround(p3, 1)));

  }
}
