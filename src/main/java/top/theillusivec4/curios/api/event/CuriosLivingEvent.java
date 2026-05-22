package top.theillusivec4.curios.api.event;

import net.minecraft.world.entity.LivingEntity;

public abstract class CuriosLivingEvent {

  private final LivingEntity entity;

  protected CuriosLivingEvent(LivingEntity entity) {
    this.entity = entity;
  }

  public LivingEntity getEntity() {
    return this.entity;
  }
}
