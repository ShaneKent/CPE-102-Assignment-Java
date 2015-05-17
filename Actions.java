import java.util.function.LongConsumer;
import static java.lang.Math.*;

public class Actions
{    
    public static final int BLOB_RATE_SCALE = 4;
    public static final int BLOB_ANIMATION_RATE_SCALE = 50;
    public static final int BLOB_ANIMATION_MIN = 1;
    public static final int BLOB_ANIMATION_MAX = 3;
    
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    
    public static final int QUAKE_STEPS = 10;
    public static final int QUAKE_DURATION = 1100;
    public static final int QUAKE_ANIMATION_RATE = 100;
    
    public static final int VEIN_SPAWN_DELAY = 500;
    public static final int VEIN_RATE_MIN = 8000;
    public static final int VEIN_RATE_MAX = 17000;
    
    // empty constructor
    
    public static LongConsumer createAnimationAction(WorldModel world, AnimatedActor entity,
                                        int repeat_count)
    {
      LongConsumer func = (long current_ticks) -> {
         //entity.removePendingAction(func);

         entity.nextImage();

         if (repeat_count != 1){ 
            scheduleAction(world, entity,
               createAnimationAction(world, entity, max(repeat_count - 1, 0)),
               current_ticks + entity.getAnimationRate());
          }
       };

      return func;
    }
    
    /*
    public static Entity tryTransformMiner(WorldModel world, Entity entity,
                                           this::transform)
    {
        Entity new_entity = transform.apply(world);
        if (entity != new_entity)
        {
            clearPendingActions(world, entity);
            world.removeEntityAt(entity.getPosition());
            world.addEntity(new_entity);
            scheduleAnimation(world, new_entity);
        }
        return new_entity;
    }
    
    
    public static void removeEntity(WorldModel world, Entity entity)
    {
        for (LongConsumer action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
        world.removeEntity(entity);
    }
    
    public static OreBlob createBlob(WorldModel world, String name, Point pt,
                                     int rate, long ticks, ImageStore i_store)
    {
        OreBlob blob = new OreBlob(name, pt, rate,
                                   getImages(i_store, 'blob'),
                                   randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
                                   * BLOB_ANIMATION_RATE_SCALE);
        blob.scheduleBlob(world, ticks, i_store);
        return blob;
    }
    
    
    public static Ore createOre(WorldModel world, String name, Point pt,
                                long ticks, ImageStore i_store)
    {
        Ore ore = new Ore(name, pt, getImages(i_store, 'ore'),
                          randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX));
        ore.scheduleOre(world, ticks, i_store);
        
        return ore;
    }
    
    
    public static Quake createQuake(WorldModel world, Point pt, long ticks, ImageStore i_store)
    {
        Quake quake = new Quake("quake", pt,
                                getImages(i_store, 'quake'), QUAKE_ANIMATION_RATE);
        quake.scheduleQuake(world, ticks);
        return quake;
    }
    
    
    public static Vein createVein(WorldModel world, String name, Point pt,
                                  long ticks, ImageStore i_store)
    {
        Vein vein = new Vein("vein" + name,
                             randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
                             pt, getImages(i_store, 'vein'));
        return vein;
    }
    */
    
    public static void scheduleAction(WorldModel world, AnimatedActor entity, LongConsumer action, long time)
    {
        entity.addPendingAction(action);
        world.scheduleAction(action, time);
    }
    
    /*
    public static void scheduleAnimation(WorldModel world, Entity entity, int repeat_count)
    {
        scheduleAction(world, entity,
                       createAnimationAction(world, entity, repeat_count),
                       entity.getAnimationRate());
    }

      
    public static void scheduleAnimation(WorldModel world, Entity entity)
    {
      int repeat_count = 0;
      scheduleAction(world, entity,
      createAnimationAction(world, entity, repeat_count),
      entity.getAnimationRate());
    }

    
    
    public static void clearPendingActions(WorldModel world, Entity entity)
    {
        for (LongConsumer action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
    }*/
}