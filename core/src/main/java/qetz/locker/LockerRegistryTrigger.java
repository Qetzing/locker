package qetz.locker;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class LockerRegistryTrigger implements Listener {
  private final PaperLocker locker;

  // Through the applied event priorities, the player is added/removed from the Locker
  // at first/last, so that other plugins can access the Look during following events

  @SuppressWarnings("deprecation")
  @EventHandler(priority = EventPriority.LOWEST)
  private void addToLocker(AsyncPlayerPreLoginEvent preLogin) {
    locker.createByOriginal(Outfit.fromGameProfile(
      WrappedGameProfile.fromHandle(preLogin.getPlayerProfile().getGameProfile())
    ));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  private void removeFromLocker(PlayerQuitEvent quit) {
    var player = quit.getPlayer();

    locker.remove(player.getUniqueId());
  }
}