package top.theillusivec4.curios.platform.fabric.compat;

import java.util.Collection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;

public class LivingDropsEvent {

  private final LivingEntity entity;
  private final DamageSource source;
  private final Collection<ItemEntity> drops;
  private final boolean recentlyHit;

  public LivingDropsEvent(LivingEntity entity, DamageSource source,
                          Collection<ItemEntity> drops, boolean recentlyHit) {
    this.entity = entity;
    this.source = source;
    this.drops = drops;
    this.recentlyHit = recentlyHit;
  }

  public LivingEntity getEntity() {
    return this.entity;
  }

  public DamageSource getSource() {
    return this.source;
  }

  public Collection<ItemEntity> getDrops() {
    return this.drops;
  }

  public boolean isRecentlyHit() {
    return this.recentlyHit;
  }
}
