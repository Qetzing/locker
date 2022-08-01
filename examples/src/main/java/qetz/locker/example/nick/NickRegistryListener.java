package qetz.locker.example.nick;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class NickRegistryListener implements Listener {
  private final NickRegistry registry;

  @EventHandler
  private void removeFromRegistry(PlayerQuitEvent quit) {
    registry.remove(quit.getPlayer().getUniqueId());
  }
}