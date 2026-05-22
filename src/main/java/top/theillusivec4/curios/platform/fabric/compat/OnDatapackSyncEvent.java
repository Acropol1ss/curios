package top.theillusivec4.curios.platform.fabric.compat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

public class OnDatapackSyncEvent {

  private final ServerPlayer player;
  private final PlayerList playerList;

  public OnDatapackSyncEvent(ServerPlayer player, PlayerList playerList) {
    this.player = player;
    this.playerList = playerList;
  }

  public ServerPlayer getPlayer() {
    return this.player;
  }

  public PlayerList getPlayerList() {
    return this.playerList;
  }
}
