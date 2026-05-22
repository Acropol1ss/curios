package top.theillusivec4.curios.common.handler;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;

public interface IItemHandlerModifiable extends IItemHandler {

  void setStackInSlot(int slot, @Nonnull ItemStack stack);
}
