package top.theillusivec4.curios.api.util;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public interface ValueIOSerializable {

  void serialize(ValueOutput output);

  void deserialize(ValueInput input);
}
