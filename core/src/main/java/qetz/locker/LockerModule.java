package qetz.locker;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LockerModule extends AbstractModule {
  public static LockerModule withPlugin(Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");
    return new LockerModule(plugin);
  }

  private final Plugin plugin;

  @Override
  protected void configure() {
    bind(PaperLocker.class).toInstance(PaperLocker.withPlugin(plugin));
    bind(Plugin.class).toInstance(plugin);
  }
}