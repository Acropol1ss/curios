package top.theillusivec4.curios.impl;

import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworking;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.internal.services.ICuriosNetwork;
import top.theillusivec4.curios.common.network.server.SPacketBreak;

public class CuriosNetwork implements ICuriosNetwork {

  @Override
  public void breakCurioInSlot(SlotContext slotContext) {
    LivingEntity livingEntity = slotContext.entity();

    if (livingEntity != null) {
      CuriosFabricNetworking.sendToPlayersTrackingEntityAndSelf(
          livingEntity,
          new SPacketBreak(livingEntity.getId(), slotContext.identifier(), slotContext.index()));
    }
  }
}
