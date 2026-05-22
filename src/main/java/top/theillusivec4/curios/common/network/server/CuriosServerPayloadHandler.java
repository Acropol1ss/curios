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

package top.theillusivec4.curios.common.network.server;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.container.CuriosMenu;
import top.theillusivec4.curios.common.inventory.container.CuriosMenuProvider;
import top.theillusivec4.curios.common.network.client.CPacketDestroy;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;
import top.theillusivec4.curios.common.network.client.CPacketOpenVanilla;
import top.theillusivec4.curios.common.network.client.CPacketPage;
import top.theillusivec4.curios.common.network.client.CPacketToggleCosmetics;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncRender;
import top.theillusivec4.curios.common.network.server.sync.SPacketSyncStack;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworking;

public class CuriosServerPayloadHandler {

  private static final CuriosServerPayloadHandler INSTANCE = new CuriosServerPayloadHandler();

  public static CuriosServerPayloadHandler getInstance() {
    return INSTANCE;
  }

  public void handlerToggleRender(final CPacketToggleRender data, final ServerPlayer player) {
    CuriosApi.getCuriosInventory(player)
        .flatMap(handler -> handler.getStacksHandler(data.identifier()))
        .ifPresent(stacksHandler -> {
          NonNullList<Boolean> renderStatuses = stacksHandler.getRenders();

          if (renderStatuses.size() > data.index()) {
            boolean value = !renderStatuses.get(data.index());
            renderStatuses.set(data.index(), value);
            CuriosFabricNetworking.sendToPlayersTrackingEntityAndSelf(player,
                new SPacketSyncRender(player.getId(), data.identifier(), data.index(), value));
          }
        });
  }

  public void handlePage(final CPacketPage data, final ServerPlayer player) {
    AbstractContainerMenu container = player.containerMenu;

    if (container instanceof CuriosMenu && container.containerId == data.windowId()) {

      if (data.next()) {
        ((CuriosMenu) container).nextPage();
      } else {
        ((CuriosMenu) container).prevPage();
      }
    }
  }

  public void handlerToggleCosmetics(final CPacketToggleCosmetics data, final ServerPlayer player) {
    AbstractContainerMenu container = player.containerMenu;

    if (container instanceof CuriosMenu && container.containerId == data.windowId()) {
      ((CuriosMenu) container).toggleCosmetics();
    }
  }

  public void handleOpenVanilla(final CPacketOpenVanilla data, final ServerPlayer player) {
    ItemStack stack = player.isCreative() ? data.carried() : player.containerMenu.getCarried();
    player.containerMenu.setCarried(ItemStack.EMPTY);
    player.doCloseContainer();

    if (!stack.isEmpty()) {
      player.inventoryMenu.setCarried(stack);
      CuriosFabricNetworking.sendToPlayer(player, new SPacketGrabbedItem(stack));
    }
  }

  public void handleOpenCurios(final CPacketOpenCurios data, final ServerPlayer player) {
    ItemStack stack = player.isCreative() ? data.carried() : player.containerMenu.getCarried();
    player.containerMenu.setCarried(ItemStack.EMPTY);
    player.openMenu(new CuriosMenuProvider());

    if (!stack.isEmpty()) {
      player.containerMenu.setCarried(stack);
      CuriosFabricNetworking.sendToPlayer(player, new SPacketGrabbedItem(stack));
    }
  }

  public void handleDestroyPacket(final CPacketDestroy data, final ServerPlayer player) {
    CuriosApi.getCuriosInventory(player)
        .ifPresent(handler -> handler.getCurios().values().forEach(stacksHandler -> {
          IDynamicStackHandler stackHandler = stacksHandler.getStacks();
          IDynamicStackHandler cosmeticStackHandler = stacksHandler.getCosmeticStacks();
          String id = stacksHandler.getIdentifier();

          for (int i = stackHandler.getSlots() - 1; i >= 0; i--) {
            NonNullList<Boolean> renderStates = stacksHandler.getRenders();
            SlotContext slotContext = new SlotContext(id, player, i, false,
                renderStates.size() > i && renderStates.get(i));
            ItemStack stack = stackHandler.getStackInSlot(i);
            ICurioItem.forEachModifier(stack, slotContext,
                (attributeHolder, attributeModifier) -> {
                  if (attributeHolder.value() instanceof SlotAttribute slotAttribute) {
                    handler.removeSlotModifier(slotAttribute.id(), attributeModifier.id());
                  } else {
                    AttributeInstance instance = player.getAttributes().getInstance(attributeHolder);

                    if (instance != null) {
                      instance.removeModifier(attributeModifier);
                    }
                  }
                });
            CuriosApi.getCurio(stack).ifPresent(curio -> curio.onUnequip(slotContext, stack));
            stackHandler.setStackInSlot(i, ItemStack.EMPTY);
            CuriosFabricNetworking.sendToPlayersTrackingEntityAndSelf(player,
                new SPacketSyncStack(player.getId(), id, i, ItemStack.EMPTY,
                    SPacketSyncStack.HandlerType.EQUIPMENT.ordinal(), new CompoundTag()));
            cosmeticStackHandler.setStackInSlot(i, ItemStack.EMPTY);
            CuriosFabricNetworking.sendToPlayersTrackingEntityAndSelf(player,
                new SPacketSyncStack(player.getId(), id, i, ItemStack.EMPTY,
                    SPacketSyncStack.HandlerType.COSMETIC.ordinal(), new CompoundTag()));
          }
        }));
  }
}
