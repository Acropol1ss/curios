package top.theillusivec4.curios;

import java.util.HashSet;
import java.util.Set;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.extensions.RegisterCuriosExtensionsEvent;
import top.theillusivec4.curios.api.internal.CuriosServices;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.common.capability.CurioInventoryCapability;
import top.theillusivec4.curios.common.capability.CurioResourceHandler;
import top.theillusivec4.curios.common.capability.ItemizedCurioCapability;
import top.theillusivec4.curios.common.data.CuriosSlotResources;
import top.theillusivec4.curios.common.integration.CuriosIntegrations;
import top.theillusivec4.curios.platform.fabric.CuriosFabricLifecycle;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworkRegistration;
import top.theillusivec4.curios.server.command.CurioArgumentType;
import top.theillusivec4.curios.server.command.CuriosCommand;
import top.theillusivec4.curios.server.command.CuriosSelectorOptions;

public class CuriosFabricMod implements ModInitializer {

  private static final CuriosSlotResources SLOT_RESOURCES = new CuriosSlotResources();

  @Override
  public void onInitialize() {
    CuriosFabricNetworkRegistration.registerPayloadTypes();
    CuriosFabricNetworkRegistration.registerServerHandlers();
    CuriosIntegrations.setup();
    registerCapabilities();
    ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(SLOT_RESOURCES);
    CuriosFabricLifecycle.register();
    CuriosSelectorOptions.register();
    new RegisterCuriosExtensionsEvent().registerExtensions();
    net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.EVENT.register(
        (dispatcher, registryAccess, environment) ->
            CuriosCommand.register(dispatcher, registryAccess));
    net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTING.register(
        server -> {
          SLOT_RESOURCES.bindRegistryAccess(server.registryAccess());
          CuriosSlotResources.SERVER = SLOT_RESOURCES;
          CuriosFabricLifecycle.events().onTagsUpdated();
          Set<String> slotIds = new HashSet<>();
          for (ISlotType value : CuriosSlotResources.SERVER.getSlots().values()) {
            slotIds.add(value.getId());
          }
          CurioArgumentType.slotIds = slotIds;
        });
  }

  private static void registerCapabilities() {
    for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
      CuriosCapability.INVENTORY.registerForType((entity, ctx) -> {
        if (entity instanceof LivingEntity livingEntity
            && !CuriosSlotTypes.getDefaultEntitySlotTypes(livingEntity).isEmpty()) {
          return new CurioInventoryCapability(livingEntity);
        }
        return null;
      }, entityType);

      CuriosCapability.ITEM_HANDLER.registerForType((entity, ctx) -> {
        if (entity instanceof LivingEntity livingEntity
            && !CuriosSlotTypes.getDefaultEntitySlotTypes(livingEntity).isEmpty()) {
          return CurioResourceHandler.from(livingEntity);
        }
        return null;
      }, entityType);
    }

    CuriosCapability.ITEM.registerFallback((stack, ctx) -> {
      Item item = stack.getItem();
      ICurioItem curioItem = CuriosServices.EXTENSIONS.getCurioItem(item);

      if (curioItem == null && item instanceof ICurioItem itemCurio) {
        curioItem = itemCurio;
      }

      if (curioItem != null && curioItem.hasCurioCapability(stack)) {
        return new ItemizedCurioCapability(curioItem, stack);
      }
      return null;
    });
  }

  public static String itemCacheKey(ItemStack stack) {
    return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString()
        + (!stack.getComponents().isEmpty()
        ? stack.getComponents().stream().map(TypedDataComponent::toString)
        .reduce((s, s2) -> s + s2).orElse("")
        : "");
  }
}
