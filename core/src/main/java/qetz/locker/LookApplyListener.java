package qetz.locker;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class LookApplyListener implements Listener {
  private final TablistPacketFactory tablistPacketFactory;
  private final PaperLocker locker;
  private final Plugin plugin;

  @EventHandler
  private void createTablistEntry(PlayerJoinEvent join) {
    var target = join.getPlayer();

    broadcastNewLook(target.getUniqueId());
    sendExistingLooks(target);
  }

  private Collection<Player> onlinePlayersExcept(Player target) {
    var receivers = Lists.<Player>newArrayList(Bukkit.getOnlinePlayers());
    receivers.remove(target);
    return receivers;
  }

  private void sendExistingLooks(Player target) {
    var existing = onlinePlayersExcept(target);
    for (var player : existing) {
      var look = locker.findById(player.getUniqueId()).orElseThrow();
      tablistPacketFactory
        .withReceiver(target)
        .withLook(look)
        .sendCreating();
    }
  }

  private void broadcastNewLook(UUID targetId) {
    var look = locker.findById(targetId).orElseThrow();
    tablistPacketFactory
      .withAllAvailableReceivers()
      .withLook(look)
      .sendCreating();
  }

  @EventHandler
  private void deleteTablistEntry(PlayerQuitEvent quit) {
    var target = quit.getPlayer();
    var look = locker.findById(target.getUniqueId()).orElseThrow();

    tablistPacketFactory
      .withReceivers(onlinePlayersExcept(target))
      .withLook(look)
      .sendDestroying();
  }
}