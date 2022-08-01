package qetz.locker.component;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import qetz.locker.Look;
import qetz.locker.Outfit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NickedLook extends Look {
  public static NickedLook with(
    String bypassPermission,
    Outfit original,
    Outfit nicked
  ) {
    Preconditions.checkNotNull(bypassPermission, "bypassPermission");
    Preconditions.checkNotNull(original, "original");
    Preconditions.checkNotNull(nicked, "nicked");
    return new NickedLook(bypassPermission, original, nicked);
  }

  private final String bypassPermission;
  private final Outfit original;
  private final Outfit nicked;

  @Override
  public Outfit chooseOutfit(Player receiver) {
    if (receiver.hasPermission(bypassPermission)) {
      return original;
    }
    return nicked;
  }
}