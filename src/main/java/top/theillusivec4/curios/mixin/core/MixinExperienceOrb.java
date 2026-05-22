package top.theillusivec4.curios.mixin.core;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.platform.fabric.CuriosFabricLifecycle;
import top.theillusivec4.curios.platform.fabric.compat.PlayerXpPickupEvent;

@Mixin(ExperienceOrb.class)
public class MixinExperienceOrb {

  @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
  private void curios$playerTouch(Player player, CallbackInfo ci) {
    PlayerXpPickupEvent event = new PlayerXpPickupEvent(player, (ExperienceOrb) (Object) this);
    CuriosFabricLifecycle.events().playerXPPickUp(event);
    if (event.isCanceled()) {
      ci.cancel();
    }
  }
}
