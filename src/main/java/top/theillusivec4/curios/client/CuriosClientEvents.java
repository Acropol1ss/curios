/*
 * Copyright (c) 2018-2024 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package top.theillusivec4.curios.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosResources;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.CuriosTags;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworking;

public class CuriosClientEvents {

  public void onClientTick(Minecraft client) {
    if (CuriosKeyMappings.OPEN_CURIOS_INVENTORY.consumeClick() && client.isWindowActive()) {
      CuriosFabricNetworking.sendToServer(new CPacketOpenCurios(ItemStack.EMPTY));
    }
  }

  public void onTooltip(ItemStack stack, Item.TooltipContext context, Player player,
                        List<Component> tooltip) {
    if (stack.isEmpty()) {
      return;
    }
    Map<String, ISlotType> slots = getItemStackSlots(stack, player);

    if (slots.isEmpty()) {
      return;
    }
    List<String> slotIds = slots.keySet().stream().toList();
    MutableComponent slotsTooltip =
        Component.translatable("curios.tooltip.slot").append(" ").withStyle(ChatFormatting.GOLD);

    for (int j = 0; j < slotIds.size(); j++) {
      String id = slotIds.get(j);
      String key = "curios.identifier." + id;
      MutableComponent type =
          Component.translatableWithFallback(
              key, Character.toUpperCase(id.charAt(0)) + id.substring(1).toLowerCase());

      if (j < slotIds.size() - 1) {
        type = type.append(", ");
      }
      type = type.withStyle(ChatFormatting.YELLOW);
      slotsTooltip.append(type);
    }
    List<Component> toAdd = List.of(slotsTooltip);
    tooltip.addAll(1,
        CuriosApi.getCurio(stack).map(curio -> curio.getSlotsTooltip(toAdd, context)).orElse(toAdd));
  }

  private static Map<String, ISlotType> getItemStackSlots(ItemStack stack, Player player) {
    Map<String, ISlotType> result = new LinkedHashMap<>();
    boolean client = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    Map<String, ISlotType> map = player != null
        ? CuriosSlotTypes.getItemSlotTypes(stack, player)
        : CuriosSlotTypes.getItemSlotTypes(stack, client);

    for (Map.Entry<String, ISlotType> entry : map.entrySet()) {
      ISlotType slotType = entry.getValue();

      if (!slotType.getValidators().contains(CuriosResources.resource("all"))) {
        result.put(entry.getKey(), slotType);
      }
    }
    String curio = CuriosSlotTypes.Preset.CURIO.id();

    if (result.containsKey(curio)) {

      if (stack.is(CuriosTags.CURIO)) {
        return Map.of(curio, result.get(curio));
      } else {
        result.remove(curio);
      }
    }
    return result;
  }
}
