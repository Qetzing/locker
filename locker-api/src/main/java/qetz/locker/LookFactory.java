package qetz.locker;

import java.util.UUID;

public interface LookFactory {
  Look create(UUID id);
}