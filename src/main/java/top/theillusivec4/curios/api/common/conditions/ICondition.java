package top.theillusivec4.curios.api.common.conditions;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.HolderLookup;

public interface ICondition {

  Codec<ICondition> CODEC = Codec.BOOL.xmap(value -> provider -> value,
      condition -> condition.test(null));

  boolean test(HolderLookup.Provider provider);

  static void writeConditions(HolderLookup.Provider provider, JsonObject json,
                              List<ICondition> conditions) {
    // Fabric datapack conditions are not wired in this port yet.
  }
}
