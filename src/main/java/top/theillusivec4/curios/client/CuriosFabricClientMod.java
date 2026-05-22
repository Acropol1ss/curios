package top.theillusivec4.curios.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import top.theillusivec4.curios.client.screen.CuriosScreen;
import top.theillusivec4.curios.client.screen.CuriosScreenEvents;
import top.theillusivec4.curios.impl.CuriosClientExtensions;
import top.theillusivec4.curios.impl.CuriosRegistry;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworkRegistration;

public class CuriosFabricClientMod implements ClientModInitializer {

  private static final CuriosClientEvents CLIENT_EVENTS = new CuriosClientEvents();
  private static final CuriosScreenEvents SCREEN_EVENTS = new CuriosScreenEvents();

  @Override
  public void onInitializeClient() {
    CuriosFabricNetworkRegistration.registerClientHandlers();
    KeyBindingHelper.registerKeyBinding(CuriosKeyMappings.OPEN_CURIOS_INVENTORY);
    MenuScreens.register(CuriosRegistry.CURIO_MENU, CuriosScreen::new);
    CuriosClientExtensions.loadRenderers();

    LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
        (entityType, entityRenderer, registrationHelper, context) -> {
          if (entityRenderer instanceof LivingEntityRenderer<?, ?, ?> livingRenderer) {
            @SuppressWarnings({"rawtypes", "unchecked"})
            var layer = new CuriosLayer(livingRenderer, context);
            registrationHelper.register(layer);
          }
        });

    ClientTickEvents.END_CLIENT_TICK.register(CLIENT_EVENTS::onClientTick);
    ItemTooltipCallback.EVENT.register((stack, context, type, lines) ->
        CLIENT_EVENTS.onTooltip(stack, context, null, lines));
    ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
      SCREEN_EVENTS.onScreenInit(screen);
      ScreenMouseEvents.beforeMouseClick(screen).register((s, event) ->
          SCREEN_EVENTS.onMouseClick(s, event.x(), event.y(), event.button()));
    });
  }
}
