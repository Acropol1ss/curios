package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class GatherSkippedAttributeTooltipsEvent {

  private final ItemStack stack;

  public GatherSkippedAttributeTooltipsEvent(ItemStack stack, Object context) {
    this.stack = stack;
  }

  public boolean isSkippingAll() {
    return false;
  }

  public boolean isSkipped(Identifier id) {
    return false;
  }
}
