package qetz.locker;

import com.comphenix.protocol.ProtocolLibrary;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class LockerPlugin extends JavaPlugin {
  private Injector injector;

  @Override
  public void onLoad() {
    this.injector = Guice.createInjector(LockerModule.withPlugin(this));
    registerLocker();
  }

  private void registerLocker() {
    var locker = injector.getInstance(PaperLocker.class);
    Bukkit.getServicesManager().register(
      Locker.class,
      locker,
      this,
      ServicePriority.Normal
    );
  }

  @Override
  public void onEnable() {
    var protocolManager = ProtocolLibrary.getProtocolManager();
    protocolManager.addPacketListener(injector.getInstance(LookApplyListener.class));

    var pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(injector.getInstance(LockerRemoveTrigger.class), this);
  }
}