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

package top.theillusivec4.curios.api;

import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.resources.Identifier;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class CuriosCapability {

  public static final Identifier ID_INVENTORY = CuriosResources.resource("inventory");
  public static final Identifier ID_ITEM_HANDLER = CuriosResources.resource("item_handler");
  public static final Identifier ID_ITEM = CuriosResources.resource("item");

  public static final EntityApiLookup<ICuriosItemHandler, Void> INVENTORY =
      EntityApiLookup.get(ID_INVENTORY, ICuriosItemHandler.class, Void.class);

  @SuppressWarnings("unchecked")
  public static final EntityApiLookup<Storage<ItemVariant>, Void> ITEM_HANDLER =
      (EntityApiLookup<Storage<ItemVariant>, Void>) (Object) EntityApiLookup.get(
          ID_ITEM_HANDLER, Storage.class, Void.class);

  public static final ItemApiLookup<ICurio, Void> ITEM =
      ItemApiLookup.get(ID_ITEM, ICurio.class, Void.class);
}
