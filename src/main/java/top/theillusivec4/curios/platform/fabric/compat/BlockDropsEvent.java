package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlockDropsEvent {

  private final Level level;
  private final LivingEntity breaker;
  private final ItemStack tool;
  private int droppedExperience;

  public BlockDropsEvent(Level level, LivingEntity breaker, ItemStack tool, int droppedExperience) {
    this.level = level;
    this.breaker = breaker;
    this.tool = tool;
    this.droppedExperience = droppedExperience;
  }

  public Level getLevel() {
    return this.level;
  }

  public LivingEntity getBreaker() {
    return this.breaker;
  }

  public ItemStack getTool() {
    return this.tool;
  }

  public int getDroppedExperience() {
    return this.droppedExperience;
  }

  public void setDroppedExperience(int droppedExperience) {
    this.droppedExperience = droppedExperience;
  }
}
