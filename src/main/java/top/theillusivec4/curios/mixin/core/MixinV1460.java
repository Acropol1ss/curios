package top.theillusivec4.curios.mixin.core;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.schemas.V1460;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.mixin.CuriosCommonMixinHooks;

/**
 * Player type registration uses a static supplier {@code method_5260} (Mojang name); older
 * versions inlined it as {@code lambda$registerTypes$…}, which breaks every Minecraft bump.
 */
@Mixin(V1460.class)
public class MixinV1460 {

  @Unique
  private static Schema curios$schema;

  @Inject(method = "method_5260", at = @At("HEAD"))
  private static void curios$captureSchema(Schema schema,
      CallbackInfoReturnable<TypeTemplate> cir) {
    curios$schema = schema;
  }

  @ModifyArg(
      method = "method_5260",
      at = @At(
          value = "INVOKE",
          target = "Lcom/mojang/datafixers/DSL;optionalFields([Lcom/mojang/datafixers/util/Pair;)Lcom/mojang/datafixers/types/templates/TypeTemplate;",
          remap = false),
      remap = true)
  private static Pair<String, TypeTemplate>[] curios$attachCuriosFixer(
      Pair<String, TypeTemplate>[] original) {
    return CuriosCommonMixinHooks.attachDataFixer(curios$schema, original);
  }
}
