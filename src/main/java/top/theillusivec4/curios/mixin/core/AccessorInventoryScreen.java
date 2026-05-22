package top.theillusivec4.curios.mixin.core;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryScreen.class)
public interface AccessorInventoryScreen {

  @Accessor
  @Mutable
  void setXMouse(float x);

  @Accessor
  @Mutable
  void setYMouse(float y);
}
