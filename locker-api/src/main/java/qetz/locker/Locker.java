package qetz.locker;

import java.util.UUID;

public interface Locker {
  void registerFactory(LookFactory factory);

  Look findOrCreateById(UUID id);
  void changeLook(UUID id, Look look);
}