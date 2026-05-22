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

package top.theillusivec4.curios.impl;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.internal.services.ICuriosRegistry;
import top.theillusivec4.curios.common.capability.CurioInventory;
import top.theillusivec4.curios.common.inventory.container.CuriosMenu;
import top.theillusivec4.curios.common.util.EquipCurioTrigger;
import top.theillusivec4.curios.platform.fabric.CuriosFabricRegistry;

public class CuriosRegistry implements ICuriosRegistry {

  public static final AttachmentType<CurioInventory> INVENTORY = CuriosFabricRegistry.INVENTORY;
  public static final MenuType<CuriosMenu> CURIO_MENU = CuriosFabricRegistry.CURIO_MENU;
  public static final LootItemFunctionType<?> CURIO_ATTRIBUTES = CuriosFabricRegistry.CURIO_ATTRIBUTES;
  public static final EquipCurioTrigger EQUIP_TRIGGER = CuriosFabricRegistry.EQUIP_TRIGGER;

  @Override
  public DataComponentType<CurioAttributeModifiers> getAttributeModifierComponent() {
    return CuriosFabricRegistry.ATTRIBUTE_MODIFIERS;
  }
}
