package qetz.locker;

import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LockerModule extends AbstractModule {
  public static LockerModule create() {
    return new LockerModule();
  }

  @Override
  protected void configure() {
    bind(Locker.class).toInstance(PaperLocker.create());
  }
}