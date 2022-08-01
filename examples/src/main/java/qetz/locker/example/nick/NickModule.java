package qetz.locker.example.nick;

import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NickModule extends AbstractModule {
  public static NickModule create() {
    return new NickModule();
  }

  @Override
  protected void configure() {
    bind(NickRegistry.class).toInstance(NickRegistry.empty());
  }
}