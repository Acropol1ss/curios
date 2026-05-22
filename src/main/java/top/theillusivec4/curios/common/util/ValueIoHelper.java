package top.theillusivec4.curios.common.util;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import top.theillusivec4.curios.api.util.ValueIOSerializable;

public final class ValueIoHelper {

  private ValueIoHelper() {
  }

  public static void writeChild(ValueOutput output, String key, ValueIOSerializable value) {
    value.serialize(output.child(key));
  }

  public static void readChild(ValueInput input, String key, ValueIOSerializable value) {
    input.child(key).ifPresent(value::deserialize);
  }
}
