package top.theillusivec4.curios.platform.fabric;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import top.theillusivec4.curios.mixin.core.InvokerArgumentTypeInfos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosResources;
import top.theillusivec4.curios.api.common.loot.SetCurioAttributesFunction;
import top.theillusivec4.curios.api.internal.services.ICuriosRegistry;
import top.theillusivec4.curios.common.capability.CurioInventory;
import top.theillusivec4.curios.common.inventory.container.CuriosMenu;
import top.theillusivec4.curios.common.util.EquipCurioTrigger;
import top.theillusivec4.curios.server.command.CurioArgumentType;

public final class CuriosFabricRegistry implements ICuriosRegistry {

  public static final AttachmentType<CurioInventory> INVENTORY =
      AttachmentRegistry.create(
          Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "inventory"),
          builder -> builder
              .initializer(() -> new CurioInventory(null))
              .copyOnDeath());

  public static final MenuType<CuriosMenu> CURIO_MENU = Registry.register(
      BuiltInRegistries.MENU,
      Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "curios_container"),
      new MenuType<>(CuriosMenu::new, FeatureFlags.DEFAULT_FLAGS));

  public static final LootItemFunctionType<SetCurioAttributesFunction> CURIO_ATTRIBUTES =
      Registry.register(
          BuiltInRegistries.LOOT_FUNCTION_TYPE,
          Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "set_curio_attributes"),
          new LootItemFunctionType<>(SetCurioAttributesFunction.CODEC));

  public static final EquipCurioTrigger EQUIP_TRIGGER = Registry.register(
      BuiltInRegistries.TRIGGER_TYPES,
      Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "equip_curio"),
      EquipCurioTrigger.INSTANCE);

  public static final ArgumentTypeInfo<CurioArgumentType, ?> CURIO_SLOT_ARGUMENT =
      InvokerArgumentTypeInfos.curios$register(
          BuiltInRegistries.COMMAND_ARGUMENT_TYPE,
          CuriosResources.MOD_ID + ":slot_type",
          CurioArgumentType.class,
          SingletonArgumentInfo.contextFree(CurioArgumentType::slot));

  public static final DataComponentType<CurioAttributeModifiers> ATTRIBUTE_MODIFIERS =
      Registry.register(
          BuiltInRegistries.DATA_COMPONENT_TYPE,
          Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "attribute_modifiers"),
          DataComponentType.<CurioAttributeModifiers>builder()
              .persistent(CurioAttributeModifiers.CODEC)
              .networkSynchronized(CurioAttributeModifiers.STREAM_CODEC)
              .cacheEncoding()
              .build());

  public static CurioInventory getInventory(LivingEntity entity) {
    CurioInventory inv = entity.getAttachedOrCreate(INVENTORY);
    inv.setOwner(entity);
    return inv;
  }

  @Override
  public DataComponentType<CurioAttributeModifiers> getAttributeModifierComponent() {
    return ATTRIBUTE_MODIFIERS;
  }
}
