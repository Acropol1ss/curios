package top.theillusivec4.curios.mixin.core;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.mixin.CuriosBlockBreakHooks;
import top.theillusivec4.curios.platform.fabric.CuriosFabricLifecycle;
import top.theillusivec4.curios.platform.fabric.compat.BlockDropsEvent;

@Mixin(Block.class)
public class MixinBlockExperience {

  @Inject(method = "tryDropExperience", at = @At("HEAD"), cancellable = true)
  private void curios$tryDropExperience(ServerLevel level, BlockPos pos, ItemStack tool,
      IntProvider experienceProvider, CallbackInfo ci) {
    LivingEntity breaker = CuriosBlockBreakHooks.BREAKER.get();

    if (breaker == null) {
      return;
    }

    int baseExperience = experienceProvider.sample(level.getRandom());
    BlockDropsEvent event = new BlockDropsEvent(level, breaker, tool, baseExperience);
    CuriosFabricLifecycle.events().onBreakBlock(event);
    int experience = event.getDroppedExperience();

    if (experience > 0) {
      ExperienceOrb.award(level, Vec3.atCenterOf(pos), experience);
    }
    ci.cancel();
  }
}
