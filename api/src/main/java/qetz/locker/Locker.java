package qetz.locker;

import java.util.Optional;
import java.util.UUID;

public interface Locker {
  void registerFactory(LookFactory factory);

  Optional<Look> findById(UUID id);
  void changeLook(UUID id, Look look);
}