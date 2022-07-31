package qetz.locker;

public interface Locker {
  void registerFactory(LookFactory factory);

  void changeLook(Look look);
}