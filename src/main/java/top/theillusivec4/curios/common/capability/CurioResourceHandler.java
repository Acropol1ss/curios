package top.theillusivec4.curios.common.capability;

import java.util.Arrays;
import java.util.Map;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.platform.fabric.CuriosFabricRegistry;

public final class CurioResourceHandler {

  private CurioResourceHandler() {
  }

  public static Storage<ItemVariant> from(final LivingEntity livingEntity) {
    CurioInventory inv = livingEntity.getAttachedOrCreate(CuriosFabricRegistry.INVENTORY);
    inv.setOwner(livingEntity);
    Map<String, ICurioStacksHandler> curios = inv.curios;
    Storage<ItemVariant>[] storages = new Storage[curios.size()];
    int index = 0;

    for (ICurioStacksHandler stacksHandler : curios.values()) {
      if (index < storages.length) {
        storages[index] = new CurioStacksStorage(stacksHandler.getStacks());
        index++;
      }
    }
    return new CombinedStorage<>(Arrays.asList(storages));
  }

  private static final class CurioStacksStorage implements Storage<ItemVariant> {

    private final IDynamicStackHandler stacks;

    private CurioStacksStorage(IDynamicStackHandler stacks) {
      this.stacks = stacks;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
      if (maxAmount <= 0) {
        return 0;
      }
      ItemStack toInsert = resource.toStack((int) Math.min(maxAmount, resource.getItem().getDefaultMaxStackSize()));
      for (int i = 0; i < this.stacks.getSlots(); i++) {
        ItemStack remainder = this.stacks.insertItem(i, toInsert, false);
        long inserted = toInsert.getCount() - remainder.getCount();
        if (inserted > 0) {
          return inserted;
        }
      }
      return 0;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
      long extracted = 0;
      for (int i = 0; i < this.stacks.getSlots(); i++) {
        ItemStack stack = this.stacks.getStackInSlot(i);
        if (!stack.isEmpty() && ItemVariant.of(stack).equals(resource)) {
          ItemStack result = this.stacks.extractItem(i, (int) Math.min(maxAmount - extracted, stack.getCount()), false);
          extracted += result.getCount();
          if (extracted >= maxAmount) {
            break;
          }
        }
      }
      return extracted;
    }

    @Override
    public java.util.Iterator<StorageView<ItemVariant>> iterator() {
      return java.util.Collections.emptyIterator();
    }
  }
}
