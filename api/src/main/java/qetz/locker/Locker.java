package qetz.locker;

import java.util.Optional;
import java.util.UUID;

public interface Locker {
  void registerFactory(LookFactory factory);

  Optional<Look> findById(UUID id);

  void updateLook(UUID id, Look newLook);
  void refreshLook(UUID id);
}