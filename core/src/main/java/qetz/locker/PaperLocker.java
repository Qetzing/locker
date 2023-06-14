package qetz.locker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaperLocker implements Locker {
  static PaperLocker withPlugin(Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");
    return new PaperLocker(Maps.newHashMap(), plugin);
  }

  private final Map<UUID, Look> looks;
  private final Plugin plugin;

  private LookFactory factory;

  @Override
  public void registerFactory(LookFactory factory) {
    if (this.factory != null) {
      throw new IllegalStateException("factory has already been registered");
    }
    this.factory = factory;
  }

  private LookFactory factory() {
    if (factory == null) {
      throw new IllegalStateException("can not create look without registered factory");
    }
    return factory;
  }

  @Override
  public Optional<Look> findById(UUID id) {
    Preconditions.checkNotNull(id, "id");
    return Optional.ofNullable(looks.get(id));
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void changeLook(UUID id, Look look) {
    Preconditions.checkNotNull(id, "id");
    Preconditions.checkNotNull(look, "look");
    if (!looks.containsKey(id)) {
      return;
    }
    var target = Bukkit.getPlayer(id);
    for (var player : Bukkit.getOnlinePlayers()) {
      player.hidePlayer(plugin, target);
    }
    looks.put(id, look);
    for (var player : Bukkit.getOnlinePlayers()) {
      player.showPlayer(plugin, target);
    }
  }

  Look findOrCreateByOriginal(Outfit original) {
    Preconditions.checkNotNull(original, "original");
    return findById(original.id())
      .orElseGet(() -> createByOriginal(original));
  }

  private Look createByOriginal(Outfit original) {
    Preconditions.checkNotNull(original, "original");
    var look = factory().create(original);
    looks.put(original.id(), look);
    return look;
  }

  void remove(UUID id) {
    Preconditions.checkNotNull(id, "id");
    looks.remove(id);
  }
}