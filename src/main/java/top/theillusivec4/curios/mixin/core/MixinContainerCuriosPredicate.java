package top.theillusivec4.curios.mixin.core;

import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.mixin.CuriosCommonMixinHooks;

/**
 * {@link Inventory} no longer declares {@link Container#hasAnyMatching}; it lives on the
 * {@link Container} interface as a default method. Mixin must be an {@code interface} so the
 * target {@link Container} is treated as an interface mixin, not a class mixin.
 */
@Mixin(Container.class)
public interface MixinContainerCuriosPredicate {

  @Inject(
      method = "hasAnyMatching(Ljava/util/function/Predicate;)Z",
      at = @At("TAIL"),
      cancellable = true
  )
  default void curios$hasAnyMatching(Predicate<ItemStack> predicate,
      CallbackInfoReturnable<Boolean> cir) {
    if ((Object) this instanceof Inventory inv) {
      if (!cir.getReturnValue() && CuriosCommonMixinHooks.contains(inv.player, predicate)) {
        cir.setReturnValue(true);
      }
    }
  }
}
