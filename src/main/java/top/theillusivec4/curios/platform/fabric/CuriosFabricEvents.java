package top.theillusivec4.curios.platform.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;
import top.theillusivec4.curios.api.event.CurioCanEquipEvent;
import top.theillusivec4.curios.api.event.CurioCanUnequipEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.event.CurioDropsEvent;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.event.SlotModifiersUpdatedEvent;

public final class CuriosFabricEvents {

  public static final Event<CurioAttributeModifierCallback> ATTRIBUTE_MODIFIER =
      EventFactory.createArrayBacked(CurioAttributeModifierCallback.class,
          callbacks -> event -> {
            for (CurioAttributeModifierCallback callback : callbacks) {
              callback.onAttributeModifier(event);
            }
            return event;
          });

  public static final Event<CurioCanEquipCallback> CAN_EQUIP =
      EventFactory.createArrayBacked(CurioCanEquipCallback.class,
          callbacks -> event -> {
            for (CurioCanEquipCallback callback : callbacks) {
              callback.onCanEquip(event);
            }
            return event;
          });

  public static final Event<CurioCanUnequipCallback> CAN_UNEQUIP =
      EventFactory.createArrayBacked(CurioCanUnequipCallback.class,
          callbacks -> event -> {
            for (CurioCanUnequipCallback callback : callbacks) {
              callback.onCanUnequip(event);
            }
            return event;
          });

  public static final Event<CurioChangeCallback> CHANGE =
      EventFactory.createArrayBacked(CurioChangeCallback.class,
          callbacks -> event -> {
            for (CurioChangeCallback callback : callbacks) {
              callback.onChange(event);
            }
            return event;
          });

  public static final Event<CurioDropsCallback> DROPS =
      EventFactory.createArrayBacked(CurioDropsCallback.class,
          callbacks -> event -> {
            for (CurioDropsCallback callback : callbacks) {
              callback.onDrops(event);
            }
            return event;
          });

  public static final Event<DropRulesCallback> DROP_RULES =
      EventFactory.createArrayBacked(DropRulesCallback.class,
          callbacks -> event -> {
            for (DropRulesCallback callback : callbacks) {
              callback.onDropRules(event);
            }
            return event;
          });

  public static final Event<SlotModifiersUpdatedCallback> SLOT_MODIFIERS_UPDATED =
      EventFactory.createArrayBacked(SlotModifiersUpdatedCallback.class,
          callbacks -> event -> {
            for (SlotModifiersUpdatedCallback callback : callbacks) {
              callback.onSlotModifiersUpdated(event);
            }
            return event;
          });

  private CuriosFabricEvents() {
  }

  public static <T> T post(T event) {
    if (event instanceof CurioAttributeModifierEvent attributeModifierEvent) {
      return (T) ATTRIBUTE_MODIFIER.invoker().onAttributeModifier(attributeModifierEvent);
    }
    if (event instanceof CurioCanEquipEvent canEquipEvent) {
      return (T) CAN_EQUIP.invoker().onCanEquip(canEquipEvent);
    }
    if (event instanceof CurioCanUnequipEvent canUnequipEvent) {
      return (T) CAN_UNEQUIP.invoker().onCanUnequip(canUnequipEvent);
    }
    if (event instanceof CurioChangeEvent changeEvent) {
      return (T) CHANGE.invoker().onChange(changeEvent);
    }
    if (event instanceof CurioDropsEvent dropsEvent) {
      return (T) DROPS.invoker().onDrops(dropsEvent);
    }
    if (event instanceof DropRulesEvent dropRulesEvent) {
      return (T) DROP_RULES.invoker().onDropRules(dropRulesEvent);
    }
    if (event instanceof SlotModifiersUpdatedEvent slotModifiersUpdatedEvent) {
      return (T) SLOT_MODIFIERS_UPDATED.invoker()
          .onSlotModifiersUpdated(slotModifiersUpdatedEvent);
    }
    return event;
  }

  @FunctionalInterface
  public interface CurioAttributeModifierCallback {
    CurioAttributeModifierEvent onAttributeModifier(CurioAttributeModifierEvent event);
  }

  @FunctionalInterface
  public interface CurioCanEquipCallback {
    CurioCanEquipEvent onCanEquip(CurioCanEquipEvent event);
  }

  @FunctionalInterface
  public interface CurioCanUnequipCallback {
    CurioCanUnequipEvent onCanUnequip(CurioCanUnequipEvent event);
  }

  @FunctionalInterface
  public interface CurioChangeCallback {
    CurioChangeEvent onChange(CurioChangeEvent event);
  }

  @FunctionalInterface
  public interface CurioDropsCallback {
    CurioDropsEvent onDrops(CurioDropsEvent event);
  }

  @FunctionalInterface
  public interface DropRulesCallback {
    DropRulesEvent onDropRules(DropRulesEvent event);
  }

  @FunctionalInterface
  public interface SlotModifiersUpdatedCallback {
    SlotModifiersUpdatedEvent onSlotModifiersUpdated(SlotModifiersUpdatedEvent event);
  }
}
