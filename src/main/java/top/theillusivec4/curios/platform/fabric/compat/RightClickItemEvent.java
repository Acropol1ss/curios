package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RightClickItemEvent {

  private final Player player;
  private final InteractionHand hand;
  private boolean canceled;
  private InteractionResult cancellationResult = InteractionResult.PASS;

  public RightClickItemEvent(Player player, InteractionHand hand) {
    this.player = player;
    this.hand = hand;
  }

  public Player getEntity() {
    return this.player;
  }

  public InteractionHand getHand() {
    return this.hand;
  }

  public ItemStack getItemStack() {
    return this.player.getItemInHand(this.hand);
  }

  public boolean isCanceled() {
    return this.canceled;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }

  public InteractionResult getCancellationResult() {
    return this.cancellationResult;
  }

  public void setCancellationResult(InteractionResult cancellationResult) {
    this.cancellationResult = cancellationResult;
  }
}
