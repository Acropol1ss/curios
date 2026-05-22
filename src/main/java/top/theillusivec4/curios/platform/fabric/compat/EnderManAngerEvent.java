package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EnderManAngerEvent {

  private final Player player;
  private final LivingEntity entity;
  private boolean canceled;

  public EnderManAngerEvent(Player player, LivingEntity entity) {
    this.player = player;
    this.entity = entity;
  }

  public Player getPlayer() {
    return this.player;
  }

  public LivingEntity getEntity() {
    return this.entity;
  }

  public boolean isCanceled() {
    return this.canceled;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
}
