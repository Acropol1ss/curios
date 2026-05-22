package top.theillusivec4.curios.client;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public final class CuriosClientMod {

  public static final RenderStateDataKey<List<SlotResult>> CUSTOM_RENDER =
      RenderStateDataKey.create();

  private CuriosClientMod() {
  }

  public static List<SlotResult> emptyRenderList() {
    return List.of();
  }

  public static void fillCustomRenderSlots(LivingEntity entity,
      net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState renderState) {
    List<SlotResult> customSlots = new ArrayList<>();
    CuriosApi.getCuriosInventory(entity)
        .ifPresent(handler -> handler.getCurios().forEach((id, stacksHandler) -> {
          IDynamicStackHandler stackHandler = stacksHandler.getStacks();
          IDynamicStackHandler cosmeticStacksHandler = stacksHandler.getCosmeticStacks();

          for (int i = 0; i < stackHandler.getSlots(); i++) {
            ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);
            boolean cosmetic = true;
            NonNullList<Boolean> renderStates = stacksHandler.getRenders();
            boolean renderable = renderStates.size() > i && renderStates.get(i);

            if (stack.isEmpty() && renderable) {
              stack = stackHandler.getStackInSlot(i);
              cosmetic = false;
            }

            if (!stack.isEmpty()) {
              SlotContext slotContext =
                  new SlotContext(id, entity, i, cosmetic, renderable);
              customSlots.add(new SlotResult(slotContext, stack));
            }
          }
        }));
    renderState.setData(CUSTOM_RENDER, customSlots);
  }
}
