package top.theillusivec4.curios.mixin.core;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.platform.fabric.CuriosFabricLifecycle;
import top.theillusivec4.curios.platform.fabric.compat.EnderManAngerEvent;

@Mixin(EnderMan.class)
public class MixinEnderMan {

  @Inject(method = "isBeingStaredBy", at = @At("HEAD"), cancellable = true)
  private void curios$isBeingStaredBy(Player player, CallbackInfoReturnable<Boolean> cir) {
    EnderManAngerEvent event = new EnderManAngerEvent(player, (EnderMan) (Object) this);
    CuriosFabricLifecycle.events().enderManAnger(event);

    if (event.isCanceled()) {
      cir.setReturnValue(false);
    }
  }
}
