package qetz.locker;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class LockerTrigger implements Listener {
  private final PaperLocker locker;

  @EventHandler
  private void removeFromLocker(PlayerQuitEvent quit) {
    var player = quit.getPlayer();

    locker.remove(player.getUniqueId());
  }
}