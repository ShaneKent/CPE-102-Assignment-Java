import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.function.Function;
import java.util.Random;
import static java.lang.Math.*;
import processing.core.*;

public class Actions
{    
    public static final int BLOB_RATE_SCALE = 4;
    public static final int BLOB_ANIMATION_RATE_SCALE = 50;
    public static final int BLOB_ANIMATION_MIN = 1;
    public static final int BLOB_ANIMATION_MAX = 3;
    
    public static final int ORE_CORRUPT_MIN = 20000;
    public static final int ORE_CORRUPT_MAX = 30000;
    
    public static final int MONSTER_ANIMATION_RATE = 125;
    public static final int GHOST_ANIMATION_RATE = 250;
    
    public static final int BOMB_STEPS = 100;
    public static final int BOMB_DURATION = 5000;
    public static final int BOMB_ANIMATION_RATE = 250;
    
    public static final int SPAWNER_ANIMATION_RATE = 10;
    public static final int JAR_ANIMATION_RATE = 100;
    
    public static final int QUAKE_STEPS = 10;
    public static final int QUAKE_DURATION = 1100;
    public static final int QUAKE_ANIMATION_RATE = 100;
    
    public static final int VEIN_SPAWN_DELAY = 500;
    public static final int VEIN_RATE_MIN = 8000;
    public static final int VEIN_RATE_MAX = 17000;
   
    public static final int SUPER_VEIN_SPAWN_DELAY = 300;
    public static final int SUPER_VEIN_RATE_MIN = 1500;
    public static final int SUPER_VEIN_RATE_MAX = 3000;   
   
    public static LongConsumer createAnimationAction(WorldModel world, AnimatedActor entity,
                                        int repeat_count)
    {
      LongConsumer[] func = { null };
      func[0] = (long current_ticks) -> {
         entity.removePendingAction(func[0]);

         entity.nextImage();

         if (repeat_count != 1){ 
            scheduleAction(world, entity,
               createAnimationAction(world, entity, max(repeat_count - 1, 0)),
               current_ticks + entity.getAnimationRate());
          }
       };

      return func[0];
    }
    
    
    public static Miner tryTransformMiner(WorldModel world, Miner entity,
                                           Function<WorldModel, Miner> transform)
    {
        Miner new_entity = transform.apply(world);
        if (entity != new_entity)
        {
            clearPendingActions(world, entity);
            world.removeEntityAt(entity.getPosition());
            world.addEntity(new_entity);
            scheduleAnimation(world, new_entity);
        }
        return new_entity;
    }
    
    public static void removeEntity(WorldModel world, Occupant entity)
    {
        for (LongConsumer action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
        world.removeEntity(entity);
    }
    
    public static OreBlob createBlob(WorldModel world, String name, Point pt,
                                     int rate, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Random r = new Random();
        int randNum = r.nextInt((BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN) + 1) + BLOB_ANIMATION_MIN;
        OreBlob blob = new OreBlob(name, ImageStore.getImages(i_store, "blob"), pt, rate,
                                   randNum * BLOB_ANIMATION_RATE_SCALE);
        blob.scheduleBlob(world, ticks, i_store);
        return blob;
    }
    
    
    public static Ore createOre(WorldModel world, String name, Point pt,
                                long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Random r = new Random();
        int randNum = r.nextInt((ORE_CORRUPT_MAX - ORE_CORRUPT_MIN) + 1) + ORE_CORRUPT_MIN;
        Ore ore = new Ore(name, ImageStore.getImages(i_store, "ore"), pt, randNum);
        ore.scheduleOre(world, ticks, i_store);
        
        return ore;
    }
    
    
    public static Quake createQuake(WorldModel world, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Quake quake = new Quake("quake",
                                ImageStore.getImages(i_store, "quake"), pt, QUAKE_ANIMATION_RATE);
        quake.scheduleQuake(world, ticks);
        return quake;
    }
    
    public static MonsterSpawner createMonsterSpawner(WorldModel world, String name, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        MonsterSpawner ms = new MonsterSpawner("spawner", ImageStore.getImages(i_store, "spawner"), pt, SPAWNER_ANIMATION_RATE);
        ms.scheduleSpawner(world, ticks, i_store);
        return ms;
    }
    
    public static Jar createJar(WorldModel world, String name, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Jar jar = new Jar("jar", ImageStore.getImages(i_store, "jar"), pt, JAR_ANIMATION_RATE);
        jar.scheduleJar(world, ticks, i_store);
        return jar;
    }
    
    public static Vein createVein(WorldModel world, String name, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Random r = new Random();
        int randNum = r.nextInt((VEIN_RATE_MAX - VEIN_RATE_MIN) + 1) + VEIN_RATE_MIN;
        Vein vein = new Vein("vein" + name, ImageStore.getImages(i_store, "vein"), pt, randNum);
        return vein;
    }
    
    public static SuperVein createSuperVein(WorldModel world, String name, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store, int reach)
    {
        Random r = new Random();
        int randNum = r.nextInt((SUPER_VEIN_RATE_MAX - SUPER_VEIN_RATE_MIN) + 1) + SUPER_VEIN_RATE_MIN;
        SuperVein sv = new SuperVein("supervein" + name, ImageStore.getImages(i_store, "supervein"), pt, randNum, reach);
        sv.scheduleSuperVein(world, ticks, i_store);
        return sv;
    }
    
    public static Monster createMonster(WorldModel world, String name, Point pt, int rate, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Monster monster = new Monster(name, ImageStore.getImages(i_store, "monster"), pt, rate, MONSTER_ANIMATION_RATE);
        monster.scheduleMonster(world, ticks, i_store);
        return monster;
    }

    public static Ghost createGhost(WorldModel world, String name, Point pt, int rate, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        Ghost ghost = new Ghost(name, ImageStore.getImages(i_store, "ghost"), pt, rate, GHOST_ANIMATION_RATE);
        ghost.scheduleGhost(world, ticks, i_store);
        return ghost;
    }
    
    public static MonsterBomb createBomb(WorldModel world, String name, Point pt, long ticks, LinkedHashMap<String, List<PImage>> i_store)
    {
        MonsterBomb bomb = new MonsterBomb(name, ImageStore.getImages(i_store, "bomb"), pt, BOMB_ANIMATION_RATE, ImageStore.getImages(i_store, "corrupt"));
        bomb.scheduleBomb(world, ticks, i_store);
        return bomb;
    }
    
    public static void scheduleAction(WorldModel world, Occupant entity, LongConsumer action, long time)
    {
        entity.addPendingAction(action);
        world.scheduleAction(action, time);
    }
    
    
    public static void scheduleAnimation(WorldModel world, AnimatedActor entity, int repeat_count)
    {
        scheduleAction(world, entity,
                       createAnimationAction(world, entity, repeat_count),
                       entity.getAnimationRate());
    }
    
    public static void scheduleAnimation(WorldModel world, AnimatedActor entity){
        scheduleAction(world, entity,
                        createAnimationAction(world, entity, 0),
                        entity.getAnimationRate());
   }
    
    public static void clearPendingActions(WorldModel world, Occupant entity)
    {
        for (LongConsumer action : entity.getPendingActions())
            world.unscheduleAction(action);
        entity.clearPendingActions();
    }
}