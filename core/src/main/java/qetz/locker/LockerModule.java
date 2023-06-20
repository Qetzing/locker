package qetz.locker;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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
    bind(Plugin.class).toInstance(plugin);
  }

  @Singleton
  @Provides
  @Inject
  PaperLocker bindPaperLocker(
    TablistPacketFactory tablistPacketFactory,
    Plugin plugin
  ) {
    return PaperLocker.with(tablistPacketFactory, plugin);
  }
}