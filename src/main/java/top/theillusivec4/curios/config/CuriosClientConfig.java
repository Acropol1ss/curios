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

package top.theillusivec4.curios.config;

public class CuriosClientConfig {

  public static final Client CLIENT = new Client();

  public static class Client {

    public final CuriosConfig.ConfigValue<Boolean> renderCurios =
        new CuriosConfig.ConfigValue<>(true);
    public final CuriosConfig.ConfigValue<Boolean> enableButton =
        new CuriosConfig.ConfigValue<>(true);
    public final CuriosConfig.ConfigValue<Integer> buttonXOffset =
        new CuriosConfig.ConfigValue<>(0);
    public final CuriosConfig.ConfigValue<Integer> buttonYOffset =
        new CuriosConfig.ConfigValue<>(0);
    public final CuriosConfig.ConfigValue<Integer> creativeButtonXOffset =
        new CuriosConfig.ConfigValue<>(0);
    public final CuriosConfig.ConfigValue<Integer> creativeButtonYOffset =
        new CuriosConfig.ConfigValue<>(0);
    public final CuriosConfig.ConfigValue<ButtonCorner> buttonCorner =
        new CuriosConfig.ConfigValue<>(ButtonCorner.TOP_LEFT);

    public enum ButtonCorner {
      TOP_LEFT(26, -75, 73, -62), TOP_RIGHT(61, -75, 95, -62), BOTTOM_LEFT(26, -20, 73,
          -29), BOTTOM_RIGHT(61, -20, 95, -29);

      final int xoffset;
      final int yoffset;
      final int creativeXoffset;
      final int creativeYoffset;

      ButtonCorner(int x, int y, int creativeX, int creativeY) {
        xoffset = x;
        yoffset = y;
        creativeXoffset = creativeX;
        creativeYoffset = creativeY;
      }

      public int getXoffset() {
        return xoffset;
      }

      public int getYoffset() {
        return yoffset;
      }

      public int getCreativeXoffset() {
        return creativeXoffset;
      }

      public int getCreativeYoffset() {
        return creativeYoffset;
      }
    }
  }
}
