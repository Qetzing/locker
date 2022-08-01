package qetz.locker.example;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import qetz.locker.Locker;
import qetz.locker.LockerProvider;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExampleModule extends AbstractModule {
  public static ExampleModule withPlugin(Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");
    return new ExampleModule(plugin);
  }

  private final Plugin plugin;

  @Override
  protected void configure() {
    bind(Plugin.class).toInstance(plugin);
    bind(Locker.class).toProvider(LockerProvider.create());
  }
}