package top.theillusivec4.curios.platform.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import top.theillusivec4.curios.common.network.client.CPacketDestroy;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;
import top.theillusivec4.curios.common.network.client.CPacketOpenVanilla;
import top.theillusivec4.curios.common.network.client.CPacketPage;
import top.theillusivec4.curios.common.network.client.CPacketToggleCosmetics;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;
import top.theillusivec4.curios.common.network.client.CuriosClientPayloadHandler;
import top.theillusivec4.curios.common.network.server.CuriosServerPayloadHandler;
import top.theillusivec4.curios.common.network.server.SPacketBreak;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;
import top.theillusivec4.curios.common.network.server.SPacketPage;
import top.theillusivec4.curios.common.network.server.SPacketQuickMove;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncActiveState;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncCurios;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncData;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncModifiers;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncRender;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncStack;

public final class CuriosFabricNetworkRegistration {

  private CuriosFabricNetworkRegistration() {
  }

  public static void registerPayloadTypes() {
    PayloadTypeRegistry.playC2S().register(CPacketDestroy.TYPE, CPacketDestroy.STREAM_CODEC);
    PayloadTypeRegistry.playC2S().register(CPacketOpenCurios.TYPE, CPacketOpenCurios.STREAM_CODEC);
    PayloadTypeRegistry.playC2S().register(CPacketOpenVanilla.TYPE, CPacketOpenVanilla.STREAM_CODEC);
    PayloadTypeRegistry.playC2S().register(CPacketPage.TYPE, CPacketPage.STREAM_CODEC);
    PayloadTypeRegistry.playC2S().register(CPacketToggleRender.TYPE, CPacketToggleRender.STREAM_CODEC);
    PayloadTypeRegistry.playC2S()
        .register(CPacketToggleCosmetics.TYPE, CPacketToggleCosmetics.STREAM_CODEC);

    PayloadTypeRegistry.playS2C().register(SPacketSyncStack.TYPE, SPacketSyncStack.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketGrabbedItem.TYPE, SPacketGrabbedItem.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketSyncCurios.TYPE, SPacketSyncCurios.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketSyncData.TYPE, SPacketSyncData.STREAM_CODEC);
    PayloadTypeRegistry.playS2C()
        .register(SPacketSyncModifiers.TYPE, SPacketSyncModifiers.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketSyncRender.TYPE, SPacketSyncRender.STREAM_CODEC);
    PayloadTypeRegistry.playS2C()
        .register(SPacketSyncActiveState.TYPE, SPacketSyncActiveState.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketBreak.TYPE, SPacketBreak.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketPage.TYPE, SPacketPage.STREAM_CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketQuickMove.TYPE, SPacketQuickMove.STREAM_CODEC);
  }

  public static void registerServerHandlers() {
    var handler = CuriosServerPayloadHandler.getInstance();
    ServerPlayNetworking.registerGlobalReceiver(CPacketDestroy.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handleDestroyPacket(payload, context.player())));
    ServerPlayNetworking.registerGlobalReceiver(CPacketOpenCurios.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handleOpenCurios(payload, context.player())));
    ServerPlayNetworking.registerGlobalReceiver(CPacketOpenVanilla.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handleOpenVanilla(payload, context.player())));
    ServerPlayNetworking.registerGlobalReceiver(CPacketPage.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handlePage(payload, context.player())));
    ServerPlayNetworking.registerGlobalReceiver(CPacketToggleRender.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handlerToggleRender(payload, context.player())));
    ServerPlayNetworking.registerGlobalReceiver(CPacketToggleCosmetics.TYPE,
        (payload, context) -> context.server().execute(
            () -> handler.handlerToggleCosmetics(payload, context.player())));
  }

  public static void registerClientHandlers() {
    var handler = CuriosClientPayloadHandler.getInstance();
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncStack.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketGrabbedItem.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncCurios.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncData.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncModifiers.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncRender.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSyncActiveState.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketBreak.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketPage.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketQuickMove.TYPE,
        (payload, context) -> context.client().execute(() -> handler.handle(payload)));
  }
}
