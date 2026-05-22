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

import java.util.ArrayList;
import java.util.List;

public class CuriosConfig {

  public static final Server SERVER = new Server();
  public static final Common COMMON = new Common();

  public static class Common {

    public final ConfigValue<List<String>> slots = new ConfigValue<>(new ArrayList<>());
  }

  public static class Server {

    public final ConfigValue<KeepCurios> keepCurios = new ConfigValue<>(KeepCurios.DEFAULT);
    public final ConfigValue<Integer> minimumColumns = new ConfigValue<>(1);
    public final ConfigValue<Integer> maxSlotsPerPage = new ConfigValue<>(48);
  }

  public enum KeepCurios {
    ON,
    DEFAULT,
    OFF
  }

  public static final class ConfigValue<T> {

    private T value;

    public ConfigValue(T defaultValue) {
      this.value = defaultValue;
    }

    public T get() {
      return this.value;
    }

    public void set(T value) {
      this.value = value;
    }
  }
}
