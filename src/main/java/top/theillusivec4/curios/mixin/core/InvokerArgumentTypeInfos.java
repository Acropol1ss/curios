package top.theillusivec4.curios.mixin.core;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArgumentTypeInfos.class)
public interface InvokerArgumentTypeInfos {

  @Invoker("register")
  static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>>
      ArgumentTypeInfo<A, T> curios$register(
          Registry<ArgumentTypeInfo<?, ?>> registry,
          String name,
          Class<? extends A> clazz,
          ArgumentTypeInfo<A, T> info) {
    throw new AssertionError();
  }
}
