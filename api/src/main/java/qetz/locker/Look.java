package qetz.locker;

import org.bukkit.entity.Player;
import qetz.locker.outfit.Outfit;

public abstract class Look {
  public abstract Outfit chooseOutfit(Player receiver);
}