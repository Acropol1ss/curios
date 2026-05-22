package top.theillusivec4.curios.common.handler;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;

public interface IItemHandler {

  int getSlots();

  @Nonnull
  ItemStack getStackInSlot(int slot);

  @Nonnull
  ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate);

  @Nonnull
  ItemStack extractItem(int slot, int amount, boolean simulate);

  int getSlotLimit(int slot);

  boolean isItemValid(int slot, @Nonnull ItemStack stack);
}
