package qetz.locker.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import qetz.locker.Locker;
import qetz.locker.component.OriginalLookFactory;

public final class ExamplePlugin extends JavaPlugin {
  private Injector injector;

  @Override
  public void onLoad() {
    this.injector = Guice.createInjector(ExampleModule.withPlugin(this));
  }

  @Override
  public void onEnable() {
    var locker = injector.getInstance(Locker.class);
    var factory = injector.getInstance(OriginalLookFactory.class);
    locker.registerFactory(factory);
  }
}