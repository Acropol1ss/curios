package top.theillusivec4.curios.common.handler;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {

  protected final IItemHandler itemHandler;
  protected final int index;

  public SlotItemHandler(IItemHandler itemHandler, int index, int x, int y) {
    super(new net.minecraft.world.SimpleContainer(1), 0, x, y);
    this.itemHandler = itemHandler;
    this.index = index;
  }

  @Override
  public boolean mayPlace(@Nonnull ItemStack stack) {
    return this.itemHandler.isItemValid(this.index, stack);
  }

  @Override
  @Nonnull
  public ItemStack getItem() {
    return this.itemHandler.getStackInSlot(this.index);
  }

  @Override
  public void set(@Nonnull ItemStack stack) {
    if (this.itemHandler instanceof IItemHandlerModifiable modifiable) {
      modifiable.setStackInSlot(this.index, stack);
    }
  }

  @Override
  public void setByPlayer(@Nonnull ItemStack stack) {
    set(stack);
  }

  @Override
  @Nonnull
  public ItemStack remove(int amount) {
    return this.itemHandler.extractItem(this.index, amount, false);
  }

  @Override
  public int getMaxStackSize() {
    return this.itemHandler.getSlotLimit(this.index);
  }

  @Override
  public boolean mayPickup(@Nonnull Player player) {
    return true;
  }

  public int getSlotIndex() {
    return this.index;
  }
}
