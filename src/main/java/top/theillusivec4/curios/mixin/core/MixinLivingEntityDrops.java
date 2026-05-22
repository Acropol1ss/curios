package top.theillusivec4.curios.mixin.core;

import java.util.ArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.platform.fabric.CuriosFabricLifecycle;
import top.theillusivec4.curios.platform.fabric.compat.LivingDropsEvent;

@Mixin(LivingEntity.class)
public class MixinLivingEntityDrops {

  @Inject(method = "dropAllDeathLoot", at = @At("TAIL"))
  private void curios$dropAllDeathLoot(ServerLevel level, DamageSource source, CallbackInfo ci) {
    LivingEntity living = (LivingEntity) (Object) this;

    if (living.level().isClientSide()) {
      return;
    }

    boolean recentlyHit = living.getLastHurtByPlayerMemoryTime() > 0
        && living.getLastHurtByPlayerMemoryTime() < 100;
    LivingDropsEvent event =
        new LivingDropsEvent(living, source, new ArrayList<>(), recentlyHit);
    CuriosFabricLifecycle.events().playerDrops(event);

    for (ItemEntity itemEntity : event.getDrops()) {
      level.addFreshEntity(itemEntity);
    }
  }
}
