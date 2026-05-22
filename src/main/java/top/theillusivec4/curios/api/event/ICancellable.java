package top.theillusivec4.curios.api.event;

public interface ICancellable {

  boolean isCanceled();

  void setCanceled(boolean canceled);
}
