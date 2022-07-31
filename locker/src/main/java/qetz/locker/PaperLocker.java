package qetz.locker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaperLocker implements Locker {
  public static Locker create() {
    return new PaperLocker(Maps.newHashMap());
  }

  private final Map<UUID, Look> looks;

  private LookFactory factory;

  @Override
  public void registerFactory(LookFactory factory) {
    if (this.factory != null) {
      throw new IllegalStateException("factory has already been registered");
    }
    this.factory = factory;
  }

  public void add(UUID id, Look look) {
    Preconditions.checkNotNull(id, "id");
    Preconditions.checkNotNull(look, "look");
    looks.put(id, look);
  }

  public void remove(UUID id) {
    Preconditions.checkNotNull(id, "id");
    looks.remove(id);
  }

  public Optional<Look> findById(UUID id) {
    Preconditions.checkNotNull(id, "id");
    return Optional.ofNullable(looks.get(id));
  }

  @Override
  public void changeLook(Look look) {
    throw new UnsupportedOperationException();
  }
}