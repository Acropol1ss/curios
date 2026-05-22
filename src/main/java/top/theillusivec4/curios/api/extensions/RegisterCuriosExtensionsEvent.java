package top.theillusivec4.curios.api.extensions;

import javax.annotation.Nonnull;
import top.theillusivec4.curios.api.internal.CuriosServices;

/**
 * Allows registration of new behavior to various game objects used by Curios.
 */
public class RegisterCuriosExtensionsEvent {

  /**
   * Registers an {@link ICurioSlotExtension} instance to a list of slot identifiers.
   *
   * <p>A slot identifier cannot be associated with more than one slot extension instance.
   * Attempting to register duplicates will throw an error.
   *
   * @param extension The slot extension instance
   * @param slotIds The list of slot identifiers to be associated with the specified slot extension
   */
  public void registerSlotExtension(@Nonnull ICurioSlotExtension extension, String... slotIds) {
    CuriosServices.EXTENSIONS.registerSlotExtension(extension, slotIds);
  }

  /**
   * Checks if the slot identifier already has a registered slot extension.
   *
   * @param slotId The slot identifier
   * @return True if the slot identifier has a registered slot extension, otherwise false
   */
  public boolean isSlotExtensionRegistered(String slotId) {
    return CuriosServices.EXTENSIONS.getSlotExtension(slotId) != null;
  }

  public void registerExtensions() {
    // Reserved for cross-mod extension registration during common setup.
  }
}
