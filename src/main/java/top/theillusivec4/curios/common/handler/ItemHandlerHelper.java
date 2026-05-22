package top.theillusivec4.curios.common.handler;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ItemHandlerHelper {

  private ItemHandlerHelper() {
  }

  public static void giveItemToPlayer(Player player, ItemStack stack) {
    if (stack.isEmpty()) {
      return;
    }
  ItemStack remaining = stack.copy();
    if (!player.getInventory().add(remaining)) {
      player.drop(remaining, false);
    }
  }
}
