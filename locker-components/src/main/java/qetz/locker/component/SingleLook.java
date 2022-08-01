package qetz.locker.component;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import qetz.locker.Look;
import qetz.locker.Outfit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SingleLook extends Look {
  public static Look withOutfit(Outfit outfit) {
    Preconditions.checkNotNull(outfit, "outfit");
    return new SingleLook(outfit);
  }

  private final Outfit outfit;

  @Override
  public Outfit chooseOutfit(Player receiver) {
    return outfit;
  }
}