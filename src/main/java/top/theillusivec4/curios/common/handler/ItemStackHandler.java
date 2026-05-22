package top.theillusivec4.curios.common.handler;

import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import top.theillusivec4.curios.api.util.ValueIOSerializable;

public class ItemStackHandler implements IItemHandlerModifiable, ValueIOSerializable {

  protected NonNullList<ItemStack> stacks;

  public ItemStackHandler() {
    this(0);
  }

  public ItemStackHandler(int size) {
    this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
  }

  public void setSize(int size) {
    NonNullList<ItemStack> resized = NonNullList.withSize(size, ItemStack.EMPTY);
    for (int i = 0; i < Math.min(size, this.stacks.size()); i++) {
      resized.set(i, this.stacks.get(i));
    }
    this.stacks = resized;
  }

  protected void validateSlotIndex(int slot) {
    if (slot < 0 || slot >= this.stacks.size()) {
      throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.stacks.size() + ")");
    }
  }

  protected void onContentsChanged(int slot) {
  }

  @Override
  public int getSlots() {
    return this.stacks.size();
  }

  @Override
  @Nonnull
  public ItemStack getStackInSlot(int slot) {
    this.validateSlotIndex(slot);
    return this.stacks.get(slot);
  }

  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    this.validateSlotIndex(slot);
    this.stacks.set(slot, stack);
    this.onContentsChanged(slot);
  }

  @Override
  @Nonnull
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (stack.isEmpty()) {
      return ItemStack.EMPTY;
    }
    if (!this.isItemValid(slot, stack)) {
      return stack;
    }
    this.validateSlotIndex(slot);
    ItemStack existing = this.stacks.get(slot);
    int limit = this.getSlotLimit(slot);

    if (!existing.isEmpty()) {
      if (!ItemStack.isSameItemSameComponents(existing, stack)) {
        return stack;
      }
      int space = limit - existing.getCount();
      if (space <= 0) {
        return stack;
      }
      int toAdd = Math.min(space, stack.getCount());
      if (!simulate) {
        existing.grow(toAdd);
        this.onContentsChanged(slot);
      }
      ItemStack remainder = stack.copy();
      remainder.shrink(toAdd);
      return remainder.isEmpty() ? ItemStack.EMPTY : remainder;
    }

    int inserted = Math.min(limit, stack.getCount());
    if (!simulate) {
      this.stacks.set(slot, stack.copyWithCount(inserted));
      this.onContentsChanged(slot);
    }
    if (inserted >= stack.getCount()) {
      return ItemStack.EMPTY;
    }
    ItemStack remainder = stack.copy();
    remainder.shrink(inserted);
    return remainder;
  }

  @Override
  @Nonnull
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    if (amount <= 0) {
      return ItemStack.EMPTY;
    }
    this.validateSlotIndex(slot);
    ItemStack existing = this.stacks.get(slot);
    if (existing.isEmpty()) {
      return ItemStack.EMPTY;
    }
    int toExtract = Math.min(amount, existing.getCount());
    ItemStack result = existing.copyWithCount(toExtract);
    if (!simulate) {
      existing.shrink(toExtract);
      if (existing.isEmpty()) {
        this.stacks.set(slot, ItemStack.EMPTY);
      }
      this.onContentsChanged(slot);
    }
    return result;
  }

  @Override
  public int getSlotLimit(int slot) {
    return 64;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return true;
  }

  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    ListTag nbtTagList = new ListTag();
    for (int i = 0; i < this.stacks.size(); i++) {
      if (!this.stacks.get(i).isEmpty()) {
        CompoundTag itemTag = new CompoundTag();
        itemTag.putInt("Slot", i);
        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, this.stacks.get(i))
            .ifSuccess(tag -> itemTag.put("Stack", tag));
        nbtTagList.add(itemTag);
      }
    }
    CompoundTag nbt = new CompoundTag();
    nbt.put("Items", nbtTagList);
    nbt.putInt("Size", this.stacks.size());
    return nbt;
  }

  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
    this.setSize(nbt.getIntOr("Size", this.stacks.size()));
    nbt.getListOrEmpty("Items").compoundStream().forEach(itemTags -> {
      int slot = itemTags.getIntOr("Slot", -1);
      if (slot >= 0 && slot < this.stacks.size()) {
        ItemStack[] stack = {ItemStack.EMPTY};
        Tag tag = itemTags.get("Stack");
        if (tag != null) {
          ItemStack.CODEC.decode(NbtOps.INSTANCE, tag).ifSuccess(s -> stack[0] = s.getFirst());
        }
        this.stacks.set(slot, stack[0]);
      }
    });
  }

  @Override
  public void serialize(ValueOutput output) {
    output.store("Data", CompoundTag.CODEC, serializeNBT(null));
  }

  @Override
  public void deserialize(ValueInput input) {
    input.read("Data", CompoundTag.CODEC)
        .ifPresent(tag -> deserializeNBT(input.lookup(), tag));
  }
}
