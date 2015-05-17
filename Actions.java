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
    
    public static createAnimationAction(TYPE world, Entity entity,
                                        TYPE repeat_count)
    {
        def action(current_ticks):
        entity.removePendingAction(action);
        
        entity.nextImage();
        
        if (repeat_count != 1)
        {
            scheduleAction(world, entity,
                           createAnimationAction(world, entity, max(repeat_count - 1, 0)),
                           current_ticks + entity.getAnimationRate());
        }
        
        return [entity.getPosition()];
        return action;
    }
    
    
    public static Entity tryTransformMiner(TYPE world, Entity entity,
                                           TYPE transform)
    {
        Entity new_entity = transform(world);
        if (entity != new_entity)
        {
            clearPendingActions(world, entity);
            world.removeEntityAt(entity.getPosition());
            world.addEntity(new_entity);
            scheduleAnimation(world, new_entity);
        }
        return new_entity;
    }
    
    
    public static void removeEntity(TYPE world, Entity entity)
    {
        for (TYPE action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
        world.removeEntity(entity);
    }
    
    public static OreBlob createBlob(TYPE world, String name, Point pt,
                                     TYPE rate, TYPE ticks, TYPE i_store)
    {
        OreBlob blob = new OreBlob(name, pt, rate,
                                   getImages(i_store, 'blob'),
                                   randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
                                   * BLOB_ANIMATION_RATE_SCALE);
        blob.schedule_blob(world, ticks, i_store);
        return blob;
    }
    
    
    public static Ore createOre(TYPE world, String name, Point pt,
                                TYPE ticks, TYPE i_store)
    {
        Ore ore = new Ore(name, pt, getImages(i_store, 'ore'),
                          randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX));
        ore.scheduleOre(world, ticks, i_store);
        
        return ore;
    }
    
    
    public static Quake createQuake(TYPE world, Point pt, TYPE ticks, TYPE i_store)
    {
        Quake quake = new Quake("quake", pt,
                                getImages(i_store, 'quake'), QUAKE_ANIMATION_RATE);
        quake.scheduleQuake(world, ticks);
        return quake;
    }
    
    
    public static Vein createVein(TYPE world, String name, Point pt,
                                  TYPE ticks, TYPE i_store)
    {
        Vein vein = new Vein("vein" + name,
                             randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
                             pt, getImages(i_store, 'vein'));
        return vein;
    }
    
    
    public static void scheduleAction(TYPE world, Entity entity,
                                      TYPE action, long time)
    {
        entity.addPendingAction(action);
        world.scheduleAction(action, time);
    }
    
    
    public static void scheduleAnimation(TYPE world, Entity entity, repeat_count=0)
    {
        scheduleAction(world, entity,
                       createAnimationAction(world, entity, repeat_count),
                       entity.getAnimationRate());
    }
    
    
    public static void clearPendingActions(world, Entity entity)
    {
        for (Action action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
    }
    
}
