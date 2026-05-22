package top.theillusivec4.curios.common.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Rect2i;
import top.theillusivec4.curios.client.screen.CuriosScreen;

public class CuriosExclusionAreas {

  public static List<Rect2i> create(CuriosScreen screen) {
      LocalPlayer player = net.minecraft.client.Minecraft.getInstance().player;

    if (player != null) {
      List<Rect2i> areas = new ArrayList<>();
      int left = screen.panelLeft() - screen.panelWidth;
      int top = screen.panelTop();

      List<Integer> list = screen.getMenu().grid;
      int height = 0;

      if (!list.isEmpty()) {
        height = list.getFirst() * 18 + 14;

        if (screen.getMenu().hasCosmetics) {
          areas.add(new Rect2i(screen.panelLeft() - 30, top - 34, 28, 34));
        }
      }
      areas.add(new Rect2i(left, top, screen.panelWidth, height));
      return areas;
    } else {
      return Collections.emptyList();
    }
  }
}
