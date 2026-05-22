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

package top.theillusivec4.curios.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import top.theillusivec4.curios.api.type.ICuriosMenu;
import top.theillusivec4.curios.client.screen.button.CuriosButton;
import top.theillusivec4.curios.mixin.core.InvokerScreen;
import top.theillusivec4.curios.config.CuriosClientConfig;
import top.theillusivec4.curios.platform.fabric.CuriosFabricNetworking;
import top.theillusivec4.curios.common.network.client.CPacketDestroy;

public class CuriosScreenEvents {

  public void onScreenInit(Screen screen) {

    if (!CuriosClientConfig.CLIENT.enableButton.get()) {
      return;
    }

    if (screen instanceof InventoryScreen inventoryScreen) {
      AbstractContainerMenu menu = inventoryScreen.getMenu();
      CuriosClientConfig.Client.ButtonCorner corner = CuriosClientConfig.CLIENT.buttonCorner.get();
      int x = corner.getXoffset();
      int y = corner.getYoffset();

      if (menu instanceof ICuriosMenu) {
        x = corner.getCreativeXoffset();
        y = corner.getCreativeYoffset();
      }
      ((InvokerScreen) inventoryScreen).callAddRenderableWidget(
          new CuriosButton(inventoryScreen, x, y, 14, 14, CuriosButton.SMALL));
    }
  }

  public boolean onMouseClick(Screen screen, double mouseX, double mouseY, int button) {

    if (screen instanceof InventoryScreen inventoryScreen && button == 1) {
      AbstractContainerMenu menu = inventoryScreen.getMenu();

      if (menu instanceof ICuriosMenu) {
        CuriosFabricNetworking.sendToServer(new CPacketDestroy());
        return true;
      }
    }
    return false;
  }
}
