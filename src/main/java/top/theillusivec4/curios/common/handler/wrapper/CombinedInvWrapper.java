package top.theillusivec4.curios.common.handler.wrapper;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.common.handler.IItemHandlerModifiable;

public class CombinedInvWrapper implements IItemHandlerModifiable {

  private final IItemHandlerModifiable[] handlers;

  public CombinedInvWrapper(IItemHandlerModifiable... handlers) {
    this.handlers = handlers;
  }

  private int getHandlerIndex(int slot) {
    int index = slot;
    for (IItemHandlerModifiable handler : this.handlers) {
      if (index < handler.getSlots()) {
        return index;
      }
      index -= handler.getSlots();
    }
    return -1;
  }

  private IItemHandlerModifiable getHandlerForSlot(int slot) {
    int index = slot;
    for (IItemHandlerModifiable handler : this.handlers) {
      if (index < handler.getSlots()) {
        return handler;
      }
      index -= handler.getSlots();
    }
    return null;
  }

  private int getSlotIndex(int slot) {
    int index = slot;
    for (IItemHandlerModifiable handler : this.handlers) {
      if (index < handler.getSlots()) {
        return index;
      }
      index -= handler.getSlots();
    }
    return -1;
  }

  @Override
  public int getSlots() {
    int count = 0;
    for (IItemHandlerModifiable handler : this.handlers) {
      count += handler.getSlots();
    }
    return count;
  }

  @Override
  @Nonnull
  public ItemStack getStackInSlot(int slot) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    return handler == null ? ItemStack.EMPTY : handler.getStackInSlot(getSlotIndex(slot));
  }

  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    if (handler != null) {
      handler.setStackInSlot(getSlotIndex(slot), stack);
    }
  }

  @Override
  @Nonnull
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    return handler == null ? stack : handler.insertItem(getSlotIndex(slot), stack, simulate);
  }

  @Override
  @Nonnull
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    return handler == null ? ItemStack.EMPTY : handler.extractItem(getSlotIndex(slot), amount, simulate);
  }

  @Override
  public int getSlotLimit(int slot) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    return handler == null ? 0 : handler.getSlotLimit(getSlotIndex(slot));
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    IItemHandlerModifiable handler = getHandlerForSlot(slot);
    return handler != null && handler.isItemValid(getSlotIndex(slot), stack);
  }
}
