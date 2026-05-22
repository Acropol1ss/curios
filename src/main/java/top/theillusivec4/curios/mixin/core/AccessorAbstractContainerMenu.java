package top.theillusivec4.curios.mixin.core;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RemoteSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerMenu.class)
public interface AccessorAbstractContainerMenu {

  @Accessor("lastSlots")
  NonNullList<ItemStack> getLastSlots();

  @Accessor("remoteSlots")
  NonNullList<RemoteSlot> getRemoteSlots();
}
