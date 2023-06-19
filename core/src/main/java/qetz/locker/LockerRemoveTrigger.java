package qetz.locker;

import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class LockerRemoveTrigger implements Listener {
  private final PaperLocker locker;

  // Through the applied event priority, the player is removed from the Locker
  // at last, so that other plugins can access him when quitting
  @EventHandler(priority = EventPriority.HIGHEST)
  private void removeFromLocker(PlayerQuitEvent quit) {
    var player = quit.getPlayer();

    locker.remove(player.getUniqueId());
  }
}