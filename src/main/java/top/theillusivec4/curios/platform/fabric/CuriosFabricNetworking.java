package top.theillusivec4.curios.platform.fabric;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class CuriosFabricNetworking {

  private CuriosFabricNetworking() {
  }

  public static void sendToServer(CustomPacketPayload payload) {
    ClientPlayNetworking.send(payload);
  }

  public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
    ServerPlayNetworking.send(player, payload);
  }

  public static void sendToPlayersTrackingEntityAndSelf(Entity entity,
                                                        CustomPacketPayload payload) {
    if (!(entity.level() instanceof ServerLevel serverLevel)) {
      return;
    }
    List<ServerPlayer> players = new ArrayList<>(PlayerLookup.tracking(entity));

    if (entity instanceof ServerPlayer self) {
      if (!players.contains(self)) {
        players.add(self);
      }
    } else if (entity instanceof LivingEntity living && living instanceof ServerPlayer player) {
      if (!players.contains(player)) {
        players.add(player);
      }
    }

    for (ServerPlayer player : players) {
      ServerPlayNetworking.send(player, payload);
    }
  }
}
