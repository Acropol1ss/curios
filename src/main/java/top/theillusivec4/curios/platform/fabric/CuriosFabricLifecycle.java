package top.theillusivec4.curios.platform.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.common.CuriosCommonEvents;
import top.theillusivec4.curios.platform.fabric.compat.OnDatapackSyncEvent;
import top.theillusivec4.curios.platform.fabric.compat.RightClickItemEvent;

public final class CuriosFabricLifecycle {

  private static final CuriosCommonEvents EVENTS = new CuriosCommonEvents();

  public static CuriosCommonEvents events() {
    return EVENTS;
  }

  private CuriosFabricLifecycle() {
  }

  public static void register() {
    ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
      if (success) {
        EVENTS.onTagsUpdated();
        EVENTS.onDatapackSync(new OnDatapackSyncEvent(null, server.getPlayerList()));
      }
    });

    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
      ServerPlayer player = handler.getPlayer();
      EVENTS.onDatapackSync(new OnDatapackSyncEvent(player, server.getPlayerList()));
    });

    ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
      if (entity instanceof LivingEntity && !(entity instanceof Player)) {
        EVENTS.entityConstructing(new EntityConstructingEvent(entity));
      }
      if (!world.isClientSide()) {
        EVENTS.entityJoinWorld(new EntityJoinLevelEvent(entity));
      }
    });

    EntityTrackingEvents.START_TRACKING.register((entity, player) ->
        EVENTS.playerStartTracking(new PlayerStartTrackingEvent(player, entity)));

    UseItemCallback.EVENT.register((player, world, hand) -> {
      if (player instanceof ServerPlayer serverPlayer) {
        EVENTS.curioRightClick(new RightClickItemEvent(serverPlayer, hand));
      }
      return InteractionResult.PASS;
    });

    ServerEntityEvents.EQUIPMENT_CHANGE.register(
        (entity, slot, previous, current) -> EVENTS.livingEquipmentChange(
            new LivingEquipmentChangeEvent(entity, slot, previous, current)));
  }

  public record EntityConstructingEvent(Entity entity) {
  }

  public record EntityJoinLevelEvent(Entity entity) {
  }

  public record PlayerStartTrackingEvent(Player player, Entity target) {
  }

  public record EntityTickEvent(Entity entity) {
  }

  public record LivingEquipmentChangeEvent(LivingEntity entity, EquipmentSlot slot,
                                           ItemStack from, ItemStack to) {
  }
}
