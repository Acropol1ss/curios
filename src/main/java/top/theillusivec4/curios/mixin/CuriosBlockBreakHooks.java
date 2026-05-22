package top.theillusivec4.curios.mixin;

import net.minecraft.world.entity.LivingEntity;

public final class CuriosBlockBreakHooks {

  public static final ThreadLocal<LivingEntity> BREAKER = new ThreadLocal<>();

  private CuriosBlockBreakHooks() {
  }
}
