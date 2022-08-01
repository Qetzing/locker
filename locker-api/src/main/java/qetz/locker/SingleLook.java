package qetz.locker;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SingleLook extends Look {
  public static SingleLook withOutfit(Outfit outfit) {
    Preconditions.checkNotNull(outfit, "outfit");
    return new SingleLook(outfit);
  }

  private final Outfit outfit;

  @Override
  Outfit chooseOutfit(UUID receiver) {
    return outfit;
  }
}