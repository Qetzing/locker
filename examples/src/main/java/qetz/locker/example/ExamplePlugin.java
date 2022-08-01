package qetz.locker.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import qetz.locker.Locker;
import qetz.locker.component.OriginalLookFactory;
import qetz.locker.example.nick.NickCommand;
import qetz.locker.example.nick.NickRegistryListener;

public final class ExamplePlugin extends JavaPlugin {
  private Injector injector;

  @Override
  public void onLoad() {
    this.injector = Guice.createInjector(ExampleModule.withPlugin(this));
  }

  @Override
  public void onEnable() {
    registerLockFactory();
    registerNickFeature();
  }

  private void registerLockFactory() {
    var locker = injector.getInstance(Locker.class);
    var factory = injector.getInstance(OriginalLookFactory.class);
    locker.registerFactory(factory);
  }

  @SuppressWarnings("ConstantConditions")
  private void registerNickFeature() {
    getCommand("nick").setExecutor(injector.getInstance(NickCommand.class));
    getServer().getPluginManager().registerEvents(
      injector.getInstance(NickRegistryListener.class),
      this
    );
  }
}