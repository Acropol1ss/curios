package top.theillusivec4.curios.mixin.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayerGameMode.class)
public interface AccessorServerPlayerGameMode {

  @Accessor
  ServerPlayer getPlayer();
}
