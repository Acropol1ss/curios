package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;

public class PlayerXpPickupEvent {

  private final Player player;
  private final ExperienceOrb orb;
  private boolean canceled;

  public PlayerXpPickupEvent(Player player, ExperienceOrb orb) {
    this.player = player;
    this.orb = orb;
  }

  public Player getEntity() {
    return this.player;
  }

  public ExperienceOrb getOrb() {
    return this.orb;
  }

  public boolean isCanceled() {
    return this.canceled;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
}
