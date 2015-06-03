import processing.core.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.LongConsumer;

public class Jar extends AnimatedActor{

	  public Jar(String name, List<PImage> imgs, Point position, int animation_rate){
	      super(name, imgs, position, animation_rate);
	   }
	   
	   
	   public LongConsumer createJarAction(WorldModel world, LinkedHashMap<String, List<PImage>> i_store)
	   {
	      LongConsumer[] action = { null };
	      action[0] = (long current_ticks) -> {
	         
	      };

	      return action[0];
	   }
	   

	   public void scheduleJar(WorldModel world, long ticks, LinkedHashMap<String, List<PImage>> i_store) {
	      Actions.scheduleAnimation(world, this);
	      Actions.scheduleAction(world, this, createJarAction(world, i_store), ticks + Actions.JAR_ANIMATION_RATE);
	   }
	}

