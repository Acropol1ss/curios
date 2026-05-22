package top.theillusivec4.curios.mixin.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.mixin.CuriosBlockBreakHooks;

@Mixin(ServerPlayerGameMode.class)
public class MixinServerPlayerGameMode {

  @Inject(method = "destroyBlock", at = @At("HEAD"))
  private void curios$destroyBlockHead(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
    ServerPlayerGameMode gameMode = (ServerPlayerGameMode) (Object) this;
    CuriosBlockBreakHooks.BREAKER.set(((AccessorServerPlayerGameMode) gameMode).getPlayer());
  }

  @Inject(method = "destroyBlock", at = @At("RETURN"))
  private void curios$destroyBlockReturn(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
    CuriosBlockBreakHooks.BREAKER.remove();
  }
}
