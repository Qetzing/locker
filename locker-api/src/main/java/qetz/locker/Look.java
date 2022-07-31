package qetz.locker;

import java.util.UUID;

public abstract class Look {
  abstract Outfit chooseOutfit(UUID receiver);
}