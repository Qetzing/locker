package qetz.locker;

import java.util.Objects;
import java.util.UUID;

public final class SingleLook extends Look {
  public static SingleLook withOutfit(Outfit outfit) {
    Objects.requireNonNull(outfit, "outfit");
    return new SingleLook(outfit);
  }

  private final Outfit outfit;

  private SingleLook(Outfit outfit) {
    this.outfit = outfit;
  }

  @Override
  Outfit chooseOutfit(UUID receiver) {
    return outfit;
  }
}