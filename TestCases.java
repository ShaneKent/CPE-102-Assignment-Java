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








}
