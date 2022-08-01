package qetz.locker.example.nick;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NickRegistry {
  public static NickRegistry empty() {
    return new NickRegistry(Lists.newArrayList());
  }

  private final Collection<UUID> nicked;

  public void add(UUID target) {
    Preconditions.checkNotNull(target, "target");
    nicked.add(target);
  }

  public void remove(UUID target) {
    Preconditions.checkNotNull(target, "target");
    nicked.remove(target);
  }

  public boolean isNicked(UUID target) {
    Preconditions.checkNotNull(target, "target");
    return nicked.contains(target);
  }
}