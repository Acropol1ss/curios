package top.theillusivec4.curios.mixin.core;

import java.util.function.Predicate;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntitySelectorOptions.class)
public interface InvokerEntitySelectorOptions {

  @Invoker("register")
  static void callRegister(String id, EntitySelectorOptions.Modifier handler,
                           Predicate<EntitySelectorParser> canUse, Component description) {
    throw new AssertionError();
  }
}
